package com.twoday.wms.warehouse.unit.report.validators;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.MockitoAnnotations;

import com.twoday.wms.warehouse.report.validators.ReportDateTimeValidator;

public class ReportDateTimeValidatorTest {

    @InjectMocks
    private ReportDateTimeValidator validator;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testValidateValidDateTime() {
        assertTrue(validator.validate("20230810-1234"));
    }

    @Test
    public void testValidateInvalidDateTime() {
        assertFalse(validator.validate("202308101234"));
        assertFalse(validator.validate("20230810-12A4"));
        assertFalse(validator.validate("20238A10-1234"));
        assertFalse(validator.validate("20230810-12345"));
        assertFalse(validator.validate("0230810-1234"));
    }

    @Test
    public void testValidateNullDateTime() {
        assertFalse(validator.validate(null));
    }

    @Test
    public void testValidateEmptyDateTime() {
        assertFalse(validator.validate(""));
    }
}
