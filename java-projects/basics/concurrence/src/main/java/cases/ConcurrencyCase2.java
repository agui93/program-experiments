package cases;

import sun.misc.Unsafe;

import java.lang.reflect.Field;

/**
 * unsafe case
 *
 * @author agui93
 * @since 2021/11/30
 */
public class ConcurrencyCase2 {
    private static Unsafe unsafe;

    static {
        try {
            Field theUnsafe = Unsafe.class.getDeclaredField("theUnsafe");
            theUnsafe.setAccessible(true);
            unsafe = (Unsafe) theUnsafe.get(null);
        } catch (Exception ignore) {
        }
    }

    static class Element {
        private String name;
        private int num;

        Element(String name, int num) {
            this.name = name;
            this.num = num;
        }

        @Override
        public String toString() {
            return "name=" + name + ", num=" + num;
        }
    }


    private static void test() {
        //获取数组的转换因子，也就是数组中元素的地址增量
        final int scale = unsafe.arrayIndexScale(Object[].class);
        int REF_ELEMENT_SHIFT;
        if (4 == scale) {
            REF_ELEMENT_SHIFT = 2;
        } else if (8 == scale) {
            REF_ELEMENT_SHIFT = 3;
        } else {
            System.out.println("error scale");
            return;
        }
        int BUFFER_PAD = 128 / scale;

        int bufferSize = 2 * 2 * 2 * 2;
        int indexMask = bufferSize - 1;


        //实际数组的大小 = BUFFER_PAD + bufferSize + BUFFER_PAD;在有效元素的前后各有BUFFER_PAD个元素空位，用于做缓存行填充
        Object[] entries = new Object[bufferSize + 2 * BUFFER_PAD];


        //赋值时跳过了BUFFER_PAD个元素
        for (int i = 0; i < bufferSize; i++) {
            entries[BUFFER_PAD + i] = new Element("name" + i, 200 + i);
        }


        //获取数组中真正保存元素数据的开始位置; BUFFER_PAD << REF_ELEMENT_SHIFT 实际上是BUFFER_PAD * scale的等价高效计算方式
        int REF_ARRAY_BASE = unsafe.arrayBaseOffset(Object[].class) + (BUFFER_PAD << REF_ELEMENT_SHIFT);

        //获取数组中的元素
        for (int i = 0; i < bufferSize; i++) {
            System.out.println(unsafe.getObject(entries, REF_ARRAY_BASE + ((i & indexMask) << REF_ELEMENT_SHIFT)));
        }
    }

    public static void main(String[] args) {
        test();
    }
}
