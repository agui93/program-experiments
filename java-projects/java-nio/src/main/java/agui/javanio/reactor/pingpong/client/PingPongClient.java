package agui.javanio.reactor.pingpong.client;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.SocketChannel;
import java.util.Iterator;
import java.util.Set;

/**
 * @author agui93
 * @since 2021/10/28
 */
public class PingPongClient {

    private int port;
    private final int pingPongMaxLimit;
    private Selector selector;
    private SocketChannel socketChannel;
    private int pingPontCircles;
    private final ByteBuffer buffer;

    public PingPongClient() {
        this(8089, 1);
    }

    public PingPongClient(int port) {
        this(port, 1);
    }

    public PingPongClient(int port, int pingPongMaxLimit) {
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
        System.out.println("try to send: " + this.pingPongMaxLimit + " Ping, and received: " + this.pingPongMaxLimit + " Pong");
        System.out.println("try connecting...");
        System.out.println();
    }

    public void processIoEvents() throws IOException {
        System.out.println("processing IoEvents");
        System.out.println();
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
        System.out.println("sensed IO-EVENT: OP_CONNECT");
        while (!this.socketChannel.finishConnect()) ;
        System.out.println("finished connect");
        System.out.println("[client: " + this.socketChannel.getLocalAddress() + "] , [server: " + this.socketChannel.getRemoteAddress() + "]");
        //System.out.println("interest on new IO-EVENT: OP_WRITE");
        selectionKey.interestOps(SelectionKey.OP_WRITE);
        System.out.println();
    }

    //监听到IO-EVENT:OP_WRITE, 向server端发送Ping
    private void opWrite(SelectionKey selectionKey) throws IOException {
        //System.out.println("sensed IO-EVENT: OP_WRITE");

        buffer.clear();
        buffer.put("Ping".getBytes());
        buffer.flip();
        this.socketChannel.write(buffer);
        boolean outputIsComplete = !buffer.hasRemaining();

        if (outputIsComplete) {
            System.out.println("has send Ping to server");
            //System.out.println("interest on new IO-EVENT: OP_READ");
            selectionKey.interestOps(SelectionKey.OP_READ);
        }
    }

    //监听到IO-EVENT:OP_READ, 从server端读取数据
    private boolean opRead(SelectionKey selectionKey) throws IOException {
        //System.out.println("sensed IO-EVENT: OP_READ");

        buffer.clear();
        int bytes = socketChannel.read(buffer);
        if (bytes > 0) {
            boolean readIsComplete = !buffer.hasRemaining();
            if (readIsComplete) {
                buffer.flip();
                System.out.print("has read ");
                while (buffer.hasRemaining()) {
                    System.out.print((char) buffer.get());
                }
                System.out.println(" from server");

                this.pingPontCircles++;
                boolean closed = this.pingPontCircles >= this.pingPongMaxLimit;
                if (!closed) {
                    selectionKey.interestOps(SelectionKey.OP_WRITE);
                    //System.out.println("interest on new IO-EVENT: OP_WRITE");
                }
                System.out.println("PingPong: circle=" + this.pingPontCircles + ", maxList=" + this.pingPongMaxLimit + ", closedFlag=" + closed);
                System.out.println();
                return closed;
            }
        } else if (bytes == -1) {
            System.out.println("disconnect");
            System.out.println();
            return true;
        }

        System.out.println();
        return false;
    }

    public void close() {
        System.out.println("closing client");
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
        System.out.println("closed client");
        System.out.println();
    }

    public static void main(String[] args) throws IOException {
        System.out.println();
        System.out.println("this is a ping-pong client sample");
        System.out.println();
        PingPongClient pingPongClient = new PingPongClient(8089, 3);
        pingPongClient.startConnect();
        pingPongClient.processIoEvents();
    }
}
