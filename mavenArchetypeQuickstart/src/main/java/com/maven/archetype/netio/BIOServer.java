package com.maven.archetype.netio;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class BIOServer {

    private static Integer port = 8000;

    public static void main(String[] args) {
//        server1();
//        server2();
        server3();
    }


    //1单线程
    public  static void server1() {
        ServerSocket server = null;
        Socket socket = null;
        InputStream in = null;
        OutputStream out = null;
        try {
            server = new ServerSocket(port);
            System.out.println("服务端1启动成功，监听端口为" + port + "，等等客户端连接...");
            socket = server.accept();  //阻塞 客户端
            //读取客户端的数据
            in = socket.getInputStream();
            //向客户端写数据
            out = socket.getOutputStream();
            byte[] buffer = new byte[1024];
            int len = 0;
            while ((len = in.read(buffer)) > 0) {
                String fromClient = new String(buffer, 0, len);
                System.out.println(fromClient);
                out.write(("from single thread server: " + fromClient).getBytes());
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    //2 多线程
    public static void server2() {
        ServerSocket server = null;
        try {
            server = new ServerSocket(port);
            System.out.println("服务端2启动成功，监听端口为" + port + "，等等客户端连接...");
            while (true) {
                Socket socket = server.accept();
                //针对每个连接创建一个线程，去处理IO操作
                new Thread(new ServerHandler(socket)).start();
                //优化 不马上创建线程，而是先记录每个socket的状态 [selector类 channel类 Buffer缓冲区]，等真正有 io 操作的时候再创建线程
                //map.put(socketA, "connected");  //jdk实现了一个api，不用自己实现了 新名称 Non-Blocking IO 同步非阻塞IO
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    //3线程池
    public static void server3() {
        ServerSocket server = null;
        ExecutorService executorService = Executors.newFixedThreadPool(10);
        try {
            server = new ServerSocket(port);
            System.out.println("服务端3启动成功，监听端口为" + port + "，等等客户端连接...");
            while (true) {
                Socket socket = server.accept();
                //使用线程池中的线程去执行每个对应的任务
                executorService.execute(new ServerHandler(socket));
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
