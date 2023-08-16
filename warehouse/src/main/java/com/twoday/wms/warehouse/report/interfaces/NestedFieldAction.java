package com.twoday.wms.warehouse.report.interfaces;

import java.lang.reflect.Field;

import com.twoday.wms.warehouse.annotations.CsvField;

@FunctionalInterface
public interface NestedFieldAction {

    void apply(Field field, CsvField[] nestedFields);
    
}
