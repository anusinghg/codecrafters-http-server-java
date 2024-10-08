package httpServer;

import common.FileReaderUtil;
import common.HttpResponse;
import org.apache.commons.cli.CommandLine;

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

    private CommandLine commandLine;
    
    public Client(Socket clientSocket, CommandLine commandLine){
        this.clientSocket = clientSocket;
        this.commandLine = commandLine;
    }

    @Override
    public void run() {
        Map<HttpResponse, String> response = new HashMap<>();
        response.put(HttpResponse.OK, "HTTP/1.1 200 OK\r\n\r\n");
        response.put(HttpResponse.NotFound, "HTTP/1.1 404 Not Found\r\n\r\n");
        response.put(HttpResponse.OKPlainText, "HTTP/1.1 200 OK\r\nContent-Type: text/plain\r\nContent-Length: {0}\r\n\r\n{1}");
        response.put(HttpResponse.OKOctetStream, "HTTP/1.1 200 OK\r\nContent-Type: application/octet-stream\r\nContent-Length: {0}\r\n\r\n{1}");
        response.put(HttpResponse.Created, "HTTP/1.1 201 Created\r\n\r\n");
        try {
            PrintWriter out = new PrintWriter(this.clientSocket.getOutputStream(), true);
            BufferedReader in = new BufferedReader(new InputStreamReader(this.clientSocket.getInputStream()));
            String input = in.readLine();
            in.readLine();

            String userAgent = in.readLine();

            System.out.println("input"+ input);
            System.out.println("userAgent"+ userAgent);

            String[] lines = input.split(" ");
            System.out.println(lines[0]);
            if(lines[1].equals("/")){
                this.clientSocket.getOutputStream().write(response.get(HttpResponse.OK).getBytes());
            }
            else if(lines[1].matches("^/files/.*$")) {
                if(lines[0].equals("GET")) {
                    String[] path = lines[1].split("/");
                    System.out.println(path[0]);
                    System.out.println(path[1]);
                    System.out.println(path[2]);
                    String fileName = path[2];
                    try {
                        System.out.println(commandLine.getArgList().size());
                        String filePath = commandLine.getArgList().get(0) + fileName;
                        System.out.println(filePath);
                        FileReaderUtil fileReaderUtil = new FileReaderUtil(filePath);
                        String fileContent = fileReaderUtil.readFileAsString();
                        String output = MessageFormat.format(response.get(HttpResponse.OKOctetStream),fileContent.length(), fileContent);
                        this.clientSocket.getOutputStream().write(output.getBytes());
                    }catch (IOException e) {
                        e.printStackTrace();
                        this.clientSocket.getOutputStream().write(response.get(HttpResponse.NotFound).getBytes());
                    }
                }
                else if(lines[0].equals("POST")) {

                    try {
                        in.readLine();
                        in.readLine();
                        String[] path = lines[1].split("/");
                        String fileName = path[2];
                        String[] contentLength = userAgent.split(" ");
                        char[] content = new char[Integer.parseInt(contentLength[1])];
                        in.read(content, 0, Integer.parseInt(contentLength[1]));
                        String filePath = commandLine.getArgList().get(0) + fileName;
                        System.out.println(filePath);
                        FileReaderUtil fileReaderUtil = new FileReaderUtil(filePath);
                        fileReaderUtil.writeStringToFileAndCreateDirectory(String.valueOf(content));
                        System.out.println("checking");
                        FileReaderUtil fileReaderUtilCheck = new FileReaderUtil(filePath);
                        String fileContent = fileReaderUtilCheck.readFileAsString();
                        System.out.println(fileContent);
                        this.clientSocket.getOutputStream().write(response.get(HttpResponse.Created).getBytes());
                    }catch (IOException e){
                        this.clientSocket.getOutputStream().write(response.get(HttpResponse.NotFound).getBytes());
                        e.printStackTrace();
                    }

                }

            }
            else if(lines[1].matches("^/echo/.*$")) {
//                System.out.println("inside");

                String[] path = lines[1].split("/");
//                System.out.println(path[0]+" | "+path[1]+" | "+path[2]);
                if((path.length==3) && (path[1].equals("echo"))) {
                    String pathInput = path[2];
                    String output = MessageFormat.format(response.get(HttpResponse.OKPlainText),pathInput.length(), pathInput);
//                    System.out.println(output);
                    this.clientSocket.getOutputStream().write(output.getBytes());
                }
            }
            else if(lines[1].equals("/user-agent")) {
                String outputUserAgent = userAgent.split(" ")[1];
                String returnString  =MessageFormat.format(response.get(HttpResponse.OKPlainText), outputUserAgent.length(), outputUserAgent);
                System.out.println(returnString);
                this.clientSocket.getOutputStream().write(returnString.getBytes());
            }
            else {
                this.clientSocket.getOutputStream().write(response.get(HttpResponse.NotFound).getBytes());
            }

            out.flush();
//            out.close();
        } catch (IOException e) {

            throw new RuntimeException(e);
        }

    }


}
