package agui.javanio.reactor.pingpong.workerthreadpool;


import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.*;
import java.util.Iterator;
import java.util.Set;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * @author agui93
 * @since 2021/10/29
 */
public class PingPongWorkerThreadPoolReactorServer {

    interface Handler {
        void handle();
    }

    static class Reactor {
        private final String serverName = "PingPongWorkerThreadPoolReactorServer";
        int port;
        private Selector selector;
        private ServerSocketChannel serverSocketChannel;
        private ExecutorService workerThreadPoolExecutor;//一般不会用固定数量的线程池,这里进行简单模拟

        public Reactor(int port) {
            this.port = port;
        }

        public void initServer() throws IOException {
            this.selector = Selector.open();
            this.serverSocketChannel = ServerSocketChannel.open();
            this.serverSocketChannel.configureBlocking(false);
            this.workerThreadPoolExecutor = Executors.newFixedThreadPool(5);
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
            selectionKey.attach(new Acceptor(this.serverName, selectionKey, workerThreadPoolExecutor));
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

            this.workerThreadPoolExecutor.shutdownNow();

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
        private final ExecutorService workerThreadPoolExecutor;

        public Acceptor(String serverName, SelectionKey acceptorSelectionKey, ExecutorService workerThreadPoolExecutor) {
            this.serverName = serverName;
            this.acceptorSelectionKey = acceptorSelectionKey;
            this.workerThreadPoolExecutor = workerThreadPoolExecutor;
        }

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
            selectionKey.attach(new PingPongHandler(this.serverName, clientName, selectionKey, workerThreadPoolExecutor));
            selectionKey.interestOps(SelectionKey.OP_READ);
            System.out.println(this.serverName + " bind a PingPongHandler to the selector for " + socketChannel.getRemoteAddress());
        }

    }

    static class PingPongHandler implements Handler {
        private final String serverName;
        private final String clientName;
        private final SelectionKey selectionKey;
        private final ExecutorService workerThreadPoolExecutor;

        //这里模拟buffer pool的模拟，实际在workerThreadPool情况下，应该有一个内存池


        public PingPongHandler(String serverName, String clientName, SelectionKey selectionKey, ExecutorService workerThreadPoolExecutor) {
            this.serverName = serverName;
            this.clientName = clientName;
            this.selectionKey = selectionKey;
            this.workerThreadPoolExecutor = workerThreadPoolExecutor;
        }

        @Override
        public void handle() {
            if (!this.selectionKey.isValid()) {
                return;
            }
            if (this.selectionKey.isReadable()) {
                triggerByReadIoEvent();
            } else if (this.selectionKey.isWritable()) {
                triggerByWriteIoEvent();
            }

        }

        private void triggerByReadIoEvent() {
//            SocketChannel socketChannel = this.selectionKey.cancel();
            //读取数据


        }

        private int read(SocketChannel socketChannel) {
            return 0;
        }

        private String decode() {
            return "";
        }

        private void compute(String data) {
        }

        private void encode(String s) {
        }

        private boolean send(SocketChannel socketChannel) {
            return false;
        }

        private void triggerByWriteIoEvent() {
        }
    }

    //简单模拟的内存池, 要求线程安全
    static class BufferPool {
        //空闲的buffers
        //待读取的数据的buffers
        //待发送数据的buffers

        public ByteBuffer loadReadBuffer() {
            return null;
        }

    }

    public static void main(String[] args) {

    }
}
