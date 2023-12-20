package com.example.employeespair.service;

import com.example.employeespair.dto.EmployeeDto;
import com.example.employeespair.model.Employee;
import com.example.employeespair.model.EmployeePair;
import com.example.employeespair.repository.EmployeeRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.*;

@Service
public class EmployeeService {

    @Autowired
    private EmployeeRepository employeeRepository;

    @Autowired
    private ModelMapper modelMapper;

    public void saveEmployee(EmployeeDto employeeDto) {
        Employee employee = new Employee();
        employee.setProjectId(employeeDto.getProjectId());
        employee.setDateFrom(employeeDto.getDateFrom());
        employee.setDateTo(employeeDto.getDateTo());
        employeeRepository.save(employee);
    }

    public void deleteEmployeeById(Long id) {
        if (employeeRepository.existsById(id)) {
            employeeRepository.deleteById(id);
        } else {
            throw new RuntimeException("Employee not found");
        }
    }

    public void updateEmployee(Long id, EmployeeDto employeeDto) {
        Optional<Employee> employee = employeeRepository.findById(id);
        if (employee.isPresent()) {
            employee.get().setProjectId(employeeDto.getProjectId());
            employee.get().setDateFrom(employeeDto.getDateFrom());
            employee.get().setDateTo(employeeDto.getDateTo());
            employeeRepository.save(employee.get());
        } else {
            throw new RuntimeException("Exception");
        }
    }

    public List<EmployeeDto> findAllEmployees() {
        return employeeRepository.findAll().stream()
                .map((employee) -> modelMapper.map(employee, EmployeeDto.class)).toList();
    }

    public EmployeeDto findEmployeeById(Long id) {
        return employeeRepository.findById(id).stream().findFirst()
                .map((employee) -> modelMapper.map(employee, EmployeeDto.class)).orElseThrow();
    }

    public EmployeePair findLongestWorkingPair(List<Employee> employees) {
        Map<EmployeePair, Long> pairDurationMap = new HashMap<>();

        for (Employee firstEmployee : employees) {
            for (Employee secondEmployee : employees) {
                if (!Objects.equals(firstEmployee.getId(), secondEmployee.getId()) &&
                        Objects.equals(firstEmployee.getProjectId(), secondEmployee.getProjectId())) {

                    long duration = calculateDuration(firstEmployee.getDateFrom(), firstEmployee.getDateTo(),
                            secondEmployee.getDateFrom(), secondEmployee.getDateTo());

                    EmployeePair employeePair = new EmployeePair(firstEmployee, secondEmployee, firstEmployee.getProjectId(), duration);
                    pairDurationMap.put(employeePair, pairDurationMap.getOrDefault(employeePair, employeePair.getDuration() + duration));
                }
            }
        }
        EmployeePair longestWorkingPair = null;
        long maxDuration = 0;

        for (Map.Entry<EmployeePair, Long> entry : pairDurationMap.entrySet()) {
            if (entry.getValue() > maxDuration) {
                longestWorkingPair = entry.getKey();
                maxDuration = entry.getValue();
            }
        }
        return longestWorkingPair;
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
}
