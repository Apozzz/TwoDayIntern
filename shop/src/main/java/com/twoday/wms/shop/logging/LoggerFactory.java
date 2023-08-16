package com.twoday.wms.shop.logging;

import java.util.List;

import org.springframework.stereotype.Component;

import com.twoday.wms.shop.logging.interfaces.LoggerInterface;

import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class LoggerFactory {

    private final List<LoggerInterface> loggers;

    public LoggerInterface getLogger(Class<?> clazz) {
        for (LoggerInterface logger : loggers) {
            if (logger.supports(clazz)) {
                return logger;
            }
        }
        
        return null;
    }

}
