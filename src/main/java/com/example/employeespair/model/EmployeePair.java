package com.example.employeespair.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@AllArgsConstructor
@Getter
@Setter
public class EmployeePair {
    private Employee employee1;
    private Employee employee2;
    private Long projectId;
    private long duration;
}
