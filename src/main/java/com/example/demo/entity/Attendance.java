package com.example.demo.entity;

import jakarta.persistence.*;
import java.time.LocalDate;

@Entity
@Table(name = "attendance")
public class Attendance {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Long userId;

    private LocalDate date;

    private String status; // PRESENT / ABSENT

    // ===== GETTERS =====
    public Long getId() { return id; }

    public Long getUserId() { return userId; }

    public LocalDate getDate() { return date; }

    public String getStatus() { return status; }

    // ===== SETTERS =====
    public void setId(Long id) { this.id = id; }

    public void setUserId(Long userId) { this.userId = userId; }

    public void setDate(LocalDate date) { this.date = date; }

    public void setStatus(String status) { this.status = status; }
}