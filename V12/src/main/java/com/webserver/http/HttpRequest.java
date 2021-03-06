package com.webserver.http;

import java.io.IOException;
import java.io.InputStream;
import java.net.Socket;
import java.util.HashMap;
import java.util.Map;

/**
 * 请求对象
 * 该类的每一个实例用于表示客户端发送过来的一个http请求内容
 * 每个请求有三部分构成
 * 请求行，消息头，消息正文
 */
public class HttpRequest {
    private Socket socket;
    //请求行相关信息
    private String method;//请求方式
    private String uri;//抽象路径
    private String protocol;//协议版本

    //消息头相关信息
    private Map<String,String> headers = new HashMap<>();

    public String getMethod() {
        return method;
    }

    public String getUri() {
        return uri;
    }

    public String getProtocol() {
        return protocol;
    }

    public String getHeaders(String headName) {

        return headers.get(headName);
    }

    //消息正文相关信息

    /**
     * httpRequest的实例化过程就是解析请求的过程
     * @param socket
     */
    public HttpRequest(Socket socket) throws EmptyRequestException {
        this.socket = socket;
        //1解析请求
        parseRequestLine();
        //2读取所有消息头
        parseHeaders();
        //3解析消息正文
        parseContent();
    }

    /**解析请求行*/
    private void parseRequestLine() throws EmptyRequestException {
        System.out.println("HTTPRequest：开始解析请求行...");
        try {
            //读取请求行
            String line = readLine();
            if(line.isEmpty()){
                throw new EmptyRequestException();
            }
            System.out.println("请求行:" + line);

            //http://localhost:8088/index.html
            //将请求行按照空格拆分为三部分，并分别赋值给上述变量
            String[] data = line.split("\\s");
            method = data[0];
            /*
                下面的代码可能在运行后浏览器发送请求拆分时，在这里赋值给uri时出现
                字符串下标越界异常，这是由于浏览器发送了空请求，原因与常见错误5一样。
                上面判空操作已经解决了问题，所以数组下标越界异常不会出现了
             */
            uri = data[1];
            protocol = data[2];
            System.out.println("method:" + method);//method:GET
            System.out.println("uri:" + uri);//uri:/index.html
            System.out.println("protocol:" + protocol);//protocol:HTTP/1.1
        }catch (IOException e){
            e.getStackTrace();
        }

        System.out.println("HTTPRequest：请求行解析完毕");
    }
    /**解析消息头*/
    private void parseHeaders(){
        System.out.println("HTTPRequest：开始解析消息头...");
        try {//下面读取每一个消息头后，将消息头的名字作为key，消息头的值作为value保存到headers中
            while (true) {
                String line = readLine();
                //读取消息头时，如果只读取到了回车加换行符就应当停止读取
                if (line.isEmpty()) {//readLine单独读取CRLF返回值应当是空字符串
                    break;
                }
                System.out.println("消息头:" + line);
                //将消息头按照冒号空格拆分并存入到headers这个Map中保存
                String[] data = line.split(":\\s");
                headers.put(data[0], data[1]);
            }
            System.out.println("headers:"+headers);
        }catch (IOException e){
            e.getStackTrace();
        }

        System.out.println("HTTPRequest：消息头解析完毕");
    }
    /**解析消息正文*/
    private void parseContent(){
        System.out.println("HTTPRequest：开始解析消息正文...");

        System.out.println("HTTPRequest：消息正文解析完毕");
    }


    public String readLine() throws IOException {
        /*
        当socket对象相同时，无论调用多少册getInputStream方法，获取回来的输入流
        总是同一个流，输出流也是一样的
         */
        InputStream in = socket.getInputStream();
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
        return sb.toString().trim();
    }
}
