package com.maven.archetype.command;

import java.io.*;
import java.util.zip.GZIPOutputStream;

public class Mianshi {

    public static void main(String[] args) {
        //String s;
        //System.out.println("s=" + s); //由于s没有初始化，代码不能编译通过

        //检查型异常（Checked Exception）   FileNotFoundException
        //非检查型异常（Unchecked Exception）数组越界、访问null对象，这种错误你自己是可以避免的。编译器不会强制你检查这种异常。

        //System.out.println("5" + 2);  //输出 52

        //testStream();
    }

    public static void testStream() {
        try {
            BufferedWriter bw = new BufferedWriter(new FileWriter("a.txt"));
            BufferedReader br = new BufferedReader(new InputStreamReader(new FileInputStream("a.txt"))); //new FileInputStream("a.txt")

            GZIPOutputStream gos = new GZIPOutputStream(new FileOutputStream("a.zip"));
            ObjectInputStream ois = new ObjectInputStream(new FileInputStream("a.dat"));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
