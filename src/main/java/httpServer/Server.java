package httpServer;

import org.apache.commons.cli.CommandLine;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class Server {

    private ServerSocket serverSocket;
    private boolean run;
    private int port;
    CommandLine commandLine;

    public Server(int port, CommandLine commandLine) throws IOException{
        this.port = port;
        this.serverSocket = new ServerSocket(port);
        this.serverSocket.setReuseAddress(true);
        this.commandLine = commandLine;
    }


    public void start() {
        this.run = true;
        System.out.println("Logs from your program will appear here!");
        System.out.println("Server is listening on port: " + this.port);

        try {
            while (run) {
                Socket clientSocket = this.serverSocket.accept();
                System.out.println("New Client Connected! "+ clientSocket.getPort());
//                new Thread(new Client(clientSocket, commandLine)).start(); // Put to a new thread.
                new Client(clientSocket, commandLine).run();
            }
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
    }

}
