package com.example.employeespair.util;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class DateParser {
    private static final DateTimeFormatter[] SUPPORTED_FORMATS = {
            DateTimeFormatter.ofPattern("yyyy-MM-dd"),
            DateTimeFormatter.ofPattern("MM-dd-yyyy")
    };

    public static LocalDate parseDate(String date) {
        for (DateTimeFormatter formatter : SUPPORTED_FORMATS) {
            try {
                return LocalDate.parse(date, formatter);
            } catch (Exception ignored) {

            }
        }
        throw new IllegalArgumentException("Unsupported date format: " + date);
    }
}
