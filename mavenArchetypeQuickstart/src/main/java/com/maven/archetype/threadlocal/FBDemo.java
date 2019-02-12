package com.maven.archetype.threadlocal;

/*
https://blog.csdn.net/y4x5M0nivSrJaY3X92c/article/details/81124944
ThreadLocal 中使用了斐波那契散列法，来保证哈希表的离散度。而它选用的乘数值即是2^32 * 黄金分割比。
 0x61c88647 与一个神奇的数字产生了关系，它就是 (Math.sqrt(5) - 1)/2。也就是传说中的黄金比例 0.618（0.618 只是一个粗略值），即0x61c88647 = 2^32 * 黄金分割比。
 */

public class FBDemo {

    private static final int HASH_INCREMENT = 0x61c88647; //1100001 11001000 10000110 01000111   //1640531527

    public static void main(String[] args) {

        magicHash(16);
//        magicHash(32);
    }

    private static void magicHash(int size) {
        int hashCode = 0;
        for (int i = 0; i < size; i++) {
            hashCode = i * HASH_INCREMENT + HASH_INCREMENT;
            System.out.print((hashCode&(size-1)) + " ");
        }
        System.out.println();

    }


}
