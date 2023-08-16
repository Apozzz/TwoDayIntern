package com.twoday.wms.warehouse.report.utils;

import java.lang.reflect.Field;
import java.lang.reflect.Method;

public class ReflectionUtils {

    private ReflectionUtils() {
        throw new IllegalStateException("Utility class");
    }
    
    public static Method findGetter(Field field) throws NoSuchMethodException {
        String base = field.getName().substring(0, 1).toUpperCase() + field.getName().substring(1);
        String getterName = field.getType() == boolean.class || field.getType() == Boolean.class ? "is" + base : "get" + base;
        return field.getDeclaringClass().getMethod(getterName);
    }

}
