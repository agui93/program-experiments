package com.algs.sword;

/**
 * // 面试题8：二叉树的下一个结点
 * // 题目：给定一棵二叉树和其中的一个结点，如何找出中序遍历顺序的下一个结点？
 * // 树中的结点除了有两个分别指向左右子结点的指针以外，还有一个指向父结点的指针。
 *
 * @author agui93
 * @since 2020/7/11
 */
public class NextNodeInBinaryTrees {

    public static class Node {
        int val;
        private Node parent;
        private Node left;
        private Node right;

        public Node(int val) {
            this.val = val;
        }
    }

    //根据中序遍历规则,归纳出实现步骤
    //1.当前节点有右子树,下一个节点是右子树的中序遍历第一个节点
    //2.否则;向上定位到父节点的左节点,父节点为下一个节点,不存在时说明无
    public static Node nextMidNode(Node node) {
        if (node == null) {
            return null;
        }

        Node current = node;
        if (current.right != null) {
            current = current.right;
            while (current.left != null) {
                current = current.left;
            }
            return current;
        } else {
            while (current.parent != null && current.parent.right == current) {
                current = current.parent;
            }
            return current.parent;
        }
    }


    public static void connectTreeNodes(Node parent, Node left, Node right) {
        if (parent != null) {
            parent.left = left;
            parent.right = right;
            if (left != null)
                left.parent = parent;
            if (right != null)
                right.parent = parent;
        }
    }


    // ====================测试代码====================
    public static void test(String testName, Node pNode, Node expected) {
        if (testName != null) {
            System.out.printf("%s begins: ", testName);
        }
        System.out.print((nextMidNode(pNode) == expected) ? "Passed.\n" : "FAILED.\n");
    }


    //            8
    //        6      10
    //       5 7    9  11
    public static void test1_7() {
        System.out.println("The tree:\n" +
                "    //            8\n" +
                "    //        6      10\n" +
                "    //       5 7    9  11"
        );

        Node pNode8 = new Node(8);
        Node pNode6 = new Node(6);
        Node pNode10 = new Node(10);
        Node pNode5 = new Node(5);
        Node pNode7 = new Node(7);
        Node pNode9 = new Node(9);
        Node pNode11 = new Node(11);

        connectTreeNodes(pNode8, pNode6, pNode10);
        connectTreeNodes(pNode6, pNode5, pNode7);
        connectTreeNodes(pNode10, pNode9, pNode11);

        test("Test1", pNode8, pNode9);
        test("Test2", pNode6, pNode7);
        test("Test3", pNode10, pNode11);
        test("Test4", pNode5, pNode6);
        test("Test5", pNode7, pNode8);
        test("Test6", pNode9, pNode10);
        test("Test7", pNode11, null);
    }

    //            5
    //          4
    //        3
    //      2
    public static void test8_11() {
        System.out.println("The tree:\n" +
                "    //          4\n" +
                "    //        3\n" +
                "    //      2"
        );

        Node pNode5 = new Node(5);
        Node pNode4 = new Node(4);
        Node pNode3 = new Node(3);
        Node pNode2 = new Node(2);

        connectTreeNodes(pNode5, pNode4, null);
        connectTreeNodes(pNode4, pNode3, null);
        connectTreeNodes(pNode3, pNode2, null);

        test("Test8", pNode5, null);
        test("Test9", pNode4, pNode5);
        test("Test10", pNode3, pNode4);
        test("Test11", pNode2, pNode3);
    }

    //        2
    //         3
    //          4
    //           5
    public static void test12_15() {
        System.out.println("The tree:\n" +
                "    //        2\n" +
                "    //         3\n" +
                "    //          4\n" +
                "    //           5"
        );
        Node pNode2 = new Node(2);
        Node pNode3 = new Node(3);
        Node pNode4 = new Node(4);
        Node pNode5 = new Node(5);

        connectTreeNodes(pNode2, null, pNode3);
        connectTreeNodes(pNode3, null, pNode4);
        connectTreeNodes(pNode4, null, pNode5);

        test("Test12", pNode5, null);
        test("Test13", pNode4, pNode5);
        test("Test14", pNode3, pNode4);
        test("Test15", pNode2, pNode3);
    }

    //   16
    public static void test16() {
        System.out.println("The tree:\n" +
                "//   16"
        );
        Node pNode5 = new Node(5);
        test("Test16", pNode5, null);
    }

    public static void main(String[] args) {
        test1_7();
        System.out.println();
        test8_11();
        System.out.println();
        test12_15();
        System.out.println();
        test16();
    }


}
