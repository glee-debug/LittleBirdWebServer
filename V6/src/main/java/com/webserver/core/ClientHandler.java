package com.webserver.core;

import com.webserver.http.HttpRequest;

import java.io.*;
import java.net.Socket;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
            //1解析请求
            HttpRequest hr = new HttpRequest(socket);
            //2读取所有消息头
            /*
            响应的大致内容:
            HTTP/1.1 200 OK(CRLF)
            Content-Type: text/html(CRLF)
            Content-Length: 2546(CRLF)(CRLF)
            1011101010101010101......
             */
            //3解析消息正文
            File file = new File(".\\webapps\\mybolg\\hello.html");

            OutputStream os = socket.getOutputStream();
            //状态行
            String line = "HTTP/1.1 200 OK";
            byte[] data = line.getBytes("ISO8859-1");
            os.write(data);
            os.write(13);
            os.write(10);

            // 响应头
            line = "Content-Type: text/html";
            data = line.getBytes("ISO8859-1");
            os.write(data);
            os.write(13);
            os.write(10);

            line = "Content-Length: "+file.length();
            data = line.getBytes("ISO8859-1");
            os.write(data);
            os.write(13);
            os.write(10);

            os.write(13);
            os.write(10);

            // 响应正文
            FileInputStream fis = new FileInputStream(file);
            int len;
            byte[] buf = new byte[1024*10];
            while ((len = fis.read(buf))!=-1){
                os.write(buf,0,len);
            }
            System.out.println("响应发送完毕!");

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
