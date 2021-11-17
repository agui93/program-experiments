package com.algs.leetcode.hot;

/**
 * https://leetcode-cn.com/problems/add-two-numbers/
 *
 * @author agui93
 * @since 2020/4/10
 */
public class AddTwoNumbers {
    public static class ListNode {
        int val;
        ListNode next;

        ListNode(int x) {
            val = x;
        }
    }

    public static void main(String[] args) {
        System.out.println(check(123, 128));
        System.out.println(check(1234, 128));
        System.out.println(check(121, 128));
        System.out.println(check(1, 99));
    }

    public static boolean check(int n1, int n2) {
        return (n1 + n2) == outPutNum(addTwoNumbers(createListNode(n1), createListNode(n2)));
    }

    public static ListNode createListNode(int num) {
        if (num < 0) {
            return null;
        }
        if (num == 0) {
            return new ListNode(0);
        }

        ListNode head = null;
        ListNode tail = null;
        while (num > 0) {
            ListNode node = new ListNode(num % 10);
            if (head == null) {
                head = node;
            } else {
                tail.next = node;
            }
            tail = node;

            num = num / 10;
        }
        return head;
    }

    public static int outPutNum(ListNode listNode) {
        if (listNode == null) {
            throw new IllegalArgumentException("param listNode empty");
        }
        int num = 0;
        int multiple = 1;
        ListNode p = listNode;
        while (p != null) {
            num += multiple * p.val;
            p = p.next;
            multiple *= 10;
        }
        return num;
    }

    public static ListNode addTwoNumbers(ListNode l1, ListNode l2) {
        if (l1 == null || l2 == null) {
            throw new IllegalArgumentException("param listNode empty");
        }

        ListNode p1 = l1;
        ListNode p2 = l2;


        ListNode head = null;
        ListNode tail = null;
        boolean carryBit = false;
        while (p1 != null || p2 != null) {
            int p1_num = 0;
            if (p1 != null) {
                p1_num = p1.val;
                p1 = p1.next;
            }

            int p2_num = 0;
            if (p2 != null) {
                p2_num = p2.val;
                p2 = p2.next;
            }


            int p_num = (carryBit ? 1 : 0) + p1_num + p2_num;

            ListNode node = new ListNode(p_num % 10);
            if (head == null) {
                head = node;
            } else {
                tail.next = node;
            }
            tail = node;

            carryBit = p_num / 10 > 0;
        }
        if (carryBit) {
            tail.next = new ListNode(1);
        }

        return head;
    }
}
