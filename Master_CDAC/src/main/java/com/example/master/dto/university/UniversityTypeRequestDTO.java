package com.example.master.dto.university;

import jakarta.validation.constraints.NotBlank;

public class UniversityTypeRequestDTO {
    @NotBlank(message = "University name cannot be empty")
    private String name;

    //Constructor
    public UniversityTypeRequestDTO() {}
    public UniversityTypeRequestDTO(String name) {
        this.name = name;
    }

    //Getter & Setter
    public String getName() { return name; }
    public void setName(String name) { this.name = name; }
}
