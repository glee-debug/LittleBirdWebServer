package com.webserver.core;

import com.webserver.http.HttpContext;

import java.io.File;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Date;

/**
 * web容器的作用
 * 1.web容器是一个web服务端程序，负责与客户端（通常是浏览器）进行交互
 * 2.完成与客户端的tcp
 */
public class WebServer{
    private ServerSocket serverSocket;

    public WebServer() {
        try {
            System.out.println("正在启动服务器...");
            serverSocket = new ServerSocket(8088);
            System.out.println("服务器启动完毕！");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void start(){
        try {
            while (true){
                System.out.println("等待客户端连接...");
                Socket socket = serverSocket.accept();
                System.out.println("一个客户端连接了");
                //启动一个线程与该客户端交互
                ClientHandler ch = new ClientHandler(socket);
                Thread t = new Thread(ch);
                t.start();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        WebServer webServer = new WebServer();
        webServer.start();
    }
}
