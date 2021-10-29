package agui.javanio.reactor.pingpong.client;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.SocketChannel;
import java.util.*;
import java.util.concurrent.CountDownLatch;

/**
 * @author agui93
 * @since 2021/10/28
 */
public class PingPongClient {
    private final String clientName;
    private String localAddress;
    private final int port;
    private final int pingPongMaxLimit;
    private Selector selector;
    private SocketChannel socketChannel;
    private int pingPontCircles;
    private final ByteBuffer buffer;

    public PingPongClient(String clientName) {
        this(clientName, 8089, 1);
    }

    public PingPongClient(String clientName, int port) {
        this(clientName, port, 1);
    }

    public PingPongClient(String clientName, int port, int pingPongMaxLimit) {
        this.clientName = clientName;
        this.port = port;
        this.pingPongMaxLimit = pingPongMaxLimit;
        this.pingPontCircles = 0;
        this.buffer = ByteBuffer.allocate(4);
    }

    public void startConnect() throws IOException {
        this.selector = Selector.open();
        this.socketChannel = SocketChannel.open();
        this.socketChannel.configureBlocking(false);
        this.socketChannel.register(this.selector, SelectionKey.OP_CONNECT);
        this.socketChannel.connect(new InetSocketAddress(this.port));
        System.out.println(clientName + " try to send: " + this.pingPongMaxLimit + " Ping, and received: " + this.pingPongMaxLimit + " Pong");
        System.out.println(clientName + " try connecting...");
    }

    public void processIoEvents() throws IOException {
        System.out.println(clientName + "processing IoEvents");
        boolean closed = false;
        while (!closed && !Thread.interrupted()) {
            int readyChannelCount = this.selector.select();
            if (readyChannelCount > 0) {
                Set<SelectionKey> selectedKeys = this.selector.selectedKeys();
                Iterator<SelectionKey> iterator = selectedKeys.iterator();
                while (iterator.hasNext()) {
                    SelectionKey selectionKey = iterator.next();
                    iterator.remove();

                    if (!selectionKey.isValid()) {
                        closed = true;
                        break;
                    }
                    if (selectionKey.isConnectable()) {
                        opConnect(selectionKey);
                    }
                    if (selectionKey.isReadable()) {
                        if (opRead(selectionKey)) {
                            closed = true;
                            break;
                        }
                    }
                    if (selectionKey.isWritable()) {
                        opWrite(selectionKey);
                    }
                }
            }
        }
        close();
    }

    //监听到OP_CONNECT后,处理链接事件; 然后监听读事件
    private void opConnect(SelectionKey selectionKey) throws IOException {
        System.out.println();
        System.out.println(clientName + " sensed IO-EVENT: OP_CONNECT");
        while (!this.socketChannel.finishConnect()) {
        }
        System.out.println(clientName + " finished connect");
        this.localAddress = this.socketChannel.getLocalAddress().toString();
        System.out.println(clientName + " [client: " + this.socketChannel.getLocalAddress() + "] , [server: " + this.socketChannel.getRemoteAddress() + "]");
        //System.out.println("interest on new IO-EVENT: OP_WRITE");
        selectionKey.interestOps(SelectionKey.OP_WRITE);
        System.out.println();
    }

    //监听到IO-EVENT:OP_WRITE, 向server端发送Ping
    private void opWrite(SelectionKey selectionKey) throws IOException {
        //System.out.println(clientName + " sensed IO-EVENT: OP_WRITE");

        buffer.clear();
        buffer.put("Ping".getBytes());
        buffer.flip();
        this.socketChannel.write(buffer);
        boolean outputIsComplete = !buffer.hasRemaining();

        if (outputIsComplete) {
            System.out.println(clientName + " has send Ping to server");
            //System.out.println(clientName + " interest on new IO-EVENT: OP_READ");
            selectionKey.interestOps(SelectionKey.OP_READ);
        }
    }

