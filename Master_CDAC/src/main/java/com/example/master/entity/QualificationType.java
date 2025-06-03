package com.example.master.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;

@Entity
public class QualificationType extends Auditable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @NotBlank(message = "Qualification name cannot be empty")
    @Column(nullable = false, unique = true)
    private String name;

    public QualificationType() {}

    public QualificationType(String name) {
        this.name = name;
    }

    //Getter & Setter

    public void setId(Long id) {
        this.id = id;
    }
    public Long getId() {
        return id;
    }
    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }
}
