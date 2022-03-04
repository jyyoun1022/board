package org.zerock.board;

import java.util.Arrays;

public class Lab {

    public static void main(String[] args) {

        String str = "     Welcome Hello World    ";

        int length = str.length();
        System.out.println("length() = " + length);

        String trim = str.trim();
        System.out.println("trim() = " + trim);

        int length1 = str.trim().length();
        System.out.println("trim().length() = " + length1);

        String[] split = str.split("");
        System.out.println("split() = " + Arrays.toString(split));

        String[] split2 = str.split(" ");
        System.out.println("split2 = " + Arrays.toString(split2));

        String[] split1 = str.trim().split("");
        System.out.println("Arrays.toString(split1) = " + Arrays.toString(split1));

    }
}
