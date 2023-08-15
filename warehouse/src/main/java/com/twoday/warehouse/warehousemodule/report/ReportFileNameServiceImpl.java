package com.twoday.warehouse.warehousemodule.report;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.twoday.warehouse.warehousemodule.report.exceptions.InvalidDateTimeFormatException;
import com.twoday.warehouse.warehousemodule.report.interfaces.ReportFileNameService;
import com.twoday.warehouse.warehousemodule.report.interfaces.ReportValidator;

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
    public String getFileName(Optional<String> dateTime) {
        if (!dateTime.isPresent()) {
            String formattedDateTime = LocalDateTime.now()
                    .format(DateTimeFormatter.ofPattern(DATE_TIME_PATTERN));
            return FILENAME_FORMAT.formatted(reportFilename, formattedDateTime);
        }

        if (Boolean.FALSE.equals(validator.validate(dateTime.get()))) {
            throw new InvalidDateTimeFormatException(INVALID_DATE_MESSAGE);
        }

        return FILENAME_FORMAT.formatted(reportFilename, dateTime.get());
    }

}
