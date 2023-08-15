package com.twoday.warehouse.warehousemodule.unit.report;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.Arrays;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import com.twoday.warehouse.warehousemodule.purchase.Purchase;
import com.twoday.warehouse.warehousemodule.report.ReportGeneratorServiceImpl;
import com.twoday.warehouse.warehousemodule.report.generators.CsvHeaderGenerator;
import com.twoday.warehouse.warehousemodule.report.generators.CsvRowGenerator;

public class ReportGeneratorServiceImplTest {

    private final static String NEW_LINE = "\n";
    
    @InjectMocks
    private ReportGeneratorServiceImpl service;

    @Mock
    private CsvHeaderGenerator csvHeaderGenerator;

    @Mock
    private CsvRowGenerator csvRowGenerator;
    

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testGenerateCSV() {
        Purchase mockPurchase = new Purchase();
        when(csvHeaderGenerator.generateCSVHeader(Purchase.class)).thenReturn("mock-header");
        when(csvRowGenerator.generateCSVRow(mockPurchase)).thenReturn("mock-row");

        String result = service.generateCSV(Arrays.asList(mockPurchase));

        assertEquals("mock-header" + NEW_LINE + "mock-row" + NEW_LINE, result);

        verify(csvHeaderGenerator, times(1)).generateCSVHeader(Purchase.class);
        verify(csvRowGenerator, times(1)).generateCSVRow(mockPurchase);
    }

}
