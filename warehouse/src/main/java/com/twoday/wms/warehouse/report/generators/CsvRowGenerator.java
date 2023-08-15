package com.twoday.wms.warehouse.report.generators;

import static com.twoday.wms.warehouse.report.constants.CsvConstants.CSV_SEPARATOR;

import java.lang.reflect.Field;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import org.springframework.stereotype.Service;

import com.twoday.wms.warehouse.annotations.CsvField;
import com.twoday.wms.warehouse.report.exceptions.ReportFileException;
import com.twoday.wms.warehouse.report.formatters.CsvFormatter;
import com.twoday.wms.warehouse.report.utils.CsvAnnotationProcessor;
import com.twoday.wms.warehouse.report.utils.ReflectionUtils;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class CsvRowGenerator {

    private final CsvFormatter csvFormatter;

    public String generateCSVRow(Object object) {
        TreeMap<Integer, String> values = new TreeMap<>();

        CsvAnnotationProcessor.processFields(object.getClass(),
                (field, annotation) -> handleField(object, field, annotation, values),
                (field, nestedFields) -> handleNestedFields(object, field, Arrays.asList(nestedFields), values));

        return String.join(CSV_SEPARATOR, values.values());
    }

    private void handleField(Object object, Field field, CsvField annotation, Map<Integer, String> values) {
        try {
            String value = csvFormatter.extractValue(object, field, annotation);
            values.put(annotation.order(), value);
        } catch (Exception e) {
            throw new ReportFileException("Error generating CSV row: %s".formatted(e.getMessage()));
        }
    }

    private void handleNestedFields(Object object, Field field, List<CsvField> nestedFields,
            Map<Integer, String> values) {
        Object currentNestedObject = getNestedObject(object, field);

        if (currentNestedObject != null) {
            nestedFields.forEach(nestedField -> handleNestedField(currentNestedObject, nestedField, values));
        }
    }

    private Object getNestedObject(Object object, Field field) {
        try {
            return ReflectionUtils.findGetter(field).invoke(object);
        } catch (Exception e) {
            throw new ReportFileException(
                    "Failed to invoke getter for %s: %s".formatted(field.getName(), e.getMessage()));
        }
    }

    private void handleNestedField(Object nestedObject, CsvField nestedField, Map<Integer, String> values) {
        try {
            Field nestedObjectField = nestedObject.getClass().getDeclaredField(nestedField.nestedField());
            String value = csvFormatter.extractValue(nestedObject, nestedObjectField, nestedField);
            values.put(nestedField.order(), value);
        } catch (Exception e) {
            throw new ReportFileException(
                    "Error generating CSV row for nested field '%s' in class '%s': %s"
                            .formatted(nestedField.nestedField(), nestedObject.getClass().getSimpleName(),
                                    e.getMessage()));
        }
    }

}
