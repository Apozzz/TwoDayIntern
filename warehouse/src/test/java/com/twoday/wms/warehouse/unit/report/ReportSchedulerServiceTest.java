package com.twoday.wms.warehouse.unit.report;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.Arrays;
import java.util.List;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.slf4j.LoggerFactory;

import com.twoday.wms.dto.PurchaseDto;
import com.twoday.wms.warehouse.purchase.Purchase;
import com.twoday.wms.warehouse.purchase.PurchaseConverter;
import com.twoday.wms.warehouse.purchase.interfaces.PurchaseService;
import com.twoday.wms.warehouse.report.ReportSchedulerService;
import com.twoday.wms.warehouse.report.interfaces.ReportFileService;
import com.twoday.wms.warehouse.report.interfaces.ReportGeneratorService;

import ch.qos.logback.classic.Logger;
import ch.qos.logback.classic.spi.ILoggingEvent;
import ch.qos.logback.core.read.ListAppender;

class ReportSchedulerServiceTest {

    @Mock
    private PurchaseService purchaseService;

    @Mock
    private ReportFileService fileService;

    @Mock
    private ReportGeneratorService generatorService;

    @Mock
    private PurchaseConverter purchaseConverter;

    @InjectMocks
    private ReportSchedulerService reportScheduler;

    private ListAppender<ILoggingEvent> listAppender;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        Logger logger = (Logger) LoggerFactory.getLogger(ReportSchedulerService.class);
        listAppender = new ListAppender<>();
        listAppender.start();
        logger.addAppender(listAppender);
    }

    @AfterEach
    public void tearDown() {
        listAppender.stop();
        listAppender.list.clear();
    }

    @Test
    void testGenerateCsvReport() {
        Purchase mockPurchase = new Purchase();
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

        List<ILoggingEvent> logsList = listAppender.list;
        assertEquals(3, logsList.size());
        assertEquals("Scheduled report generation job started.",
                logsList.get(0).getFormattedMessage());
        assertEquals("Fetched 1 purchases for report generation.",
                logsList.get(1).getFormattedMessage());
        assertEquals("Report generated and saved successfully.",
                logsList.get(2).getFormattedMessage());

    }
}