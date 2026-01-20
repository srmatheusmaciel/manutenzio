package com.acme.cars.payload;

import com.acme.cars.model.Role;
import lombok.Data;

@Data
public class RegisterRequest {
    private String nome;
    private String email;
    private String password;
    private Role role;
}