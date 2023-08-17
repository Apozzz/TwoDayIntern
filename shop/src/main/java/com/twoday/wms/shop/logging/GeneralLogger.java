package com.twoday.wms.shop.logging;

import org.aspectj.lang.JoinPoint;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class GeneralLogger {

    private GeneralLogger() {
        throw new IllegalStateException("Utility class");
    }

    public static void logBefore(JoinPoint joinPoint, Class<? extends Logger> loggerClass) {
        Logger logger = (loggerClass == Logger.class) ? LoggerFactory.getLogger(joinPoint.getTarget().getClass())
                : LoggerFactory.getLogger(loggerClass);
        LoggingHelper.logBefore(joinPoint, logger);
    }

    public static void logAfter(JoinPoint joinPoint, Class<? extends Logger> loggerClass) {
        Logger logger = (loggerClass == Logger.class) ? LoggerFactory.getLogger(joinPoint.getTarget().getClass())
                : LoggerFactory.getLogger(loggerClass);
        LoggingHelper.logAfter(joinPoint, logger);
    }

    public static void logException(JoinPoint joinPoint, Throwable ex, Class<? extends Logger> loggerClass) {
        Logger logger = (loggerClass == Logger.class) ? LoggerFactory.getLogger(joinPoint.getTarget().getClass())
                : LoggerFactory.getLogger(loggerClass);
        LoggingHelper.logException(joinPoint, logger, ex);
    }

}
