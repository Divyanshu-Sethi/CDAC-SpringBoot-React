package com.example.master.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;

@Entity
public class InstituteType extends Auditable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id ;

    @NotBlank(message = "Institute-Type name cannot be empty")
    @Column(nullable = false, unique = true)
    private String name;

    public InstituteType() {}

    public InstituteType(String name) { this.name = name; }

    //Getters & Setters

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public String getName() { return name; }
    public void setName(String name) { this.name = name; }
}
