package com.jwt_auth_service.jwt_auth_service.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
 
// this is a data transfer object... used in controller - AuthController.java
@Data
@AllArgsConstructor
@NoArgsConstructor
public class RegisterRequest {

    private String email;
    private String username;
    private String password;
    private String role;
    
}
