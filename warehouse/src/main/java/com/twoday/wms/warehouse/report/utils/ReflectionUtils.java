package com.twoday.wms.warehouse.report.utils;

import java.beans.PropertyDescriptor;
import java.lang.reflect.Field;
import java.lang.reflect.Method;

import org.springframework.beans.BeanUtils;

import com.twoday.wms.warehouse.report.exceptions.ReportFileException;

public class ReflectionUtils {

    private ReflectionUtils() {
        throw new IllegalStateException("Utility class");
    }
    
    public static Method findGetter(Field field) {
        String fieldName = field.getName();
        PropertyDescriptor pd = BeanUtils.getPropertyDescriptor(field.getDeclaringClass(), fieldName);
        
        if (pd == null || pd.getReadMethod() == null) {
            throw new ReportFileException("Failed to find getter method for field: %s".formatted(fieldName));
        }

        return pd.getReadMethod();
    }

}
