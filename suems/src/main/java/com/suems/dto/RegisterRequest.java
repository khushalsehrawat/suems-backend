package com.suems.dto;


import jakarta.persistence.Column;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public class RegisterRequest {

    @Column(unique = true)
    @Email(message = "Please provide a valid email address")
    @NotBlank(message = "Email is required")
    private String email;

    @NotBlank @Size(min = 4, max = 16)
    private String password;

    public RegisterRequest(){}

    public RegisterRequest(String email, String password) {
        this.email = email;
        this.password = password;
    }


    public @Email(message = "Please provide a valid email address") @NotBlank(message = "Email is required") String getEmail() {
        return email;
    }

    public void setEmail(@Email(message = "Please provide a valid email address") @NotBlank(message = "Email is required") String email) {
        this.email = email;
    }

    public @NotBlank @Size(min = 4, max = 16) String getPassword() {
        return password;
    }

    public void setPassword(@NotBlank @Size(min = 4, max = 16) String password) {
        this.password = password;
    }
}
