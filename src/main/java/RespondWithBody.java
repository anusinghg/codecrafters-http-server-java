import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.text.MessageFormat;
import java.util.HashMap;
import java.util.Map;

public class RespondWithBody {
    public static void main(String[] args) {
        // You can use print statements as follows for debugging, they'll be visible when running tests.
        System.out.println("Logs from your program will appear here!");
        final int portNumber = 4221;
//     Uncomment this block to pass the first stage

        Map<String, String> response = new HashMap<>();
        response.put("ok", "HTTP/1.1 200 OK\r\n\r\n");
        response.put("notFound", "HTTP/1.1 404 Not Found\r\n\r\n");
        response.put("echo", "HTTP/1.1 200 OK\r\nContent-Type: text/plain\r\nContent-Length: {0}\r\n\r\n{1}");

        try (ServerSocket serverSocket = new ServerSocket(portNumber)) {

            // Since the tester restarts your program quite often, setting SO_REUSEADDR
            // ensures that we don't run into 'Address already in use' errors
            serverSocket.setReuseAddress(true);

            Socket clientSocket = serverSocket.accept(); // Wait for connection from client.

            PrintWriter out = new PrintWriter(clientSocket.getOutputStream(), true);
            BufferedReader in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
            String input = in.readLine();

            String[] lines = input.split(" ");
//            System.out.println(lines[1]);
            if(lines[1].equals("/")){
                clientSocket.getOutputStream().write(response.get("ok").getBytes());
            }
            else if(lines[1].matches("^/echo/.*$")) {
//                System.out.println("inside");

                String[] path = lines[1].split("/");
//                System.out.println(path[0]+" | "+path[1]+" | "+path[2]);
                if((path.length==3) && (path[1].equals("echo"))) {
                    String pathInput = path[2];
                    String output = MessageFormat.format(response.get("echo"),pathInput.length(), pathInput);
//                    System.out.println(output);
                    clientSocket.getOutputStream().write(output.getBytes());
                }
            }
            else {
                clientSocket.getOutputStream().write(response.get("notFound").getBytes());
            }


            System.out.println("accepted new connection");
        } catch (IOException e) {
            System.out.println("IOException: " + e.getMessage());
            e.printStackTrace();
        }
    }
}
