package com.maven.archetype.radix;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
/*
//十六进制和二进制的对应关系表
// 0（0000），1（0001），2（0010），3（0011），4（0100），5（0101），6（0110），7（0111），8（1000），9（1001），A（1010），B（1011），C（1100），D（1101），E（1110），F（1111）
//八进制和二进制的对应关系表
// 0（000），1（001），2（010），3（011），4（100），5（101），6（110），7（111）
//Java基本数据类型与表示范围(boolean忽略)
//     byte(整型)：8位，     short(整型)：16位，       char(字符型)：16位，     int(整型)：32位，    float(浮点型单精度)：32位，     long(整型)：64位，      double(浮点型双精度)：64位。

 */

public class radixDemo {
    private static final int HASH_INCREMENT = 0x61c88647;

    public static void main(String[] args) {
        String hasIncString = Integer.toHexString(HASH_INCREMENT);
        assertEquals("61c88647", hasIncString);
        //字符串16进制转10进制
        System.out.println(hex2decimal(hasIncString));  //1640531527
        //字符串16进制转2进制
        System.out.println(hasIncString);
        System.out.println(hexStringToByte(hasIncString));
        byte b = Byte.MAX_VALUE;
        assertEquals(127, b);
        assertTrue(127 == b);

    }


    /**
     * 字符串16进制转10进制
     *
     * @param hex
     * @return
     */
    public static int hex2decimal(String hex) {
        return Integer.parseInt(hex, 16);
    }

    /**
     * 字符串16进制转2进制
     *
     * @param hex
     * @return
     */
    private static String hexStringToByte(String hex) {
        int i = Integer.parseInt(hex, 16);
        String str2 = Integer.toBinaryString(i);
        return str2;
    }
}
