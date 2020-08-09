package Util;

import java.io.IOException;
import java.util.logging.FileHandler;
import java.util.logging.Logger;
import java.util.logging.SimpleFormatter;

public class LogHandler {
    public static Logger logger = Logger.getLogger(LogHandler.class.getName());
    public static FileHandler fh = null;

    public static void init() throws IOException {
        fh = new FileHandler("log/EPT_Log.txt");
        System.setProperty("java.util.logging.SimpleFormatter.format", "[%1$tF %1$tT] - [%4$-7s] - [%2$s] -> %5$s%6$s%n");
        fh.setFormatter(new SimpleFormatter());
        logger.addHandler(fh);
        logger.info("App Started Successfully.");
    }
}

