package com.example.demo.repository;

import com.example.demo.entity.Salary;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface SalaryRepository extends JpaRepository<Salary, Long> {

    List<Salary> findByEmployeeId(Long employeeId);
}