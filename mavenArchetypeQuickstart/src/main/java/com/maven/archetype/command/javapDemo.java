package com.maven.archetype.command;

/*
 javap -c javapDemo.class  javap 指令集 http://www.firemail.wang:8088/forum.php?mod=viewthread&tid=9655
 */

public class javapDemo {

    private static final int _P_1 = 1;
    public static final int _P_2 = 2;

    public static void main(String[] args) {
        int m = 0, n = 0;
        for (int i = 0; i < 10; i++) {
            m = m++;
            n = ++n;
        }
        System.out.println("m = " + m);
        System.out.println("n = " + n);
    }
}
