package com.twoday.warehouse.warehousemodule.report.formatters;

import static com.twoday.warehouse.warehousemodule.report.constants.CsvConstants.DOUBLE_QUOTE;
import static com.twoday.warehouse.warehousemodule.report.constants.CsvConstants.EMPTY_STRING;
import static com.twoday.warehouse.warehousemodule.report.constants.CsvConstants.QUOTE;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;

import org.springframework.stereotype.Service;

import com.twoday.warehouse.warehousemodule.annotations.CsvField;
import com.twoday.warehouse.warehousemodule.report.utils.ReflectionUtils;

@Service
public class CsvFormatter {
    
    public String extractValue(Object object, Field field, CsvField annotation) throws IllegalAccessException, IllegalArgumentException, InvocationTargetException, NoSuchMethodException  {
        Object rawValue = ReflectionUtils.findGetter(field).invoke(object);
        String stringValue = rawValue != null ? rawValue.toString() : EMPTY_STRING;

        if (annotation.quote()) {
            stringValue = QUOTE + stringValue.replace(QUOTE, DOUBLE_QUOTE) + QUOTE;
        }

        return stringValue;
    }

}
