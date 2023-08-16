package com.twoday.wms.warehouse.report.validators;

import org.springframework.stereotype.Service;

import com.twoday.wms.warehouse.report.interfaces.ReportValidator;

@Service
public class ReportDateTimeValidator implements ReportValidator {

    private static final String DATE_TIME_PATTERN = "\\d{8}-\\d{4}";
    private static final String EMPTY_STRING = "";

    @Override
    public Boolean validate(String dateTime) {
        return dateTime != null && !dateTime.equals(EMPTY_STRING) && dateTime.matches(DATE_TIME_PATTERN);
    }

}
