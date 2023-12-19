package com.example.employeespair.dto;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
public class EmployeeDto {
    private Long projectId;
    private LocalDate dateFrom;
    private LocalDate dateTo;
}
