package com.twoday.warehouse.warehousemodule.unit.report;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.when;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import com.twoday.warehouse.warehousemodule.report.ReportFileNameServiceImpl;
import com.twoday.warehouse.warehousemodule.report.exceptions.InvalidDateTimeFormatException;
import com.twoday.warehouse.warehousemodule.report.interfaces.ReportValidator;

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
        String actualFilename = reportFileNameService.getFileName(Optional.empty());
        assertEquals(expectedFilename, actualFilename);
    }

    @Test
    public void testGetFilenameWhenDateTimeIsValid() {
        String validDateTime = "20230808-1200";
        when(validator.validate(validDateTime)).thenReturn(true);
        String expectedFilename = FILENAME_FORMAT.formatted(reportFilename, validDateTime);
        String actualFilename = reportFileNameService.getFileName(Optional.of(validDateTime));
        assertEquals(expectedFilename, actualFilename);
    }

    @Test
    public void testGetFilenameWhenDateTimeIsInvalid() {
        String invalidDateTime = "invalid-date-time";
        when(validator.validate(invalidDateTime)).thenReturn(false);
        assertThrows(InvalidDateTimeFormatException.class, () -> {
            reportFileNameService.getFileName(Optional.of(invalidDateTime));
        });
    }

}
