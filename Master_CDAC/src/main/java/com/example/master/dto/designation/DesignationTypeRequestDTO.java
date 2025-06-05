package com.example.master.dto.designation;

import jakarta.validation.constraints.NotBlank;

public class DesignationTypeRequestDTO {
    @NotBlank(message = "Designation name cannot be empty")
    private String name;

    //Constructor
    public DesignationTypeRequestDTO() {}
    public DesignationTypeRequestDTO(String name) {
        this.name = name;
    }

    //Getter & Setter
    public String getName() { return name; }
    public void setName(String name) { this.name = name; }
}
