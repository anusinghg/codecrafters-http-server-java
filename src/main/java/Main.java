import ConcurrentConnections.Server;
import common.CommandLineUtil;
import org.apache.commons.cli.CommandLine;
import org.apache.commons.cli.Option;
import org.apache.commons.cli.ParseException;

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
      Option directoryPath = new Option("d", "directory", false, "Base directory path.");
      Option[] options = new Option[]{directoryPath};

      CommandLine commandLine = CommandLineUtil.getCommandLine(options, args);
      System.out.println("hi");
      for(String arg: commandLine.getArgs()) {
        System.out.println(arg);
      }

      new Server(4221, commandLine).start();
    } catch (IOException | ParseException e) {
      throw new RuntimeException(e);
    }
  }




}
