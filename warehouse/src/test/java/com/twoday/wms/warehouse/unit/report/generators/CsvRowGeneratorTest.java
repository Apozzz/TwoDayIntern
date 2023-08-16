package com.twoday.wms.warehouse.unit.report.generators;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.when;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import com.twoday.wms.warehouse.annotations.CsvField;
import com.twoday.wms.warehouse.report.exceptions.ReportFileException;
import com.twoday.wms.warehouse.report.formatters.CsvFormatter;
import com.twoday.wms.warehouse.report.generators.CsvRowGenerator;

class CsvRowGeneratorTest {

    @Mock
    private CsvFormatter csvFormatter;

    @InjectMocks
    private CsvRowGenerator csvRowGenerator;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    class TestObject {
        @CsvField(order = 1, name = "value1")
        String field1 = "value1";

        @CsvField(order = 2, name = "value2")
        String field2 = "value2";
    }

    @Test
    void testGenerateCSVRow() throws NoSuchFieldException, IllegalAccessException, IllegalArgumentException,
            InvocationTargetException, NoSuchMethodException {
        TestObject testObj = new TestObject();

        Field field1 = TestObject.class.getDeclaredField("field1");
        Field field2 = TestObject.class.getDeclaredField("field2");
        CsvField annotation1 = field1.getAnnotation(CsvField.class);
        CsvField annotation2 = field2.getAnnotation(CsvField.class);

        when(csvFormatter.extractValue(testObj, field1, annotation1)).thenReturn("value1");
        when(csvFormatter.extractValue(testObj, field2, annotation2)).thenReturn("value2");

        String result = csvRowGenerator.generateCSVRow(testObj);

        assertEquals("value1,value2", result);
    }

    @Test
    void testGenerateCSVRowWithException() throws NoSuchFieldException, IllegalAccessException,
            IllegalArgumentException, InvocationTargetException, NoSuchMethodException {
        TestObject testObj = new TestObject();

        Field field1 = TestObject.class.getDeclaredField("field1");
        CsvField annotation1 = field1.getAnnotation(CsvField.class);

        when(csvFormatter.extractValue(testObj, field1, annotation1))
                .thenThrow(new RuntimeException("Some error"));

        assertThrows(ReportFileException.class, () -> csvRowGenerator.generateCSVRow(testObj));
    }

}