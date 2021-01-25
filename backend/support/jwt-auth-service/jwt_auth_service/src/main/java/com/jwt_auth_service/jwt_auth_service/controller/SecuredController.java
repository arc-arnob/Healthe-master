package com.jwt_auth_service.jwt_auth_service.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/api/securedcontroller")
public class SecuredController {

    @GetMapping("/test")
    public String test(){
        return "Auth server working fine";
    }
    
}
