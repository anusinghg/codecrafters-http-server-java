import httpServer.Server;
import common.CommandLineUtil;
import org.apache.commons.cli.CommandLine;
import org.apache.commons.cli.Option;
import org.apache.commons.cli.ParseException;

import java.io.*;

public class Main {
  public static void main(String[] args){
//    junk.httpServer.ExtractURLPath.main(args);
//    junk.httpServer.RespondWithBody.main(args);
//    junk.httpServer.ReadHeader.main(args);
    try {
      Option directoryPath = new Option("d", "directory", false, "Base directory path.");
      Option[] options = new Option[]{directoryPath};

      CommandLine commandLine = CommandLineUtil.getCommandLine(options, args);

      new Server(4221, commandLine).start();
    } catch (IOException | ParseException e) {
      throw new RuntimeException(e);
    }
  }




}
