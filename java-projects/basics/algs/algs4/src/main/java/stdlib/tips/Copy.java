package stdlib.tips;

import stdlib.lib.BinaryStdIn;
import stdlib.lib.BinaryStdOut;

/******************************************************************************
 *  Compilation:  javac-introcs Copy.java
 *  Execution:    java-introcs Copy < file
 *  Dependencies: BinaryStdIn.java BinaryStdOut.java
 *
 *  Reads in a binary file from standard input and writes it to standard output.
 *
 *  % java-introcs Copy < mandrill.jpg > copy.jpg
 *
 *  % diff mandrill.jpg copy.jpg
 *
 ******************************************************************************/

public class Copy {

    public static void main(String[] args) {
        while (!BinaryStdIn.isEmpty()) {
            char c = BinaryStdIn.readChar();
            BinaryStdOut.write(c);
        }
        BinaryStdOut.flush();
    }
}