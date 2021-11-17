package com.algs.sword;

/**
 * // 面试题5：替换空格
 * // 题目：请实现一个函数，把字符串中的每个空格替换成"%20"。例如输入“We are happy.”，
 * // 则输出“We%20are%20happy.”。
 *
 * @author agui93
 * @since 2020/7/11
 */
public class ReplaceSpaces {

    //todo by 对比string.replace*方法
    public static String replaceSpace(String s) {
        if (s == null || s.length() == 0) {
            return "";
        }
        int spaceNum = 0;
        for (int i = 0; i < s.length(); i++) {
            if (s.charAt(i) == ' ') {
                spaceNum++;
            }
        }
        if (spaceNum == 0) {
            return s;
        }

        char[] chars = new char[s.length() - spaceNum + spaceNum * 3];
        int j = chars.length;
        for (int i = s.length() - 1; i >= 0; i--) {
            if (s.charAt(i) == ' ') {
                chars[--j] = '0';
                chars[--j] = '2';
                chars[--j] = '%';
            } else {
                chars[--j] = s.charAt(i);
            }

        }

        return new String(chars);
    }

    public static void useCase(String s) {
        System.out.println("origin str:" + s + "$");
        System.out.println("after replace:" + replaceSpace(s));
        System.out.println();
        System.out.println();
    }

    public static void main(String[] args) {
        useCase("We are happy.");
        useCase(" We are happy.");
        useCase("We are happy. ");
        useCase("We are  happy. ");
        useCase(" ");
        useCase("  ");
        useCase("");
        useCase(null);

    }
}
