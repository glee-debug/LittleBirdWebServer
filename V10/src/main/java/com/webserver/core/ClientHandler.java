package com.webserver.core;

import com.webserver.http.EmptyRequestException;
import com.webserver.http.HttpRequest;
import com.webserver.http.HttpResponse;

import java.io.*;
import java.net.Socket;
import java.util.HashMap;
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
    private static Map<String,String> mime = new HashMap<>();
    static{
        mime.put("html","text/html");
        mime.put("HTML","text/html");
        mime.put("js","application/javascript");
        mime.put("css","text/css");
        mime.put("png","img/png");
        mime.put("jpg","img/jpeg");
        mime.put("gif","img/gif");
        mime.put("defaultType","font/woff2");
    }

    public ClientHandler(Socket socket) {
        this.socket = socket;
    }

    public void run() {
        //http协议要求 一问一答
        try{
            //1解析请求
            HttpRequest hr = new HttpRequest(socket);
            //2读取所有消息头
            //3解析消息正文
            String path = hr.getUri();
            HttpResponse hp = new HttpResponse(socket);
            File file = new File("."+path);
            if (!file.exists() || file.isDirectory()){
                file = new File("./webapps/root/404.html");
                hp.setStatusCode(404);
                hp.setStatusReason("NotFound");
            }
            String key = parsePath(file.getName());
            hp.putHeaders("Content-Type", mime.get(key));
            hp.putHeaders("Content-Length",String.valueOf(file.length()));
            hp.putHeaders("Server","LittleBirdWebServer");
            hp.setFile(file);
            hp.flush();

            System.out.println("OK");

        }catch (EmptyRequestException e){
            //什么都不用做，上面抛出该异常就是为了忽略处理和响应操作
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

    public String parsePath(String path){
        int last = path.lastIndexOf(".")+1;
        String fileType = path.substring(last,path.length());
        if (!mime.keySet().contains(fileType)) {
            fileType = "defaultType";
        }
        return fileType;
    }
}
