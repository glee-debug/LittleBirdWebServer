package WebServer.core;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class WebServer {
    private ServerSocket serverSocket;

    public WebServer(){
        try {
            serverSocket = new ServerSocket(8089);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void start(){
        try {
            while (true){
                Socket socket = serverSocket.accept();
                Client client = new Client(socket);
                Thread t = new Thread(client);
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
