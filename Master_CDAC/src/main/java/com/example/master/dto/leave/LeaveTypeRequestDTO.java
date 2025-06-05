package com.example.master.dto.leave;

import jakarta.validation.constraints.NotBlank;

public class LeaveTypeRequestDTO {
    @NotBlank(message = "Leave name cannot be empty")
    private String name;

    //Constructor
    public LeaveTypeRequestDTO() {}
    public LeaveTypeRequestDTO(String name) {
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
