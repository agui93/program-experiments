package cases;

import sun.misc.Unsafe;

import java.lang.reflect.Field;

/**
 * get unsafe instance by reflection
 *
 * @author agui93
 * @since 2021/11/30
 */
public class ConcurrencyCase1 {
    private static Unsafe unsafe;

    static {
        try {
            Field theUnsafe = Unsafe.class.getDeclaredField("theUnsafe");
            theUnsafe.setAccessible(true);
            unsafe = (Unsafe) theUnsafe.get(null);
        } catch (Exception ignore) {
        }
    }

    private static void checkUnsafeInstance() {
        if (unsafe == null) {
            System.out.println("get unsafe instance failed");
            return;
        }
        System.out.println("get unsafe instance success");
    }

    public static void main(String[] args) {
        checkUnsafeInstance();
    }

}
