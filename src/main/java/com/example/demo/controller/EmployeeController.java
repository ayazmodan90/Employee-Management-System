package com.example.demo.controller;

import com.example.demo.entity.Employee;
import com.example.demo.service.EmployeeService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/admin")   // 🔥 IMPORTANT
public class EmployeeController {

    private final EmployeeService service;

    public EmployeeController(EmployeeService service) {
        this.service = service;
    }

    // SAVE
    @PostMapping("/saveEmployee")
    public String saveEmployee(@ModelAttribute Employee employee) {
        service.saveEmployee(employee);
        return "redirect:/admin/employees"; // 🔥 FIX
    }

    // EDIT
    @GetMapping("/edit/{id}")
    public String updateForm(@PathVariable Long id, Model model) {
        Employee employee = service.getEmployeeById(id);
        model.addAttribute("employee", employee);
        return "admin/addEmployee";
    }

    // DELETE
    @GetMapping("/delete/{id}")
    public String delete(@PathVariable Long id) {
        service.deleteEmployee(id);
        return "redirect:/admin/employees";
    }
}