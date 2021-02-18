package com.webserver.core;

import java.io.IOException;
import java.io.InputStream;
import java.net.Socket;

/**
 * 负责与指定客户端进行HTTP交互
 * HTTP协议要求与客户端的交互规则采取一问一答的方式，因此，处理客户端交互以3步形式完成
 * 1.解析请求（一问）
 * 2.处理请求
 * 3.发送响应（一答）
 */
public class ClientHandler implements Runnable{
    private Socket socket;

    public ClientHandler(Socket socket) {
        this.socket = socket;
    }

    public void run() {
        //http协议要求 一问一答
        try{
            //解析请求
            InputStream in = socket.getInputStream();

            // 读取客户端发过来的请求信息，英文都是用一个字节存储
            int d;
            char pre=' ';
            char cur=' ';
            StringBuilder sb = new StringBuilder();
            while ((d = in.read())!=-1) {
                cur = (char) d;
                if (pre == 13 && cur == 10){
                    break;
                }
                sb.append(cur);
                pre = cur;
            }
            String line = sb.toString().trim();
            System.out.println(line);
            //处理请求
            String method;
            String uri;
            String protocol;
            String[] data = line.split(" ");//空格\\s
            method = data[0];
            uri = data[1];
            protocol = data[2];
            System.out.println("method:"+method);
            System.out.println("uri:"+uri);
            System.out.println("protocal:"+protocol);
            //发送响应


        }catch(Exception e){
            e.getStackTrace();
        }finally {
            //处理完毕与客户端断开连接
            try {
                socket.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

    }
}
