package com.twoday.wms.warehouse.unit.report;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.when;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import com.twoday.wms.warehouse.report.ReportFileNameServiceImpl;
import com.twoday.wms.warehouse.report.exceptions.InvalidDateTimeFormatException;
import com.twoday.wms.warehouse.report.interfaces.ReportValidator;

public class ReportFileNameServiceImplTest {
    
    private static final String DATE_TIME_PATTERN = "yyyyMMdd-HHmm";
    private static final String FILENAME_FORMAT = "%s-%s.csv";
    
    private final String reportFilename = "null";

    @InjectMocks
    private ReportFileNameServiceImpl reportFileNameService;

    @Mock
    private ReportValidator validator;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testGetFilenameWhenDateTimeNotPresent() {
        String expectedFilename = FILENAME_FORMAT.formatted(reportFilename, 
            LocalDateTime.now().format(DateTimeFormatter.ofPattern(DATE_TIME_PATTERN)));
        String actualFilename = reportFileNameService.getFileName(null);
        assertEquals(expectedFilename, actualFilename);
    }

    @Test
    public void testGetFilenameWhenDateTimeIsValid() {
        String validDateTime = "20230808-1200";
        when(validator.validate(validDateTime)).thenReturn(true);
        String expectedFilename = FILENAME_FORMAT.formatted(reportFilename, validDateTime);
        String actualFilename = reportFileNameService.getFileName(validDateTime);
        assertEquals(expectedFilename, actualFilename);
    }

    @Test
    public void testGetFilenameWhenDateTimeIsInvalid() {
        String invalidDateTime = "invalid-date-time";
        when(validator.validate(invalidDateTime)).thenReturn(false);
        assertThrows(InvalidDateTimeFormatException.class, () -> {
            reportFileNameService.getFileName(invalidDateTime);
        });
    }

}
