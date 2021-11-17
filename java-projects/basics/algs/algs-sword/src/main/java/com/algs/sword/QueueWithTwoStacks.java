package com.algs.sword;

import java.util.ArrayDeque;

/**
 * // 面试题9：用两个栈实现队列
 * // 题目：用两个栈实现一个队列。队列的声明如下，请实现它的两个函数appendTail
 * // 和deleteHead，分别完成在队列尾部插入结点和在队列头部删除结点的功能。
 *
 * @author agui93
 * @since 2020/7/11
 */
public class QueueWithTwoStacks {


    //选用JDK栈实现java.util.ArrayDeque
    private ArrayDeque<Integer> stack1;
    private ArrayDeque<Integer> stack2;


    public QueueWithTwoStacks() {
        stack1 = new ArrayDeque<>();
        stack2 = new ArrayDeque<>();
    }

    public void appendTail(Integer value) {
        stack1.push(value);
    }

    public int deleteHead() {
        if (!stack2.isEmpty()) {
            return stack2.pop();
        }
        while (!stack1.isEmpty()) {
            stack2.push(stack1.pop());
        }

        if (stack2.isEmpty()) {
            return -1;
        }

        return stack2.pop();
    }


    public static void test(Integer data, Integer expected) {
        if (data == expected) {
            System.out.println("Test Passed!");
        } else {
            System.out.println("Test Failed!");
        }
    }


    public static void main(String[] args) {
        QueueWithTwoStacks queueWithTwoStacks = new QueueWithTwoStacks();
        queueWithTwoStacks.appendTail(1);
        queueWithTwoStacks.appendTail(2);
        queueWithTwoStacks.appendTail(3);
        test(queueWithTwoStacks.deleteHead(), 1);
        test(queueWithTwoStacks.deleteHead(), 2);

        queueWithTwoStacks.appendTail(4);

        test(queueWithTwoStacks.deleteHead(), 3);

        queueWithTwoStacks.appendTail(5);
        test(queueWithTwoStacks.deleteHead(), 4);

        test(queueWithTwoStacks.deleteHead(), 5);
        test(queueWithTwoStacks.deleteHead(), -1);

    }

}
