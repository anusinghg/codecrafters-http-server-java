import ConcurrentConnections.Server;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.List;

public class Main {
  public static void main(String[] args){
//    ExtractURLPath.main(args);
//    RespondWithBody.main(args);
//    ReadHeader.main(args);
    try {
      for(String arg: args) {
        System.out.println(arg);
      }
      new Server(4221).start();
    } catch (IOException e) {
      throw new RuntimeException(e);
    }
  }




}
