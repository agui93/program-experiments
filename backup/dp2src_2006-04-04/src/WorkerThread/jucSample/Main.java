import java.util.concurrent.Executors;
import java.util.concurrent.ExecutorService;

public class Main {
    public static void main(String[] args) {
        ExecutorService executorService = Executors.newFixedThreadPool(5);

        try {
            new ClientThread("Alice", executorService).start();
            new ClientThread("Bobby", executorService).start();
            new ClientThread("Chris", executorService).start();

            // 等待大约5秒
            Thread.sleep(5000);
        } catch (InterruptedException e) {
        } finally {
            executorService.shutdown();
        }
    }
}
