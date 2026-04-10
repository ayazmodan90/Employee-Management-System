package com.example.demo.service;

import com.example.demo.entity.Salary;
import com.example.demo.repository.SalaryRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
public class SalaryService {

    private final SalaryRepository repo;

    public SalaryService(SalaryRepository repo) {
        this.repo = repo;
    }

    public void paySalary(Long employeeId, Double amount) {

        Salary salary = new Salary();
        salary.setEmployeeId(employeeId);
        salary.setAmount(amount);
        salary.setPaymentDate(LocalDate.now());
        salary.setStatus("PAID");

        repo.save(salary);
    }

    public List<Salary> getEmployeeSalary(Long employeeId) {
        return repo.findByEmployeeId(employeeId);
    }
}