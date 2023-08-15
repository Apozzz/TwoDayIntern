package com.twoday.warehouse.warehousemodule.unit.report;

import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.Arrays;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import com.twoday.warehouse.warehousemodule.purchase.Purchase;
import com.twoday.warehouse.warehousemodule.purchase.interfaces.PurchaseService;
import com.twoday.warehouse.warehousemodule.report.ReportScheduler;
import com.twoday.warehouse.warehousemodule.report.interfaces.ReportFileService;
import com.twoday.warehouse.warehousemodule.report.interfaces.ReportGeneratorService;

class ReportSchedulerTest {

    @Mock
    private PurchaseService purchaseService;

    @Mock
    private ReportFileService fileService;

    @Mock
    private ReportGeneratorService generatorService;

    @InjectMocks
    private ReportScheduler reportScheduler;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testGenerateCsvReport() {
        Purchase mockPurchase = new Purchase(); // A mock Purchase instance

        List<Purchase> mockPurchases = Arrays.asList(mockPurchase);
        when(purchaseService.findTop25ByOrderByIdDesc()).thenReturn(mockPurchases);
        when(generatorService.generateCSV(mockPurchases)).thenReturn("mock-csv-data");

        reportScheduler.generateCsvReport();

        verify(purchaseService, times(1)).findTop25ByOrderByIdDesc();
        verify(generatorService, times(1)).generateCSV(mockPurchases);
        verify(fileService, times(1)).saveToFile("mock-csv-data");
    }
}