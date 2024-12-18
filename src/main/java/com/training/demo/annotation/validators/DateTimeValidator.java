package com.training.demo.annotation.validators;

import com.training.demo.annotation.DateTime;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

public class DateTimeValidator implements ConstraintValidator<DateTime, String> {

    private String pattern;

    @Override
    public void initialize(DateTime constraintAnnotation) {
        this.pattern = constraintAnnotation.pattern();
    }

    @Override
    public boolean isValid(String sDateTime, ConstraintValidatorContext context) {
        if (sDateTime == null)
            return false;
        try {
            LocalDate.parse(sDateTime, DateTimeFormatter.ofPattern(pattern));
        } catch (DateTimeParseException e) {
            return false;
        }
        return true;
    }
}