    //监听到IO-EVENT:OP_READ, 从server端读取数据
    private boolean opRead(SelectionKey selectionKey) throws IOException {
        //System.out.println(clientName + " sensed IO-EVENT: OP_READ");

        buffer.clear();
        int bytes = socketChannel.read(buffer);
        if (bytes > 0) {
            boolean readIsComplete = !buffer.hasRemaining();
            if (readIsComplete) {
                buffer.flip();
                System.out.print(clientName + " has read ");
                while (buffer.hasRemaining()) {
                    System.out.print((char) buffer.get());
                }
                System.out.println(" from server");

                this.pingPontCircles++;
                boolean closed = this.pingPontCircles >= this.pingPongMaxLimit;
                if (!closed) {
                    selectionKey.interestOps(SelectionKey.OP_WRITE);
                    //System.out.println(clientName + " interest on new IO-EVENT: OP_WRITE");
                }
                System.out.println(clientName + " PingPong: circle=" + this.pingPontCircles + ", maxList=" + this.pingPongMaxLimit + ", closedFlag=" + closed);
                System.out.println();
                return closed;
            }
        } else if (bytes == -1) {
            System.out.println(clientName + " disconnect");
            System.out.println();
            return true;
        }

        System.out.println();
        return false;
    }

    public void close() {
        System.out.println(clientName + " closing client");
        try {
            this.socketChannel.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

        try {
            this.selector.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        System.out.println(clientName + " closed client");
    }

    public String result() {
        return clientName + "-" + this.localAddress + " result=" + (this.pingPontCircles == this.pingPongMaxLimit) + ", ping-pong-circle=" + pingPontCircles + ", pingPongMaxLimit=" + pingPongMaxLimit;
    }

    public static void clientSample(String sampleName, int serverPort, int pingPongMaxLimit) throws IOException {
        System.out.println();
        System.out.println("this is a ping-pong client sample: " + sampleName);
        PingPongClient pingPongClient = new PingPongClient(sampleName, serverPort, pingPongMaxLimit);
        pingPongClient.startConnect();
        pingPongClient.processIoEvents();
        System.out.println(pingPongClient.result());
    }

    public static void multiConcurrentClients(int concurrentCount, int serverPort, int pingPongMaxLimit) throws InterruptedException {
        List<PingPongClient> pingPongClients = new ArrayList<>(concurrentCount);
        for (int i = 0; i < concurrentCount; i++) {
            String sampleName = "ClientSample" + i;
            PingPongClient pingPongClient = new PingPongClient(sampleName, serverPort, pingPongMaxLimit);
            pingPongClients.add(pingPongClient);
        }

        CountDownLatch countDownLatch = new CountDownLatch(pingPongClients.size());
        List<Thread> threadList = new ArrayList<>(pingPongClients.size());
        for (PingPongClient pingPongClient : pingPongClients) {
            threadList.add(new Thread(() -> {
                try {
                    try {
                        //等待约1000~1200毫秒
                        Thread.sleep(1000 + new Random().nextInt(200));
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    try {
                        pingPongClient.startConnect();
                        pingPongClient.processIoEvents();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                } finally {
                    countDownLatch.countDown();
                }
            }));
        }


        //并行执行pingPongClient
        for (Thread thread : threadList) {
            thread.start();
        }

        //等待获取所有client的执行情况
        countDownLatch.await();

        System.out.println();
        System.out.println("-------------------------");
        System.out.println("multiConcurrentClients concurrence=" + concurrentCount + ", pingPongMaxLimit=" + pingPongMaxLimit);
        for (PingPongClient pingPongClient : pingPongClients) {
            System.out.println(pingPongClient.result());
        }
        System.out.println("-------------------------");
    }

    public static void main(String[] args) throws IOException, InterruptedException {
//        clientSample("Sample1", 8089, 1);
//        clientSample("Sample2", 8089, 3);

        multiConcurrentClients(10, 8089, 10);
//        multiConcurrentClients(10, 8089, 50);
    }
}
