package com.example.employeespair.service;

import com.example.employeespair.dto.EmployeeDto;
import com.example.employeespair.exception.EmployeeNotFoundException;
import com.example.employeespair.model.Employee;
import com.example.employeespair.model.EmployeePair;
import com.example.employeespair.repository.EmployeeRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class EmployeeService {

    @Autowired
    private EmployeeRepository employeeRepository;

    @Autowired
    private ModelMapper modelMapper;

    public void saveEmployee(EmployeeDto employeeDto) {
        Employee employee = new Employee();
        employee.setEmployeeId(employeeDto.getEmployeeId());
        employee.setProjectId(employeeDto.getProjectId());
        employee.setDateFrom(employeeDto.getDateFrom());
        employee.setDateTo(employeeDto.getDateTo());
        employeeRepository.save(employee);
    }

    public void deleteEmployeeById(Long employeeId) {
        if (employeeRepository.existsByEmployeeId(employeeId)) {
            employeeRepository.deleteByEmployeeId(employeeId);
        } else {
            throw new EmployeeNotFoundException("Employee with id: " + employeeId + " not found.");
        }
    }

    public void updateEmployee(Long employeeId, EmployeeDto employeeDto) {
        Optional<Employee> employee = employeeRepository.findById(employeeId);
        if (employee.isPresent()) {
            employee.get().setEmployeeId(employeeDto.getEmployeeId());
            employee.get().setProjectId(employeeDto.getProjectId());
            employee.get().setDateFrom(employeeDto.getDateFrom());
            employee.get().setDateTo(employeeDto.getDateTo());
            employeeRepository.save(employee.get());
        } else {
            throw new EmployeeNotFoundException("Employee with id: " + employeeId + " not found.");
        }
    }

    public List<EmployeeDto> findAllEmployees() {
        return employeeRepository.findAll().stream()
                .map((employee) -> modelMapper.map(employee, EmployeeDto.class)).toList();
    }

    public List<EmployeeDto> findEmployeeById(Long id) {
        return employeeRepository.findByEmployeeId(id).stream()
                .map((element) -> modelMapper.map(element, EmployeeDto.class))
                .collect(Collectors.toList());
    }

    public EmployeePair findLongestWorkingPair(List<Employee> employees) {
        Map<EmployeePair, Long> pairDurationMap = new HashMap<>();

        for (int i = 0; i < employees.size(); i++) {
            for (int j = i + 1; j < employees.size(); j++) {
                Employee firstEmployee = employees.get(i);
                Employee secondEmployee = employees.get(j);
                if (!firstEmployee.getEmployeeId().equals(secondEmployee.getEmployeeId()) &&
                        firstEmployee.getProjectId().equals(secondEmployee.getProjectId())) {

                    long duration = calculateDuration(firstEmployee.getDateFrom(), firstEmployee.getDateTo(),
                            secondEmployee.getDateFrom(), secondEmployee.getDateTo());

                    EmployeePair employeePair = new EmployeePair(firstEmployee, secondEmployee, firstEmployee.getProjectId(), duration);
                    pairDurationMap.merge(employeePair, duration, Long::sum);
                }
            }
        }
        return pairDurationMap.entrySet().stream()
                .max(Map.Entry.comparingByValue())
                .map(Map.Entry::getKey)
                .orElse(null);
    }

    public void saveAll(List<Employee> employees) {
        employeeRepository.saveAll(checkForDuplication(employees));
    }

    private long calculateDuration(LocalDate firstEmployeeStartDate, LocalDate firstEmployeeEndDate,
                                   LocalDate secondEmployeeStartDate, LocalDate secondEmployeeEndDate) {

        long duration = 0;
        if (firstEmployeeStartDate.isBefore(secondEmployeeEndDate) && secondEmployeeStartDate.isBefore(firstEmployeeEndDate)) {
            LocalDate startDate = firstEmployeeStartDate.isBefore(secondEmployeeStartDate) ? secondEmployeeStartDate : firstEmployeeStartDate;
            LocalDate endDate = firstEmployeeEndDate.isBefore(secondEmployeeEndDate) ? firstEmployeeEndDate : secondEmployeeEndDate;

            duration = ChronoUnit.DAYS.between(startDate, endDate);

        }
        return duration;
    }

    private List<Employee> checkForDuplication(List<Employee> employees) {
        Set<Employee> uniqueEmployees = new HashSet<>();
        for (Employee employee : employees) {
            boolean isDuplicate = employeeRepository.findByEmployeeId(employee.getEmployeeId()).stream()
                    .anyMatch(existingEmployee -> existingEmployee.equals(employee));

            if (!isDuplicate) {
                uniqueEmployees.add(employee);
            }
        }
        return new ArrayList<>(uniqueEmployees);
    }
}
