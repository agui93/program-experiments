package com.algs.sword;

/**
 * // 面试题7：重建二叉树
 * // 题目：输入某二叉树的前序遍历和中序遍历的结果，请重建出该二叉树。假设输
 * // 入的前序遍历和中序遍历的结果中都不含重复的数字。例如输入前序遍历序列{1,
 * // 2, 4, 7, 3, 5, 6, 8}和中序遍历序列{4, 7, 2, 1, 5, 3, 8, 6}，则重建出
 * // 图2.6所示的二叉树并输出它的头结点。
 *
 * @author agui93
 * @since 2020/7/11
 */
public class ConstructBinaryTree {

    public static class Node {
        int val;
        private Node left;
        private Node right;

        public Node(int val) {
            this.val = val;
        }
    }

    public static Node construct(int[] preArrays, int[] middleArrays) {
        //数据校验
        return root(preArrays, 0, preArrays.length, middleArrays, 0, middleArrays.length);
    }


    //start包含,end不包含
    public static Node root(int[] preArrays, int preStart, int preEnd, int[] mArrays, int mStart, int mEnd) {
        if (preEnd - preStart <= 0) {
            return null;
        }
        if (preEnd - preStart == 1) {
            return new Node(preArrays[preStart]);
        }

        Node root = new Node(preArrays[preStart]);

        int mRootIndex = 0;
        for (int i = mStart; i < mEnd; i++) {
            if (root.val == mArrays[i]) {
                mRootIndex = i;
            }
        }

        root.left = root(preArrays, preStart + 1, preStart + 1 + mRootIndex - mStart, mArrays, mStart, mRootIndex);
        root.right = root(preArrays, preStart + 1 + mRootIndex - mStart, preEnd, mArrays, mRootIndex + 1, mEnd);

        return root;
    }


    public static void main(String[] args) {
        int[] preArrays = {1, 2, 4, 7, 3, 5, 6, 8};
        int[] mArrays = {4, 7, 2, 1, 5, 3, 8, 6};

        Node root = construct(preArrays, mArrays);

    }

}
