package com.webserver.core;

import com.webserver.http.HttpRequest;

import java.io.IOException;
import java.io.InputStream;
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
            //2读取所有消息头
            //3解析消息正文
            HttpRequest hr = new HttpRequest(socket);

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
