package com.twoday.warehouse.warehousemodule.unit.report.generators;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.MockitoAnnotations;

import com.twoday.warehouse.warehousemodule.annotations.CsvField;
import com.twoday.warehouse.warehousemodule.annotations.CsvFields;
import com.twoday.warehouse.warehousemodule.report.generators.CsvHeaderGenerator;

class CsvHeaderGeneratorTest {

    @InjectMocks
    private CsvHeaderGenerator csvHeaderGenerator;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.openMocks(this);
    }

    class TestObject {
        @CsvField(order = 1, name = "Header1")
        String field1;

        @CsvField(order = 2, name = "Header2")
        String field2;

        @CsvFields({ @CsvField(order = 3, name = "NestedHeader1") })
        NestedTestObject nestedObj;
    }

    class NestedTestObject {
        String nestedField1;
    }

    @Test
    void testGenerateCSVHeader() {
        String header = csvHeaderGenerator.generateCSVHeader(TestObject.class);
        assertEquals("Header1,Header2,NestedHeader1", header);
    }

    @Test
    void testGenerateCSVHeaderWithDifferentOrder() {
        class TestOrderObject {
            @CsvField(order = 2, name = "Header2")
            String field2;

            @CsvField(order = 1, name = "Header1")
            String field1;
        }

        String header = csvHeaderGenerator.generateCSVHeader(TestOrderObject.class);
        assertEquals("Header1,Header2", header);
    }

    @Test
    void testGenerateCSVHeaderWithNoAnnotations() {
        class TestNoAnnotationObject {
            String field1;
            String field2;
        }

        String header = csvHeaderGenerator.generateCSVHeader(TestNoAnnotationObject.class);
        assertEquals("", header);
    }

}