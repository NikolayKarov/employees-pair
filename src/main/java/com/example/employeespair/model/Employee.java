package com.example.employeespair.model;

import jakarta.persistence.*;
import lombok.*;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;
import java.time.LocalDate;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Entity
public class Employee {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @NotNull
    @Positive(message = "Employee ID must be positive number")
    private Long employeeId;
    @NotNull
    @Positive(message = "Project ID must be positive number")
    private Long projectId;
    @NotNull
    private LocalDate dateFrom;
    private LocalDate dateTo;

}
