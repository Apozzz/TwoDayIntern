package com.twoday.wms.shop.logging;

import java.lang.reflect.Method;

import org.aspectj.lang.JoinPoint;
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
        Method method = getMethodFromJoinPoint(joinPoint);
        return method.getAnnotation(LogMessage.class);
    }

    public static void logBefore(JoinPoint joinPoint, Logger logger) {
        LogMessage logMessageAnnotation = LoggingHelper.getLogMessageAnnotation(joinPoint);

        if (logMessageAnnotation != null && !logMessageAnnotation.before().isEmpty()) {
            logger.info(logMessageAnnotation.before(), joinPoint.getArgs());
        } else {
            LoggerUtil.logInfo(logger, DEFAULT_BEFORE_MESSAGE, joinPoint.getSignature().toShortString(),
                    joinPoint.getArgs());
        }
    }

    public static void logAfter(JoinPoint joinPoint, Logger logger) {
        LogMessage logMessageAnnotation = LoggingHelper.getLogMessageAnnotation(joinPoint);

        if (logMessageAnnotation != null && !logMessageAnnotation.after().isEmpty()) {
            logger.info(logMessageAnnotation.after(), joinPoint.getArgs());
        } else {
            LoggerUtil.logInfo(logger, DEFAULT_AFTER_MESSAGE, joinPoint.getSignature().toShortString(),
                    joinPoint.getArgs());
        }
    }

    private static Method getMethodFromJoinPoint(JoinPoint joinPoint) {
        MethodSignature methodSignature = (MethodSignature) joinPoint.getSignature();
        return methodSignature.getMethod();
    }

}
