package com.twoday.wms.warehouse.report;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.twoday.wms.warehouse.interfaces.ReportFileNameService;
import com.twoday.wms.warehouse.interfaces.ReportValidator;
import com.twoday.wms.warehouse.report.exceptions.InvalidDateTimeFormatException;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ReportFileNameServiceImpl implements ReportFileNameService {

    private static final String DATE_TIME_PATTERN = "yyyyMMdd-HHmm";
    private static final String FILENAME_FORMAT = "%s-%s.csv";
    private static final String INVALID_DATE_MESSAGE = "The provided dateTime is not valid.";

    @Value("${report.filename}")
    private String reportFilename;

    private final ReportValidator validator;

    @Override
    public String getFileName(String dateTime) {
        if (dateTime == null) {
            String formattedDateTime = LocalDateTime.now()
                    .format(DateTimeFormatter.ofPattern(DATE_TIME_PATTERN));
            return FILENAME_FORMAT.formatted(reportFilename, formattedDateTime);
        }

        if (Boolean.FALSE.equals(validator.validate(dateTime))) {
            throw new InvalidDateTimeFormatException(INVALID_DATE_MESSAGE);
        }

        return FILENAME_FORMAT.formatted(reportFilename, dateTime);
    }

}
