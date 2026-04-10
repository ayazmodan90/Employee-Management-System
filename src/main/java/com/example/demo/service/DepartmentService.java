package com.example.demo.service;

import com.example.demo.entity.Department;
import java.util.List;

public interface DepartmentService {

    Department saveDepartment(Department department);

    List<Department> getAllDepartments();

    Department getDepartmentById(Long id);

    void deleteDepartment(Long id);
}