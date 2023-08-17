package com.twoday.wms.warehouse.interfaces;

import java.lang.reflect.Field;

@FunctionalInterface
public interface FieldAction<T> {

    void apply(Field field, T annotationData);
    
}
