public class Blackhole {
    public static void enter(Object obj) {
        System.out.println("Step 1");
        magic(obj);
        System.out.println("Step 2");
        synchronized (obj) {
            System.out.println("Step 3 (never reached here)");  // 不会执行到这里
        }
    }

（接下来请完成magic方法的编写）

}
