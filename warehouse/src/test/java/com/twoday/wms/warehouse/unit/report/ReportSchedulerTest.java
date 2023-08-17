package com.twoday.wms.warehouse.unit.report;

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

import com.twoday.wms.dto.PurchaseDto;
import com.twoday.wms.warehouse.purchase.Purchase;
import com.twoday.wms.warehouse.purchase.PurchaseConverter;
import com.twoday.wms.warehouse.purchase.interfaces.PurchaseService;
import com.twoday.wms.warehouse.report.ReportScheduler;
import com.twoday.wms.warehouse.report.interfaces.ReportFileService;
import com.twoday.wms.warehouse.report.interfaces.ReportGeneratorService;

class ReportSchedulerTest {

    @Mock
    private PurchaseService purchaseService;

    @Mock
    private ReportFileService fileService;

    @Mock
    private ReportGeneratorService generatorService;

    @Mock
    private PurchaseConverter purchaseConverter;

    @InjectMocks
    private ReportScheduler reportScheduler;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testGenerateCsvReport() {
        Purchase mockPurchase = new Purchase(); // A mock Purchase instance
        PurchaseDto mockPurchaseDto = new PurchaseDto();
        List<Purchase> mockPurchases = Arrays.asList(mockPurchase);
        when(purchaseService.findTop25ByOrderByIdDesc()).thenReturn(mockPurchases);
        List<PurchaseDto> mockPurchasesDto = Arrays.asList(mockPurchaseDto);
        when(purchaseConverter.toDto(mockPurchase)).thenReturn(mockPurchaseDto);
        when(generatorService.generateCSV(mockPurchasesDto)).thenReturn("mock-csv-data");

        reportScheduler.generateCsvReport();

        verify(purchaseService, times(1)).findTop25ByOrderByIdDesc();
        verify(generatorService, times(1)).generateCSV(mockPurchasesDto);
        verify(fileService, times(1)).saveToFile("mock-csv-data");
    }
}