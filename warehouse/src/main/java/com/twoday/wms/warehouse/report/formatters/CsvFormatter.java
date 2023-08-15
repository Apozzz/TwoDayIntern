package com.twoday.wms.warehouse.report.formatters;

import static com.twoday.wms.warehouse.report.constants.CsvConstants.DOUBLE_QUOTE;
import static com.twoday.wms.warehouse.report.constants.CsvConstants.EMPTY_STRING;
import static com.twoday.wms.warehouse.report.constants.CsvConstants.QUOTE;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;

import org.springframework.stereotype.Service;

import com.twoday.wms.warehouse.annotations.CsvField;
import com.twoday.wms.warehouse.report.utils.ReflectionUtils;

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
