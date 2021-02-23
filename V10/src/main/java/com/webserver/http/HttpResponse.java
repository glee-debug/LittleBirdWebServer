package com.webserver.http;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.net.Socket;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

/**
 *
 *  响应的大致内容:
 *  HTTP/1.1 200 OK(CRLF)
 *  Content-Type: text/html(CRLF)
 *  Content-Length: 2546(CRLF)(CRLF)
 *  1011101010101010101......
 */
public class HttpResponse {
    private Socket socket;
    private String protocol = "HTTP/1.1";//协议版本
    private int statusCode = 200;//状态代码
    private String statusReason = "OK";//状态描述
    private File file;//响应页面
    //输出流
    private OutputStream out;
    //响应头
    private Map<String,String> headers = new HashMap<>();

    //请求文件
    private String path;

    public HttpResponse(Socket socket) {
        this.socket = socket;
    }

    public void flush(){
        //发送状态行
        sendStatusline();
        //发送响应头
        sendResponseHeaders();
        //发送响应正文
        sendResponseContent();
    }

    public void sendStatusline(){
        System.out.println("正在发送状态行...");
        try{
            String line = protocol + " " + getStatusCode() + " " + getStatusReason();
            System.out.println("状态行："+line);
            println(line);
        }catch (IOException e){
            e.getStackTrace();
        }
        System.out.println("状态行发送完毕！");
    }

    public void sendResponseHeaders(){
        System.out.println("正在发送响应头...");
        try{
//            Set<Map.Entry<String,String>> entrySet = headers.entrySet();
//            for (Map.Entry<String,String> e:entrySet){
//                println(e.getKey()+": "+e.getValue());
//            }
            //hashmap的lambda表达式遍历写法
            headers.forEach(
                    (k,v)->{
                        try {
                            println(k+": "+v);
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
            );

            out.write(13);
            out.write(10);
        }catch (IOException e){
            e.getStackTrace();
        }
        System.out.println("响应头发送完毕！");
    }

    public void sendResponseContent(){
        System.out.println("正在发送响应正文...");
        try(FileInputStream fis = new FileInputStream(getFile())){
            int len;
            byte[] data = new byte[1024*10];
            while ((len = fis.read(data))!=-1){
                out.write(data,0,len);
            }

        }catch (IOException e){
            e.getStackTrace();
        }

        System.out.println("响应正文发送完毕！");
    }
    private void println(String line) throws IOException{
        out = socket.getOutputStream();
        out.write(line.getBytes("ISO8859-1"));
        out.write(13);
        out.write(10);
    }

    public File getFile() {
        return file;
    }

    public void setFile(File file) {
        this.file = file;
    }

    public Map<String, String> getHeaders() {
        return headers;
    }

    public void putHeaders(String key,String value) {
        headers.put(key,value);
    }

    public int getStatusCode() {
        return statusCode;
    }

    public void setStatusCode(int statusCode) {
        this.statusCode = statusCode;
    }

    public String getStatusReason() {
        return statusReason;
    }

    public void setStatusReason(String statusReason) {
        this.statusReason = statusReason;
    }
}
