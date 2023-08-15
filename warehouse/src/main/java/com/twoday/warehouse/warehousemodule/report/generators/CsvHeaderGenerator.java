package com.twoday.warehouse.warehousemodule.report.generators;

import static com.twoday.warehouse.warehousemodule.report.constants.CsvConstants.CSV_SEPARATOR;

import java.util.TreeMap;

import org.springframework.stereotype.Service;

import com.twoday.warehouse.warehousemodule.annotations.CsvField;
import com.twoday.warehouse.warehousemodule.report.utils.CsvAnnotationProcessor;

@Service
public class CsvHeaderGenerator {
    
    public String generateCSVHeader(Class<?> clazz) {
        TreeMap<Integer, String> headers = new TreeMap<>();
    
        CsvAnnotationProcessor.processFields(clazz, 
            (field, annotation) -> headers.put(annotation.order(), annotation.name()), 
            (field, nestedFields) -> {
                for (CsvField nestedField : nestedFields) {
                    headers.put(nestedField.order(), nestedField.name());
                }
            }
        );
    
        return String.join(CSV_SEPARATOR, headers.values());
    }

}
