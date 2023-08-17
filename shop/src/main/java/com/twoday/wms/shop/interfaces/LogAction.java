package com.twoday.wms.shop.interfaces;

import org.aspectj.lang.JoinPoint;
import org.slf4j.Logger;

@FunctionalInterface
public interface LogAction {

    void log(JoinPoint joinPoint, Logger logger);
    
}
