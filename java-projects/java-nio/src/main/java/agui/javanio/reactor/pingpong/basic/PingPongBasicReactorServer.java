package agui.javanio.reactor.pingpong.basic;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.*;
import java.util.Iterator;
import java.util.Set;

/**
 * @author agui93
 * @since 2021/10/28
 */
public class PingPongBasicReactorServer {

    interface Handler {
        void handle(SelectionKey selectionKey);
    }

    static class Reactor {
        int port;
        private Selector selector;
        private ServerSocketChannel serverSocketChannel;

        public Reactor(int port) {
            this.port = port;
        }

        public void initServer() throws IOException {
            this.selector = Selector.open();
            this.serverSocketChannel = ServerSocketChannel.open();
            this.serverSocketChannel.configureBlocking(false);
        }


        public void runServer() throws IOException {
            this.serverSocketChannel.bind(new InetSocketAddress(this.port));
            bindAcceptor();
            listenAndDispatchIoEvents();
            close();
        }

        private void bindAcceptor() throws ClosedChannelException {
            this.serverSocketChannel.register(this.selector, SelectionKey.OP_ACCEPT, new Acceptor());
        }

        private void listenAndDispatchIoEvents() throws IOException {
            while (!Thread.interrupted()) {
                int readyChannelCount = this.selector.select();
                if (readyChannelCount > 0) {
                    Set<SelectionKey> selectedKeys = this.selector.selectedKeys();
                    Iterator<SelectionKey> iterator = selectedKeys.iterator();
                    while (iterator.hasNext()) {
                        SelectionKey selectionKey = iterator.next();
                        iterator.remove();
                        dispatchIoEvent(selectionKey);
                    }
                }
            }
        }

        public void dispatchIoEvent(SelectionKey selectionKey) {
            if (!selectionKey.isValid()) {
                return;
            }
            Object obj = selectionKey.attachment();
            if (obj instanceof Handler) {
                ((Handler) obj).handle(selectionKey);
            }
        }

        public void close() {
            try {
                this.serverSocketChannel.close();
            } catch (IOException e) {
                e.printStackTrace();
            }

            try {
                this.selector.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    static class Acceptor implements Handler {
        //selectionKey代表着ServerSocketChannel在selector上的注册
        @Override
        public void handle(SelectionKey selectionKey) {
            if (!selectionKey.isValid()) {
                return;
            }
            if (selectionKey.isAcceptable()) {
                ServerSocketChannel serverSocketChannel = (ServerSocketChannel) selectionKey.channel();
                try {
                    //建立链接
                    SocketChannel socketChannel = serverSocketChannel.accept();
                    //绑定新的handler到多路复用器selector
                    bindPingPongHandler2Selector(selectionKey, socketChannel);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }

        public void bindPingPongHandler2Selector(SelectionKey selectionKey, SocketChannel socketChannel) throws IOException {
            socketChannel.configureBlocking(false);
            socketChannel.register(selectionKey.selector(), SelectionKey.OP_READ, new PingPongHandler());
        }
    }

    static class PingPongHandler implements Handler {
        private final ByteBuffer buffer;

        public PingPongHandler() {
            this.buffer = ByteBuffer.allocate(4);
        }

        //selectionKey代表着键连后的SocketChannel在selector上的注册
        @Override
        public void handle(SelectionKey selectionKey) {
            if (!selectionKey.isValid()) {
                return;
            }
            try {
                if (selectionKey.isReadable()) {
                    opRead(selectionKey);
                } else if (selectionKey.isWritable()) {
                    opWrite(selectionKey);
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        private void opRead(SelectionKey selectionKey) throws IOException {
            SocketChannel socketChannel = (SocketChannel) selectionKey.channel();
            buffer.clear();
            int bytes = socketChannel.read(buffer);
            if (bytes > 0) {
                boolean readIsComplete = !buffer.hasRemaining();
                if (readIsComplete) {
                    //compute data
                    buffer.flip();
                    StringBuilder stringBuilder = new StringBuilder();
                    while (buffer.hasRemaining()) {
                        stringBuilder.append((char) buffer.get());
                    }
                    compute(stringBuilder.toString());

                    selectionKey.interestOps(SelectionKey.OP_WRITE);
                }
            } else if (bytes == -1) {
                selectionKey.cancel();
            }
        }


        private void compute(String data) {
            //模拟数据计算
            System.out.println("had read: " + data + "from client");
        }

        private void opWrite(SelectionKey selectionKey) throws IOException {
            SocketChannel socketChannel = (SocketChannel) selectionKey.channel();

            buffer.clear();
            buffer.put("Pong".getBytes());
            buffer.flip();
            socketChannel.write(buffer);

            boolean outputIsComplete = !buffer.hasRemaining();
            if (outputIsComplete) {
                System.out.println("has send Pong to client");
                selectionKey.interestOps(SelectionKey.OP_READ);
            }
        }
    }


    public static void main(String[] args) throws IOException {
        Reactor reactor = new Reactor(8089);
        reactor.initServer();
        reactor.runServer();
    }
}
