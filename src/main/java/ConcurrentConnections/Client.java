package ConcurrentConnections;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.text.MessageFormat;
import java.util.HashMap;
import java.util.Map;

public class Client implements Runnable{
    
    private Socket clientSocket;

    private PrintWriter out;
    private BufferedReader in;
    
    public Client(Socket clientSocket){
        this.clientSocket = clientSocket;
    }

    @Override
    public void run() {
        Map<String, String> response = new HashMap<>();
        response.put("ok", "HTTP/1.1 200 OK\r\n\r\n");
        response.put("notFound", "HTTP/1.1 404 Not Found\r\n\r\n");
        response.put("echo", "HTTP/1.1 200 OK\r\nContent-Type: text/plain\r\nContent-Length: {0}\r\n\r\n{1}");
        try {
            PrintWriter out = new PrintWriter(this.clientSocket.getOutputStream(), true);
            BufferedReader in = new BufferedReader(new InputStreamReader(this.clientSocket.getInputStream()));
            String input = in.readLine();
            in.readLine();

            String userAgent = in.readLine();
            String[] lines = input.split(" ");
            if(lines[1].equals("/")){
                this.clientSocket.getOutputStream().write(response.get("ok").getBytes());
            }
            else if(lines[1].matches("^/echo/.*$")) {
//                System.out.println("inside");

                String[] path = lines[1].split("/");
//                System.out.println(path[0]+" | "+path[1]+" | "+path[2]);
                if((path.length==3) && (path[1].equals("echo"))) {
                    String pathInput = path[2];
                    String output = MessageFormat.format(response.get("echo"),pathInput.length(), pathInput);
//                    System.out.println(output);
                    this.clientSocket.getOutputStream().write(output.getBytes());
                }
            }
            else if(lines[1].equals("/user-agent")) {
                String outputUserAgent = userAgent.split(" ")[1];
                String returnString  =MessageFormat.format(response.get("echo"), outputUserAgent.length(), outputUserAgent);
                System.out.println(returnString);
                this.clientSocket.getOutputStream().write(returnString.getBytes());
            }
            else {
                this.clientSocket.getOutputStream().write(response.get("notFound").getBytes());
            }

            out.flush();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

    }


}
