package common;

import org.apache.commons.cli.*;

public class CommandLineUtil {

    private static CommandLineParser commandLineParser;
    private static HelpFormatter helpFormatter;

    static Options options;

    static {
        commandLineParser = new DefaultParser();
        helpFormatter = new HelpFormatter();
        options = new Options();
    }

    public static CommandLine getCommandLine(String[] args) throws ParseException {
        return commandLineParser.parse(options, args);
    }

    public static CommandLine getCommandLine(Option[] optionArray, String[] args) throws ParseException {
        for(Option option: optionArray)
            options.addOption(option);
        return commandLineParser.parse(options, args);
    }

}
