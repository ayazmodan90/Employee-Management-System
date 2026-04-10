package com.example.demo.controller;

import com.example.demo.entity.Department;
import com.example.demo.service.DepartmentService;
import jakarta.servlet.http.HttpSession;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/admin/departments")
public class DepartmentController {

    private final DepartmentService departmentService;

    public DepartmentController(DepartmentService departmentService) {
        this.departmentService = departmentService;
    }

    // 🔹 LIST PAGE
    @GetMapping
    public String listDepartments(Model model, HttpSession session) {

        if (session.getAttribute("loggedInUser") == null) {
            return "redirect:/auth/login";
        }

        model.addAttribute("departments", departmentService.getAllDepartments());
        return "admin/departments";
    }

    // 🔹 ADD PAGE
    @GetMapping("/add")
    public String addDepartmentPage(Model model) {
        model.addAttribute("department", new Department());
        return "admin/add-department";
    }

    // 🔹 SAVE
    @PostMapping("/save")
    public String saveDepartment(@ModelAttribute Department department) {
        departmentService.saveDepartment(department);
        return "redirect:/admin/departments";
    }

    // 🔹 EDIT PAGE
    @GetMapping("/edit/{id}")
    public String editDepartment(@PathVariable Long id, Model model) {
        model.addAttribute("department", departmentService.getDepartmentById(id));
        return "admin/edit-department";
    }

    // 🔹 UPDATE
    @PostMapping("/update")
    public String updateDepartment(@ModelAttribute Department department) {
        departmentService.saveDepartment(department);
        return "redirect:/admin/departments";
    }

    // 🔹 DELETE
    @GetMapping("/delete/{id}")
    public String deleteDepartment(@PathVariable Long id) {
        departmentService.deleteDepartment(id);
        return "redirect:/admin/departments";
    }
}