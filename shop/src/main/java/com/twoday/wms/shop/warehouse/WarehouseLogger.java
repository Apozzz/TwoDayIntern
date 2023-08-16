package com.twoday.wms.shop.warehouse;

import org.aspectj.lang.JoinPoint;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import com.twoday.wms.shop.logging.LoggerUtil;
import com.twoday.wms.shop.logging.LoggingHelper;
import com.twoday.wms.shop.logging.interfaces.LoggerInterface;

@Component
public class WarehouseLogger implements LoggerInterface {

    private static final Logger logger = LoggerFactory.getLogger(WarehouseController.class);

    @Override
    public boolean supports(Class<?> clazz) {
        return clazz.getName().contains("WarehouseController");
    }

    @Override
    public void logBeforeCall(JoinPoint joinPoint) {
        LoggingHelper.logBefore(joinPoint, logger);
    }

    @Override
    public void logAfterCall(JoinPoint joinPoint) {
        LoggingHelper.logAfter(joinPoint, logger);
    }

    @Override
    public void logException(JoinPoint joinPoint, Throwable ex) {
        LoggerUtil.logError(logger, "An error occurred in method {}. Details: {}",
                joinPoint.getSignature().toShortString(),
                ex.getMessage());
    }

}