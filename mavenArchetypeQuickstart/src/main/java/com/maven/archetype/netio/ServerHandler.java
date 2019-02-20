package com.maven.archetype.netio;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;

public class ServerHandler implements Runnable{

    private Socket socket;


    public ServerHandler(Socket socket) {
        this.socket = socket;
    }

    @Override
    public void run() {
        InputStream in = null;
        OutputStream out = null;
        try {
            in = socket.getInputStream();
            out = socket.getOutputStream();
            byte [] buffer = new byte[1024];
            int len = 0;
            //读取客户端的数据
            while((len = in.read(buffer)) > 0 ) {
                String fromClient = new String(buffer, 0, len);
                System.out.println(fromClient);
                out.write(("from multi thread server: " + fromClient).getBytes());
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

    }
}
