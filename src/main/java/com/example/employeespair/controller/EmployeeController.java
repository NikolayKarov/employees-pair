package com.example.employeespair.controller;

import com.example.employeespair.dto.EmployeeDto;
import com.example.employeespair.model.Employee;
import com.example.employeespair.service.EmployeeService;
import com.example.employeespair.util.CSVParser;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.Valid;
import java.util.List;

@Controller
@RequestMapping("/employee")
public class EmployeeController {

    @Autowired
    private EmployeeService employeeService;

    @PostMapping
    public ResponseEntity<String> saveEmployee(@RequestBody EmployeeDto employeeDto) {
        employeeService.saveEmployee(employeeDto);
        return ResponseEntity.ok("Employee saved successful!");
    }

    @DeleteMapping("/{id}")
    @Transactional
    public ResponseEntity<String> deleteEmployee(@PathVariable Long id) {
        employeeService.deleteEmployeeById(id);
        return ResponseEntity.ok("Employee removed successful!");
    }

    @PutMapping("/{id}")
    public ResponseEntity<String> updateEmployee(@PathVariable Long id, @RequestBody @Valid EmployeeDto employeeDto) {
        employeeService.updateEmployee(id, employeeDto);
        return ResponseEntity.ok("Employee updated successful!");
    }

    @GetMapping
    public ResponseEntity<List<EmployeeDto>> findAllEmployees() {
        List<EmployeeDto> employees = employeeService.findAllEmployees();
        return ResponseEntity.ok(employees);
    }

    @GetMapping("/{id}")
    public ResponseEntity<List<EmployeeDto>> findEmployeeById(@PathVariable Long id) {
        return ResponseEntity.ok(employeeService.findEmployeeById(id));
    }

    @PostMapping("/longest-pair")
    public ResponseEntity<String> getLongestWorkingPair(@RequestParam("file") MultipartFile file) {
        List<Employee> employees = CSVParser.readDataFromCSV(file);
        employeeService.saveAll(employees);
        return ResponseEntity.ok(employeeService.findLongestWorkingPair(employees).toString());
    }

}
