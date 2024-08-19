import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;

public class Main {
  public static void main(String[] args) {
    // You can use print statements as follows for debugging, they'll be visible when running tests.
    System.out.println("Logs from your program will appear here!");
    final int portNumber = 4221;
//     Uncomment this block to pass the first stage

     try (ServerSocket serverSocket = new ServerSocket(portNumber)) {

       // Since the tester restarts your program quite often, setting SO_REUSEADDR
       // ensures that we don't run into 'Address already in use' errors
       serverSocket.setReuseAddress(true);

       Socket clientSocket = serverSocket.accept(); // Wait for connection from client.

       PrintWriter  out = new PrintWriter(clientSocket.getOutputStream(), true);
       BufferedReader  in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));

       out.println("HTTP/1.1 200 OK\r\n\r");


         System.out.println("accepted new connection");
     } catch (IOException e) {
       System.out.println("IOException: " + e.getMessage());
     }
  }
}
