package com.twoday.wms.warehouse.unit.report;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.io.File;
import java.util.List;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.slf4j.LoggerFactory;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;

import com.twoday.wms.warehouse.report.ReportController;
import com.twoday.wms.warehouse.report.interfaces.ReportFileNameService;
import com.twoday.wms.warehouse.report.interfaces.ReportFileService;

import ch.qos.logback.classic.Logger;
import ch.qos.logback.classic.spi.ILoggingEvent;
import ch.qos.logback.core.read.ListAppender;

public class ReportControllerTest {

    @InjectMocks
    private ReportController reportController;

    @Mock
    private ReportFileService fileService;

    @Mock
    private ReportFileNameService fileNameService;

    private ListAppender<ILoggingEvent> listAppender;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
        Logger logger = (Logger) LoggerFactory.getLogger(ReportController.class);
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
    public void testGetPurchasesReport() {
        String dateTime = "20230810-1200";
        File mockFile = mock(File.class);
        when(fileService.determineCorrectFile(dateTime)).thenReturn(mockFile);
        String filename = "somefilename.csv";
        when(fileNameService.getFileName(dateTime)).thenReturn(filename);
        ResponseEntity<Resource> response = reportController.getPurchasesReport(dateTime);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(MediaType.parseMediaType("text/csv"), response.getHeaders().getContentType());
        assertEquals("attachment; filename=%s".formatted(filename),
                response.getHeaders().getFirst(HttpHeaders.CONTENT_DISPOSITION));
        verify(fileService, times(1)).determineCorrectFile(dateTime);
        verify(fileNameService, times(1)).getFileName(dateTime);
        List<ILoggingEvent> logsList = listAppender.list;
        assertEquals(2, logsList.size());
        assertEquals("Received request to fetch purchases report. Date-Time: 20230810-1200",
                logsList.get(0).getFormattedMessage());
        assertEquals("Fetching report file: null",
                logsList.get(1).getFormattedMessage());
    }

}