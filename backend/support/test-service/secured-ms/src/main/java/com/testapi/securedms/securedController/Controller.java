package com.testapi.securedms.securedController;

import org.springframework.web.bind.annotation.RestController;

import lombok.AllArgsConstructor;

import org.springframework.web.bind.annotation.GetMapping;



@RestController
@AllArgsConstructor
public class Controller {
    
    @GetMapping(value="/api/test")
    public String getMethodName() {
        return "This is running motherfucker";
    }
    

}
