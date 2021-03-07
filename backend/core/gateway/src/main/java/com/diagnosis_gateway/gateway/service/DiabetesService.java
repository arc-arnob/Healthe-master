package com.diagnosis_gateway.gateway.service;

import com.diagnosis_gateway.gateway.Repository.DiabetesRepository;
import com.diagnosis_gateway.gateway.model.Diabetes;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class DiabetesService {
    
    @Autowired
    private AuthService authService;
    @Autowired
    private DiabetesRepository diabetesRepo;

    public String save(Diabetes diabetes){
        String username = authService.getCurrentUser().getUsername();

        diabetes.setPat_username(username);
        diabetesRepo.save(diabetes);

        return "Saved";
        


    }
}
