package com.twoday.wms.warehouse.interfaces;

import java.lang.reflect.Field;

import com.twoday.wms.dto.annotations.CsvField;

@FunctionalInterface
public interface NestedFieldAction {

    void apply(Field field, CsvField[] nestedFields);
    
}
