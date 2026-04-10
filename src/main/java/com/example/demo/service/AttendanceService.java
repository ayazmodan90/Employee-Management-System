package com.example.demo.service;

import com.example.demo.entity.Attendance;
import com.example.demo.repository.AttendanceRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDate;

@Service
public class AttendanceService {

    private final AttendanceRepository repo;

    public AttendanceService(AttendanceRepository repo) {
        this.repo = repo;
    }

    // MARK PRESENT
    public Attendance markPresent(Long userId) {

        LocalDate today = LocalDate.now();

        return repo.findByUserIdAndDate(userId, today)
                .orElseGet(() -> {
                    Attendance att = new Attendance();
                    att.setUserId(userId);
                    att.setDate(today);
                    att.setStatus("PRESENT");
                    return repo.save(att);
                });
    }

    // GET TODAY STATUS
    public Attendance getTodayAttendance(Long userId) {
        return repo.findByUserIdAndDate(userId, LocalDate.now())
                .orElse(null);
    }
}