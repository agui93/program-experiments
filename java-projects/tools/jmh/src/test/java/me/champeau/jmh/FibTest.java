package me.champeau.jmh;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

/**
 * @author agui
 * @since 2021/9/17
 */
public class FibTest {

    @Test
    public void testFib() {
        assertEquals(144, Fib.fibClassic(12));
        assertEquals(144, Fib.tailRecFib(12));
    }

}
