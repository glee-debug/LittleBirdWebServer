package WebServer.core;

import java.net.Socket;

public class Client implements Runnable{
    private Socket socket;

    public Client(Socket socket) {
        this.socket = socket;
    }

    @Override
    public void run() {

    }
}
