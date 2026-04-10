package com.example.demo.controller;

import com.example.demo.entity.Attendance;
import com.example.demo.entity.User;
import com.example.demo.service.AttendanceService;
import jakarta.servlet.http.HttpSession;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/auth")
public class AttendanceController {

    private final AttendanceService attendanceService;

    public AttendanceController(AttendanceService attendanceService) {
        this.attendanceService = attendanceService;
    }

    // ================= PAGE =================
    @GetMapping("/attendance")
    public String attendancePage(HttpSession session, Model model) {

        User user = (User) session.getAttribute("loggedInUser");

        if (user == null) {
            return "redirect:/auth/login";
        }

        Attendance attendance = attendanceService.getTodayAttendance(user.getId());

        model.addAttribute("attendance", attendance);
        model.addAttribute("user", user);

        return "auth/attendance";
    }

    // ================= MARK PRESENT =================
    @PostMapping("/attendance/mark")
    public String markAttendance(HttpSession session) {

        User user = (User) session.getAttribute("loggedInUser");

        if (user != null) {
            attendanceService.markPresent(user.getId());
        }

        return "redirect:/auth/attendance";
    }
}