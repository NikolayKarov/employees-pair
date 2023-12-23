package com.example.employeespair.util;

import com.example.employeespair.exception.EmptyFileException;
import com.example.employeespair.model.Employee;
import org.springframework.web.multipart.MultipartFile;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import static com.example.employeespair.util.DateParser.parseDate;

public class CSVParser {

    public static List<Employee> readDataFromCSV(MultipartFile file) {
        validateFile(file);
        List<Employee> employees = new ArrayList<>();

        try (BufferedReader reader = new BufferedReader(new InputStreamReader(file.getInputStream()))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] data = line.split(",");
                Employee employee = new Employee();
                employee.setEmployeeId(Long.parseLong(data[0].trim()));
                employee.setProjectId(Long.parseLong(data[1].trim()));
                employee.setDateFrom(parseDate(data[2].trim()));

                if (data[3] == null || data[3].equalsIgnoreCase("null")) {
                    employee.setDateTo(LocalDate.now());
                } else {
                    employee.setDateTo(parseDate(data[3]));
                }
                employees.add(employee);
            }
            return employees;

        } catch (IOException exception) {
            exception.printStackTrace();
        }
        return employees;
    }

    private static void validateFile(MultipartFile file) {
        if (file.isEmpty()) {
            throw new EmptyFileException("File is empty!");
        }
        if (!file.getOriginalFilename().endsWith(".csv")) {
            throw new UnsupportedOperationException("Unsupported file format.");
        }
    }
}