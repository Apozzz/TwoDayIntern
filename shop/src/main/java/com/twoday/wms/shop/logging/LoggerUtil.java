package com.twoday.wms.shop.logging;

import org.slf4j.Logger;

public class LoggerUtil {

    private LoggerUtil() {
        throw new UnsupportedOperationException("LoggerUtil cannot be instantiated.");
    }

    public static void logInfo(Logger logger, String message, Object... args) {
        if (logger.isInfoEnabled()) {
            logger.info(message, args);
        }
    }

    public static void logError(Logger logger, String message, Object... args) {
        if (logger.isErrorEnabled()) {
            logger.debug(message, args);
        }
    }
    
}
