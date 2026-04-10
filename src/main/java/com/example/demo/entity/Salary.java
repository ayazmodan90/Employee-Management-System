package com.example.demo.entity;

import jakarta.persistence.*;
import java.time.LocalDate;

@Entity
@Table(name = "salary")
public class Salary {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Long employeeId;

    private Double amount;

    private LocalDate paymentDate;

    private String status; // PAID / PENDING

    // GETTERS
    public Long getId() { return id; }
    public Long getEmployeeId() { return employeeId; }
    public Double getAmount() { return amount; }
    public LocalDate getPaymentDate() { return paymentDate; }
    public String getStatus() { return status; }

    // SETTERS
    public void setId(Long id) { this.id = id; }
    public void setEmployeeId(Long employeeId) { this.employeeId = employeeId; }
    public void setAmount(Double amount) { this.amount = amount; }
    public void setPaymentDate(LocalDate paymentDate) { this.paymentDate = paymentDate; }
    public void setStatus(String status) { this.status = status; }
}