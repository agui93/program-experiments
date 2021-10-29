package agui.javanio.reactor.pingpong.basic;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.*;
import java.util.Iterator;
import java.util.Set;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * @author agui93
 * @since 2021/10/28
 */
public class PingPongBasicReactorServer {

    interface Handler {
        void handle();
    }

    static class Reactor {
        private final String serverName = "PingPongBasicReactorServer";
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
            System.out.println(this.serverName + " starting server");
            bindAcceptor();
            listenAndDispatchIoEvents();
            close();
        }

        private void bindAcceptor() throws ClosedChannelException {
            SelectionKey selectionKey = this.serverSocketChannel.register(this.selector, 0);
            selectionKey.attach(new Acceptor(this.serverName, selectionKey));
            selectionKey.interestOps(SelectionKey.OP_ACCEPT);
            System.out.println(this.serverName + " bind acceptor and interestOps=OP_ACCEPT");
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
                ((Handler) obj).handle();
            }
        }

        public void close() {
            System.out.println(this.serverName + " closing server");
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
            System.out.println(this.serverName + " closed server");
        }
    }

    static class Acceptor implements Handler {
        private final String serverName;
        private final SelectionKey acceptorSelectionKey;

        public Acceptor(String serverName, SelectionKey acceptorSelectionKey) {
            this.serverName = serverName;
            this.acceptorSelectionKey = acceptorSelectionKey;
        }

        //selectionKey代表着ServerSocketChannel在selector上的注册
        @Override
        public void handle() {
            if (!acceptorSelectionKey.isValid()) {
                return;
            }
            if (acceptorSelectionKey.isAcceptable()) {
                ServerSocketChannel serverSocketChannel = (ServerSocketChannel) acceptorSelectionKey.channel();
                try {
                    //建立链接
                    SocketChannel socketChannel = serverSocketChannel.accept();
                    System.out.println(this.serverName + " accept " + socketChannel.getRemoteAddress());
                    //绑定新的handler到多路复用器selector
                    bindPingPongHandler2Selector(acceptorSelectionKey.selector(), socketChannel);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }

        public void bindPingPongHandler2Selector(Selector selector, SocketChannel socketChannel) throws IOException {
            socketChannel.configureBlocking(false);
            SelectionKey selectionKey = socketChannel.register(selector, 0);
            String clientName = "Client" + socketChannel.getRemoteAddress().toString();
            selectionKey.attach(new PingPongHandler(this.serverName, clientName, selectionKey));
            selectionKey.interestOps(SelectionKey.OP_READ);
            System.out.println(this.serverName + " bind a PingPongHandler to the selector for " + socketChannel.getRemoteAddress());
        }
    }

    static class PingPongHandler implements Handler {
        private final String serverName;
        private final String clientName;

        private final SelectionKey selectionKey;

        private final ByteBuffer readBuffer;
        private final ByteBuffer writeBuffer;


        private final AtomicInteger ping;
        private final AtomicInteger pong;


        public PingPongHandler(String serverName, String clientName, SelectionKey selectionKey) {
            this.serverName = serverName;
            this.clientName = clientName;
            this.selectionKey = selectionKey;

            this.readBuffer = ByteBuffer.allocate(4);
            this.writeBuffer = ByteBuffer.allocate(4);
            this.ping = new AtomicInteger(0);
            this.pong = new AtomicInteger(0);
        }


        //selectionKey代表着键连后的SocketChannel在selector上的注册
        @Override
        public void handle() {
            if (!this.selectionKey.isValid()) {
                return;
            }
            try {
                if (this.selectionKey.isReadable()) {
                    this.triggerByReadIoEvent();
                } else if (this.selectionKey.isWritable()) {
                    this.triggerByWriteIoEvent();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        //读io事件触发动作
        private void triggerByReadIoEvent() throws IOException {
            SocketChannel socketChannel = (SocketChannel) this.selectionKey.channel();

            //读取数据，这步简化处理，实际处理需要定义合适的应用通信协议来处理编解码
            int readStatus = read(socketChannel);

            if (readStatus == -1) {
                int pingCount = this.ping.get();
                int pongCount = this.pong.get();
                System.out.println("\n------result=" + (pingCount == pongCount) + ", ping=" + pingCount + ", pong=" + pongCount + " after " + this.clientName + " disconnect\n");
                this.selectionKey.cancel();
            } else if (readStatus == 1) {
                //解码
                String decodeObj = decode();
                //业务处理数据
                compute(decodeObj);
                //期待下次发送的数据,进行编码
                encode("Pong");
                //调整interestOps
                this.selectionKey.interestOps(SelectionKey.OP_WRITE);
            } else if (readStatus == 0) {
                this.selectionKey.interestOps(SelectionKey.OP_READ);
            }
        }

        //写io事件触发动作
        private void triggerByWriteIoEvent() throws IOException {
            SocketChannel socketChannel = (SocketChannel) this.selectionKey.channel();
            boolean sendStatus = send(socketChannel);
            if (sendStatus) {
                this.selectionKey.interestOps(SelectionKey.OP_READ);
            } else {
                this.selectionKey.interestOps(SelectionKey.OP_WRITE);
            }
        }

        //读取数据: 链接已断开则返回-1 or 读取完毕则返回1  or 未读取完毕则返回0
        private int read(SocketChannel socketChannel) throws IOException {
            int bytes = socketChannel.read(this.readBuffer);
            if (bytes == -1) {
                return -1;
            }

            boolean readIsComplete = !this.readBuffer.hasRemaining();
            return readIsComplete ? 1 : 0;
        }

        //解码
        private String decode() {
            //解码:字节数组->对象
            this.readBuffer.flip();
            StringBuilder stringBuilder = new StringBuilder();
            while (this.readBuffer.hasRemaining()) {
                stringBuilder.append((char) this.readBuffer.get());
            }
            this.readBuffer.clear();//保证下次可以继续读取数据
            return stringBuilder.toString();
        }

        //处理数据
        private void compute(String data) {
            if ("Ping".equals(data)) {
                this.ping.incrementAndGet();
            }
            //模拟数据计算
            try {
                Thread.sleep(20);
            } catch (InterruptedException ignored) {
            }

            System.out.println(this.serverName + " had read: " + data + "from " + this.clientName);
        }

        //编码
        private void encode(String s) {
            byte[] encodeBytes = s.getBytes();//编码:对象->字节数组
            this.writeBuffer.clear();
            this.writeBuffer.put(encodeBytes);
            this.writeBuffer.flip();
        }

        //发送数据:  已经发送完毕则返回true or 未发送完毕则返回false
        private boolean send(SocketChannel socketChannel) throws IOException {
            socketChannel.write(this.writeBuffer);
            boolean outputIsComplete = !this.writeBuffer.hasRemaining();
            if (outputIsComplete) {
                this.pong.incrementAndGet();
                System.out.println(this.serverName + " has send Pong to " + this.clientName);
            }
            return outputIsComplete;
        }
    }

    public static void main(String[] args) throws IOException {
        System.out.println("this is PingPongBasicReactorServer Sample");
        Reactor reactor = new Reactor(8089);
        reactor.initServer();
        reactor.runServer();
    }
}
