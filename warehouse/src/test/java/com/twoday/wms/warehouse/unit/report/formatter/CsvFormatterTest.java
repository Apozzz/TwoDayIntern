package com.twoday.wms.warehouse.unit.report.formatter;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.MockitoAnnotations;

import com.twoday.wms.warehouse.annotations.CsvField;
import com.twoday.wms.warehouse.report.formatters.CsvFormatter;

class CsvFormatterTest {

    @InjectMocks
    private CsvFormatter csvFormatter;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    public static class TestObjectWithQuote {
        @CsvField(order = 1, name = "test", quote = true)
        public String field = "value";

        public String getField() {
            return field;
        }
    }

    @Test
    void testExtractValueWithQuote() throws IllegalAccessException, IllegalArgumentException, NoSuchFieldException, InvocationTargetException, NoSuchMethodException {
        TestObjectWithQuote testObject = new TestObjectWithQuote();
        Field field = TestObjectWithQuote.class.getDeclaredField("field");
        CsvField annotation = field.getAnnotation(CsvField.class);
        String result = csvFormatter.extractValue(testObject, field, annotation);
        assertEquals("\"value\"", result);
    }

    public static class TestObjectWithoutQuote {
        @CsvField(order = 1, name = "test", quote = false)
        public String field = "value";

        public String getField() {
            return field;
        }
    }

    @Test
    void testExtractValueWithoutQuote() throws IllegalAccessException, IllegalArgumentException, NoSuchFieldException, InvocationTargetException, NoSuchMethodException {
        TestObjectWithoutQuote testObject = new TestObjectWithoutQuote();
        Field field = TestObjectWithoutQuote.class.getDeclaredField("field");
        CsvField annotation = field.getAnnotation(CsvField.class);
        String result = csvFormatter.extractValue(testObject, field, annotation);
        assertEquals("value", result);
    }

}