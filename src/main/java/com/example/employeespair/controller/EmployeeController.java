package com.example.employeespair.controller;

import com.example.employeespair.dto.EmployeeDto;
import com.example.employeespair.service.EmployeeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

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

    @DeleteMapping
    public ResponseEntity<String> deleteBook(@RequestParam Long id) {
        employeeService.deleteEmployeeById(id);
        return ResponseEntity.ok("Employee removed successful!");
    }

    @PutMapping
    public ResponseEntity<String> updateEmployee(@RequestParam Long id, @RequestBody EmployeeDto employeeDto) {
        employeeService.updateEmployee(id, employeeDto);
        return ResponseEntity.ok("Employee updated successful!");
    }

    @GetMapping
    public ResponseEntity<List<EmployeeDto>> findAllEmployees() {
        List<EmployeeDto> employees = employeeService.findAllEmployees();
        return ResponseEntity.ok(employees);
    }

    @GetMapping("/{id}")
    public ResponseEntity<EmployeeDto> findEmployeeById(@PathVariable Long id) {
        return ResponseEntity.ok(employeeService.findEmployeeById(id));
    }

}
