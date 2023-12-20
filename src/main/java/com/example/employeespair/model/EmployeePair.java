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

    @Override
    public String toString() {
        return String.format("Employee1 id: %d\n" +
                "Employee2 id: %d\n" +
                "Project id: %d\n" +
                "Duration: %d days", employee1.getId(), employee2.getId(), projectId, duration);
    }

}
