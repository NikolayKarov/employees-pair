package com.example.employeespair.util;

import com.example.employeespair.model.Employee;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class CSVParser {

    public static List<Employee> readDataFromCSV() {

        List<Employee> employees = new ArrayList<>();

        String filePath = "D:\\Nikolay\\Projects\\employees-pair\\src\\main\\resources\\data.csv";
        try (BufferedReader reader = new BufferedReader(new FileReader(filePath))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] data = line.split(",");
                Employee employee = new Employee();
                employee.setId(Long.parseLong(data[0]));
                employee.setProjectId(Long.parseLong(data[1]));
                employee.setDateFrom(DateParser.parseDate(data[2]));

                if (data[3] == null || data[3].equalsIgnoreCase("null")) {
                    employee.setDateTo(LocalDate.now());
                } else {
                    employee.setDateTo(DateParser.parseDate(data[3]));
                }
            }
            return employees;

        } catch (IOException exception) {
            exception.printStackTrace();
        }
        return employees;
    }
}