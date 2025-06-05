package com.example.master.dto.usertype;

import jakarta.validation.constraints.NotBlank;

public class UsertypeTypeRequestDTO {
    @NotBlank(message = "Usertype cannot be empty")
    private String name;

    //Constructor
    public UsertypeTypeRequestDTO() {}
    public UsertypeTypeRequestDTO(String name) {
        this.name = name;
    }

    //Getter & Setter
    public String getName() { return name; }
    public void setName(String name) { this.name = name; }
}
