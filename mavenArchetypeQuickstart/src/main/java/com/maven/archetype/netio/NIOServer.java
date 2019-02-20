package com.maven.archetype.netio;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.*;
import java.util.Iterator;
import java.util.Set;

public class NIOServer {
    //Selector Channel(ServerSocketChannel SocketChannel)  Buffer
    private int port = 8000;
    private InetSocketAddress address = null;
    private Selector selector;

    public NIOServer(int port) {
        this.port = port;
        address = new InetSocketAddress(this.port);
        try {
            ServerSocketChannel server = ServerSocketChannel.open();  //new ServerSocket();
            server.socket().bind(address);
            //服务端通道设置成非阻塞的模式
            server.configureBlocking(false); //必须设置通道为 非阻塞，才能向 Selector 注册。
            selector = Selector.open();
            //每当有客户端连接上来的时候，默认它是已经连接上的  它的状态就是 Connected
            server.register(selector, SelectionKey.OP_ACCEPT);
            System.out.println("服务器启动成功：" + this.port);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    //Selector开始轮询
    public void listen() {

        try {
            while (true) {
                int wait = this.selector.select(); //accept() 阻塞的 select()也是阻塞的
                if (wait == 0) {
                    continue;
                }
                Set<SelectionKey> keys = this.selector.selectedKeys();
                Iterator<SelectionKey> i = keys.iterator();
                while (i.hasNext()) {
                    SelectionKey key = i.next();
                    //针对每一个客户端进行相应的操作
                    process(key);
                    i.remove();
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

    }
    //处理每一个客户端 key
    public void process(SelectionKey key) throws IOException {
        ByteBuffer buffer = ByteBuffer.allocate(1024);
        if (key.isAcceptable()) { //每一个新建连接都会先走这
            ServerSocketChannel server = (ServerSocketChannel) key.channel();
            SocketChannel client = server.accept();
            client.configureBlocking(false);
            //客户端一量连接上来 读写
            //往这个selector上注册 key READ 接下来可以读取
            client.register(selector, SelectionKey.OP_READ);
        } else if (key.isReadable()) {
            SocketChannel client = (SocketChannel) key.channel();
            int len = client.read(buffer);
            //读取完成了
            if (len > 0) {
                buffer.flip(); //固定位置 数据已经读取完，记录新的位置信息
                String content = new String(buffer.array(), 0, len);
                client.register(selector, SelectionKey.OP_WRITE);
                System.out.println(content);
            }
            buffer.clear();
        } else if (key.isWritable()) {
            SocketChannel client = (SocketChannel) key.channel();
            client.write(buffer.wrap("Hello Wold".getBytes()));
            client.close();
        }
    }


    public static void main(String[] args) {
        new NIOServer(8000).listen();
    }
}
