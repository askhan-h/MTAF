package base.utils.log4j;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class logs {

    private static final Logger logger = LogManager.getLogger(logs.class);

    public static void info(String msg) {
        logger.info(msg);
    }

    public static Throwable error(String msg) {
        logger.error(msg);
        return new Exception(msg);
    }


}
