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
      Option directoryPath = new Option("d", "directory", true, "Number of lines");
      Option[] options = new Option[]{directoryPath};

      CommandLine commandLine = CommandLineUtil.getCommandLine(options, args);

      new Server(4221, commandLine).start();
    } catch (IOException | ParseException e) {
      throw new RuntimeException(e);
    }
  }




}
