package ConcurrentConnections;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.text.MessageFormat;
import java.util.HashMap;
import java.util.Map;

public class Server {

    private ServerSocket serverSocket;
    private boolean run;
    private int port;

    public Server(int port) throws IOException{
        this.port = port;
        this.serverSocket = new ServerSocket(port);
        this.serverSocket.setReuseAddress(true);
    }


    public void start() {
        this.run = true;
        System.out.println("Logs from your program will appear here!");
        System.out.println("Server is listening on port: " + this.port);

        try {
            while (run) {
                Socket clientSocket = this.serverSocket.accept();
                System.out.println("New Client Connected! "+ clientSocket.getPort());
                new Thread(new Client(clientSocket)).start(); // Put to a new thread.
            }
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
    }

}
