package com.twoday.warehouse.warehousemodule.unit.report;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.io.File;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;

import com.twoday.warehouse.warehousemodule.report.ReportController;
import com.twoday.warehouse.warehousemodule.report.interfaces.ReportFileNameService;
import com.twoday.warehouse.warehousemodule.report.interfaces.ReportFileService;

public class ReportControllerTest {

    @InjectMocks
    private ReportController reportController;

    @Mock
    private ReportFileService fileService;

    @Mock
    private ReportFileNameService fileNameService;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testGetPurchasesReport() {
        String dateTime = "20230810-1200";
        Optional<String> dateTimeOptional = Optional.ofNullable(dateTime);
        File mockFile = mock(File.class);
        when(fileService.determineCorrectFile(dateTimeOptional)).thenReturn(mockFile);
        String filename = "somefilename.csv";
        when(fileNameService.getFileName(dateTimeOptional)).thenReturn(filename);
        ResponseEntity<Resource> response = reportController.getPurchasesReport(dateTime);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(MediaType.parseMediaType("text/csv"), response.getHeaders().getContentType());
        assertEquals("attachment; filename=%s".formatted(filename),
                response.getHeaders().getFirst(HttpHeaders.CONTENT_DISPOSITION));
        verify(fileService, times(1)).determineCorrectFile(dateTimeOptional);
        verify(fileNameService, times(1)).getFileName(dateTimeOptional);
    }

}