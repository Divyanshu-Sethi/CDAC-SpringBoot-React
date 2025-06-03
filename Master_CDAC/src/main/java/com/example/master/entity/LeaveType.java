package com.example.master.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;

@Entity
public class LeaveType extends Auditable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @NotBlank(message = "Leave name cannot be empty")
    @Column(nullable = false, unique = true)
    private String name;

    public LeaveType() {}

    public LeaveType(String name) {
        this.name = name;
    }

    // Getters & Setters

    public void setId(Long id) {
        this.id = id;
    }
    public Long getId() { return id; }
    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }
}
