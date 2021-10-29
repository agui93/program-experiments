package agui.javanio.reactor.pingpong;

import java.nio.channels.SelectionKey;

/**
 * @author agui93
 * @since 2021/10/29
 */
public class PingPongWorkerThreadPoolReactorServer {

    interface Handler {
        void handle(SelectionKey selectionKey);
    }

    static class Reactor {

    }

}
