package cz.skalicky.javaplayground;

import org.apache.commons.lang3.StringUtils;

public class BitwiseOperators {

    public static void main(String[] args) {

        System.out.println(StringUtils.leftPad("-1", 33) + " = "
                + StringUtils.leftPad(Integer.toBinaryString(-1), 33));
        int x = -1;
        for (int i = 0; i < 40; ++i) {

            System.out.println(StringUtils.leftPad(x + " << 1 .... " + (x << 1), 33) + " = "
                    + StringUtils.leftPad(Integer.toBinaryString(x << 1), 33));
            x = x << 1;
        }

        System.out.println("----------------------------------------------------");
        System.out.println(StringUtils.leftPad("-1", 33) + " = "
                + StringUtils.leftPad(Integer.toBinaryString(-1), 33));
        x = -1;
        for (int i = 0; i < 40; ++i) {

            System.out.println(StringUtils.leftPad(x + " >> 1 .... " + (x >> 1), 33) + " = "
                    + StringUtils.leftPad(Integer.toBinaryString(x >> 1), 33));
            x = x >> 1;
        }

        System.out.println("----------------------------------------------------");
        System.out.println(StringUtils.leftPad("-1", 33) + " = "
                + StringUtils.leftPad(Integer.toBinaryString(-1), 33));
        x = -1;
        for (int i = 0; i < 40; ++i) {

            System.out.println(StringUtils.leftPad(x + " >>> 1 .... " + (x >>> 1), 33) + " = "
                    + StringUtils.leftPad(Integer.toBinaryString(x >>> 1), 33));
            x = x >>> 1;
        }
    }

}
