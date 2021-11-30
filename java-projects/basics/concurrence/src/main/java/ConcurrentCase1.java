/**
 * reorder
 *
 * @author agui93
 * @since 2021/11/29
 */
public class ConcurrentCase1 {

    static class Something {
        private int x = 0;
        private int y = 0;

        public void write() {
            x = 100;
            y = 50;
        }

        public void read() {
            if (x < y) {
                System.out.println("x < y"); //有会出现可能
            }
        }
    }


    public static void testReorder() {
        final Something something = new Something();
        new Thread(something::write).start();
        new Thread(something::read).start();
    }

    public static void main(String[] args) {
        final Something something = new Something();
        new Thread(something::write).start();
        new Thread(something::read).start();

        try {
            Thread.sleep(1000);
        } catch (InterruptedException ignored) {
        }
        System.out.flush();
    }
}
