package com.twoday.wms.shop.logging;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.AfterThrowing;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import com.twoday.wms.shop.interfaces.LogAction;

@Aspect
@Component
public class LoggingAspect {

    private static final String CONTROLLER_METHODS_POINTCUT = "within(@org.springframework.web.bind.annotation.RestController *)";

    @Before(CONTROLLER_METHODS_POINTCUT)
    public void logBeforeCall(JoinPoint joinPoint) {
        handleLog(joinPoint, LoggingHelper::logBefore);
    }

    @After(CONTROLLER_METHODS_POINTCUT)
    public void logAfterCall(JoinPoint joinPoint) {
        handleLog(joinPoint, LoggingHelper::logAfter);
    }

    @AfterThrowing(pointcut = CONTROLLER_METHODS_POINTCUT, throwing = "ex")
    public void handleException(JoinPoint joinPoint, Throwable ex) {
        Class<?> loggerClass = getEffectiveLoggerClass(joinPoint);
        Logger logger = LoggerFactory.getLogger(loggerClass);
        LoggingHelper.logException(joinPoint, logger, ex);
    }

    private void handleLog(JoinPoint joinPoint, LogAction action) {
        Class<?> loggerClass = getEffectiveLoggerClass(joinPoint);
        Logger logger = LoggerFactory.getLogger(loggerClass);
        action.log(joinPoint, logger);
    }
    
    private Class<?> getEffectiveLoggerClass(JoinPoint joinPoint) {
        Class<?> loggerClass = LoggingHelper.getLogMessageAnnotation(joinPoint).loggerClass();
        return loggerClass == Void.class ? joinPoint.getTarget().getClass() : loggerClass;
    }

}
