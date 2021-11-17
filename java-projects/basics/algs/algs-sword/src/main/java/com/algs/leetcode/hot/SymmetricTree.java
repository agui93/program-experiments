package com.algs.leetcode.hot;

import java.util.LinkedList;
import java.util.Queue;

/**
 * https://leetcode-cn.com/problems/symmetric-tree/
 * 考察:递归 二叉树遍历
 */
public class SymmetricTree {

    public static class TreeNode {
        int val;
        TreeNode left;
        TreeNode right;

        TreeNode(int x) {
            val = x;
        }
    }


    //递归的解法
    public boolean isSymmetricByRecursion(TreeNode p1, TreeNode p2) {
        if (p1 == null && p2 == null) {
            return true;
        }
        if (p1 == null || p2 == null) {
            return false;
        }
        return p1.val == p2.val
                && isSymmetricByRecursion(p1.left, p2.right)
                && isSymmetricByRecursion(p1.right, p2.left);
    }


    //BFS队列遍历 [9,-42,-42,null,76,76,null,null,13,null,13]
    public boolean isSymmetricByBFS(TreeNode root) {
        if (root == null) {
            return true;
        }

        Queue<TreeNode> queue = new LinkedList<>();
        queue.add(root.left);
        queue.add(root.right);
        while (!queue.isEmpty()) {
            TreeNode t1 = queue.poll();
            TreeNode t2 = queue.poll();
            if (t1 == null && t2 == null) {
                continue;       //该步骤
            }
            if (t1 == null || t2 == null) {
                return false;
            }
            if (t1.val != t2.val) {
                return false;
            }

            queue.add(t1.left);
            queue.add(t2.right);
            queue.add(t1.right);
            queue.add(t2.left);
        }

        return true;
    }

    //todo DFS栈遍历
    public boolean isSymmetricByDFS() {
        return false;
    }

    public boolean isSymmetric(TreeNode root) {
//        return isSymmetricByRecursion(root, root);
        return isSymmetricByBFS(root);
    }


    //先序 中序 后序遍历的方式进行对比



    public static void main(String[] args) {
        //todo 模拟二叉树的输入和输出
    }

}
