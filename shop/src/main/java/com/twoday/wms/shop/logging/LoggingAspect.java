package com.twoday.wms.shop.logging;

import java.util.function.Consumer;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.AfterThrowing;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;

import com.twoday.wms.shop.logging.interfaces.LoggerInterface;

import lombok.RequiredArgsConstructor;

@Aspect
@Component
@RequiredArgsConstructor
public class LoggingAspect {

    private static final String CONTROLLER_METHODS_POINTCUT = "within(@org.springframework.web.bind.annotation.RestController *)";

    private final LoggerFactory loggerFactory;

    @Pointcut(CONTROLLER_METHODS_POINTCUT)
    public void allControllerMethods() {
    }

    @Before("allControllerMethods()")
    public void logBeforeCall(JoinPoint joinPoint) {
        handleLog(joinPoint, logger -> logger.logBeforeCall(joinPoint));
    }

    @After("allControllerMethods()")
    public void logAfterCall(JoinPoint joinPoint) {
        handleLog(joinPoint, logger -> logger.logAfterCall(joinPoint));
    }

    @AfterThrowing(pointcut = "allControllerMethods()", throwing = "ex")
    public void logException(JoinPoint joinPoint, Throwable ex) {
        handleLog(joinPoint, logger -> logger.logException(joinPoint, ex));
    }

    private void handleLog(JoinPoint joinPoint, Consumer<LoggerInterface> logMethod) {
        LoggerInterface loggerInstance = loggerFactory.getLogger(joinPoint.getTarget().getClass());
        
        if (loggerInstance != null) {
            logMethod.accept(loggerInstance);
        }
    }

}
