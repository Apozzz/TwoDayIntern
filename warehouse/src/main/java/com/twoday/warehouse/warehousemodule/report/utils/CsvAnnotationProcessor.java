package com.twoday.warehouse.warehousemodule.report.utils;

import java.lang.reflect.Field;

import com.twoday.warehouse.warehousemodule.annotations.CsvField;
import com.twoday.warehouse.warehousemodule.annotations.CsvFields;
import com.twoday.warehouse.warehousemodule.report.interfaces.FieldAction;
import com.twoday.warehouse.warehousemodule.report.interfaces.NestedFieldAction;

public class CsvAnnotationProcessor {

    private CsvAnnotationProcessor() {
        throw new IllegalStateException("Utility class");
    }
    
    public static void processFields(Class<?> clazz, FieldAction<CsvField> singleFieldAction, NestedFieldAction nestedFieldAction) {
        for (Field field : clazz.getDeclaredFields()) {
            if (field.isAnnotationPresent(CsvField.class)) {
                CsvField annotation = field.getAnnotation(CsvField.class);
                singleFieldAction.apply(field, annotation);
            } else if (field.isAnnotationPresent(CsvFields.class)) {
                CsvFields annotations = field.getAnnotation(CsvFields.class);
                nestedFieldAction.apply(field, annotations.value());
            }
        }
    }

}
