package com.example.demo.service;

import com.example.demo.entity.Employee;
import com.example.demo.repository.EmployeeRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class EmployeeService {

    private final EmployeeRepository employeeRepository;

    public EmployeeService(EmployeeRepository employeeRepository) {
        this.employeeRepository = employeeRepository;
    }

    // GET ALL
    public List<Employee> getAllEmployees() {
        return employeeRepository.findAll();
    }

    // SAVE
    public void saveEmployee(Employee employee) {
        employeeRepository.save(employee);
    }

    // GET BY ID
    public Employee getEmployeeById(Long id) {
        return employeeRepository.findById(id).orElse(null);
    }

    // DELETE
    public void deleteEmployee(Long id) {
        employeeRepository.deleteById(id);
    }
}