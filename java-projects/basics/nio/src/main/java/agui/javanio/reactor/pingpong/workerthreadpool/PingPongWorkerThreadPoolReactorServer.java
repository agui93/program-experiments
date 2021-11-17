package agui.javanio.reactor.pingpong.workerthreadpool;


import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.*;
import java.util.Iterator;
import java.util.Set;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Semaphore;
import java.util.concurrent.atomic.AtomicInteger;

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
            selectionKey.attach(new Acceptor(this.serverName, selectionKey, this.workerThreadPoolExecutor));
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


        //并发情况:或者用于io线程从channel读取数据到buffer  或者用于worker-thread-pool从buffer中读取数据(当且仅当buffer已从channel里读取一组完整数据)
        private final ByteBuffer readBuffer;
        private final Semaphore readSemaphore;

        private final AtomicInteger ping;
        private final AtomicInteger pong;


        public PingPongHandler(String serverName, String clientName, SelectionKey selectionKey, ExecutorService workerThreadPoolExecutor) {
            this.serverName = serverName;
            this.clientName = clientName;
            this.selectionKey = selectionKey;
            this.workerThreadPoolExecutor = workerThreadPoolExecutor;

            this.ping = new AtomicInteger(0);
            this.pong = new AtomicInteger(0);

            this.readBuffer = ByteBuffer.allocate(4);
            this.readSemaphore = new Semaphore(1);
        }

        @Override
        public void handle() {
            if (!this.selectionKey.isValid()) {
                return;
            }
            try {
                if (this.selectionKey.isReadable()) {
                    triggerByReadIoEvent();
                } else if (this.selectionKey.isWritable()) {
                    triggerByWriteIoEvent();
                }
            } catch (IOException | InterruptedException e) {
                e.printStackTrace();
            }
        }

        private void triggerByReadIoEvent() throws IOException, InterruptedException {
            if (!this.readSemaphore.tryAcquire()) {
                this.selectionKey.interestOps(SelectionKey.OP_READ);
                return;
            }

            //read:use-readBuffer
            int readStatus = read();


            if (readStatus == -1) {
                int pingCount = this.ping.get();
                int pongCount = this.pong.get();
                System.out.println("\n------result=" + (pingCount == pongCount) + ", ping=" + pingCount + ", pong=" + pongCount + " after " + this.clientName + " disconnect\n");
                this.selectionKey.cancel();
            } else if (readStatus == 1) {
                this.readSemaphore.release();
                this.workerThreadPoolExecutor.submit(() -> {
                    try {
                        String decodeObj;
                        this.readSemaphore.acquire();
                        try {
                            //阻塞等待readBuffer可以使用
                            decodeObj = decode();
                        } finally {
                            this.readBuffer.clear();//保证下次可以继续读取数据
                            this.readSemaphore.release();
                        }
                        //业务处理数据
                        compute(decodeObj);

                        //期待下次发送的数据,进行编码
                        encode("Pong");
                        //调整interestOps
                        this.selectionKey.interestOps(SelectionKey.OP_WRITE);
                    } catch (InterruptedException ignored) {
                    }
                });
            } else if (readStatus == 0) {
                this.selectionKey.interestOps(SelectionKey.OP_READ);
            }

            //-> decode:use-readBuffer, compute encode -> focus on write
        }

        private int read() throws IOException {
            SocketChannel socketChannel = (SocketChannel) this.selectionKey.channel();
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

        }

        private void triggerByWriteIoEvent() throws IOException {
            //send data
            send();
        }

        private void send() {
        }

    }


    public static void main(String[] args) {

    }
}
