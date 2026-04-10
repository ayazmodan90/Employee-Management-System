package com.example.demo.controller;

import com.example.demo.entity.Employee;
import com.example.demo.entity.User;
import com.example.demo.service.EmployeeService;
import jakarta.servlet.http.HttpSession;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import com.example.demo.service.SalaryService;
@Controller
@RequestMapping("/admin")
public class AdminController {

    private final EmployeeService employeeService;
    private final SalaryService salaryService; // 🔥 NEW

    public AdminController(EmployeeService employeeService,
                           SalaryService salaryService) {
        this.employeeService = employeeService;
        this.salaryService = salaryService;
    }
    @GetMapping("/dashboard")
    public String adminDashboard(Model model, HttpSession session) {

        User user = (User) session.getAttribute("loggedInUser");

        if (user == null) {
            return "redirect:/auth/login";
        }

        if (!"ADMIN".equals(user.getRole())) {
            return "redirect:/auth/dashboard";
        }

        model.addAttribute("totalEmployees", employeeService.getAllEmployees().size());

        return "admin/dashboard";  // 🔥 IMPORTANT
    }

    // ================= ADMIN DASHBOARD =================
    @GetMapping("/addEmployee")
    public String addEmployeePage(Model model) {

        model.addAttribute("employee", new Employee());

        return "admin/addEmployee";
    }
    // ================= SHOW EMPLOYEES =================
    @GetMapping("/employees")
    public String showEmployees(Model model, HttpSession session) {

        User user = (User) session.getAttribute("loggedInUser");

        if (user == null) {
            return "redirect:/auth/login";
        }

        if (!"ADMIN".equals(user.getRole())) {
            return "redirect:/auth/dashboard";
        }

        model.addAttribute("listEmployees", employeeService.getAllEmployees());

        // 🔥 IMPORTANT: file name match karo
        return "admin/showRecords";
    }

    // ================= ADD EMPLOYEE PAGE =================
//    @GetMapping("/paySalary/{id}")
//    public String paySalaryPage(@PathVariable Long id, Model model) {
//
//        Employee employee = employeeService.getEmployeeById(id);
//
//        model.addAttribute("employee", employee);
//
//        return "admin/paySalary"; // 🔥 page name
//    }
    
    @GetMapping("/showFormForUpdate/{id}")
    public String updateForm(@PathVariable Long id, Model model) {

        Employee employee = employeeService.getEmployeeById(id);
        model.addAttribute("employee", employee);

        return "admin/addEmployee";
    }
    
 // ================= PAY SALARY PAGE =================
    @GetMapping("/paySalary/{id}")
    public String paySalaryPage(@PathVariable Long id, Model model) {

        Employee employee = employeeService.getEmployeeById(id);

        model.addAttribute("employee", employee);

        return "admin/paySalary"; // 🔥 page name
    }

    // ================= PROCESS SALARY =================
    @PostMapping("/paySalary")
    public String processSalary(@RequestParam Long employeeId,
                               @RequestParam Double amount) {

        salaryService.paySalary(employeeId, amount);

        return "redirect:/admin/employees";
    }
}
