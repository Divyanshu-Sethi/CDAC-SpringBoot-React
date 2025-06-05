package com.example.master.dto.institute;

import jakarta.validation.constraints.NotBlank;

public class InstituteTypeRequestDTO {
    @NotBlank(message = "Institute name cannot be empty")
    private String name;

    //Constructor
    public InstituteTypeRequestDTO() {}
    public InstituteTypeRequestDTO(String name) {
        this.name = name;
    }

    //Getter & Setter
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
