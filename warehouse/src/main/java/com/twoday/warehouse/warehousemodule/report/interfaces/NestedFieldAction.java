package com.twoday.warehouse.warehousemodule.report.interfaces;

import java.lang.reflect.Field;

import com.twoday.warehouse.warehousemodule.annotations.CsvField;

@FunctionalInterface
public interface NestedFieldAction {

    void apply(Field field, CsvField[] nestedFields);
    
}
