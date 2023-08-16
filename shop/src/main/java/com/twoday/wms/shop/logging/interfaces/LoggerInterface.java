package com.twoday.wms.shop.logging.interfaces;

import org.aspectj.lang.JoinPoint;

public interface LoggerInterface {

    boolean supports(Class<?> clazz);
    void logBeforeCall(JoinPoint joinPoint);
    void logAfterCall(JoinPoint joinPoint);
    void logException(JoinPoint joinPoint, Throwable ex);

}
