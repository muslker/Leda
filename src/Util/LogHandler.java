package Util;

import java.io.IOException;
import java.util.logging.FileHandler;
import java.util.logging.Logger;
import java.util.logging.SimpleFormatter;

public class LogHandler {
    public static Logger logger = Logger.getLogger(LogHandler.class.getName());
    public static FileHandler fh = null;

    public static void init() throws IOException {
        fh = new FileHandler("log/TestLog.txt");
        fh.setFormatter(new SimpleFormatter());
        logger.addHandler(fh);
        logger.info("starting");
    }
}

