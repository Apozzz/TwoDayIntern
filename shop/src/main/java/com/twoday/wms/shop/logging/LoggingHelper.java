package com.twoday.wms.shop.logging;

import java.lang.reflect.Method;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.Signature;
import org.aspectj.lang.reflect.MethodSignature;
import org.slf4j.Logger;

import com.twoday.wms.shop.logging.annotations.LogMessage;

public class LoggingHelper {

    private static final String DEFAULT_BEFORE_MESSAGE = "Calling method {} with arguments: {}";
    private static final String DEFAULT_AFTER_MESSAGE = "Method {} was called successfully";

    private LoggingHelper() {
        throw new IllegalStateException("Utility class");
    }

    public static LogMessage getLogMessageAnnotation(JoinPoint joinPoint) {
        MethodSignature signature = (MethodSignature) joinPoint.getSignature();
        Method method = signature.getMethod();
        return method.getAnnotation(LogMessage.class);
    }

    public static void logBefore(JoinPoint joinPoint, Logger logger) {
        LogMessage logMessage = getLogMessageAnnotation(joinPoint);
        String message = logMessage.before().isEmpty() ? DEFAULT_BEFORE_MESSAGE : logMessage.before();
        logWithSafetyCheck(joinPoint, logger, message, joinPoint.getArgs());
    }

    public static void logAfter(JoinPoint joinPoint, Logger logger) {
        LogMessage logMessage = getLogMessageAnnotation(joinPoint);
        String message = logMessage.after().isEmpty() ? DEFAULT_AFTER_MESSAGE : logMessage.after();
        logWithSafetyCheck(joinPoint, logger, message, joinPoint.getArgs());
    }

    public static void logException(JoinPoint joinPoint, Logger logger, Throwable ex) {
        logWithSafetyCheck(joinPoint, logger, "An error occurred in method {}. Details: {}", ex.getMessage());
    }

    private static String safeToShortString(JoinPoint joinPoint) {
        Signature signature = joinPoint != null ? joinPoint.getSignature() : null;
        return signature != null ? signature.toShortString() : null;
    }

    private static void logWithSafetyCheck(JoinPoint joinPoint, Logger logger, String message, Object... args) {
        String methodSignature = safeToShortString(joinPoint);
        if (methodSignature != null) {
            Object[] extendedArgs = new Object[args.length + 1];
            extendedArgs[0] = methodSignature;
            System.arraycopy(args, 0, extendedArgs, 1, args.length);
            logger.info(message, extendedArgs);
        }
    }

}
