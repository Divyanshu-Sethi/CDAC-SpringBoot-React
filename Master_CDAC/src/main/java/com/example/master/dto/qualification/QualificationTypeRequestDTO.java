package com.example.master.dto.qualification;

import jakarta.validation.constraints.NotBlank;


public class QualificationTypeRequestDTO {
    @NotBlank(message = "Qualification name cannot be empty")
    private String name;

    //Constructor
    public QualificationTypeRequestDTO() {}
    public QualificationTypeRequestDTO(String name){
        this.name = name;
    }
    //Getter & Setter
    public String getName() { return name;}
    public void setName(String name) { this.name = name; }
}
