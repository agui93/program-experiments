package com.algs.sword;

import java.util.Arrays;

/**
 * // 面试题6：从尾到头打印链表
 * // 题目：输入一个链表的头结点，从尾到头反过来打印出每个结点的值。
 *
 * @author agui93
 * @since 2020/7/11
 */
public class PrintListInReversedOrder {

    public static class ListNode {
        private int value;
        private ListNode next;

        public ListNode(int v) {
            this.value = v;
        }
    }


    //todo by Deque Stack的JDK源码(线程非安全和线程安全的不同实现)
    public static int[] reversePrint(ListNode head) {
        if (head == null) {
            return new int[0];
        }

//        java.util.Deque<ListNode> deque = new java.util.ArrayDeque<>();
        java.util.Stack<ListNode> stack = new java.util.Stack<>();
        ListNode node = head;
        while (node != null) {
            stack.push(node);
            node = node.next;
        }

        int[] values = new int[stack.size()];

        for (int i = 0; i < values.length; i++) {
            values[i] = stack.pop().value;
        }


        return values;
    }


    //创建链表,返回链表head
    public static ListNode createList(int[] values) {
        ListNode head = null, tail = null;
        for (int value : values) {
            if (head == null) {
                head = tail = new ListNode(value);
            } else {
                tail.next = new ListNode(value);
                tail = tail.next;
            }
        }
        return head;
    }


    public static void main(String[] args) {
        int[] values = {2, 3, 1};
        ListNode head = createList(values);
        int[] result = reversePrint(head);
        System.out.println(Arrays.toString(result));
    }
}
