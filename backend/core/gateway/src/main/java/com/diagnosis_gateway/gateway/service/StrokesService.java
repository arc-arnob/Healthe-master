package com.diagnosis_gateway.gateway.service;

import com.diagnosis_gateway.gateway.Repository.StrokeRepository;
import com.diagnosis_gateway.gateway.dto.StrokeDto;
import com.diagnosis_gateway.gateway.model.Stroke;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class StrokesService {
    @Autowired
    private AuthService authService;

    @Autowired
    private StrokeRepository strokeRepo;

    public String save(Stroke stroke){

        String username = authService.getCurrentUser().getUsername();
        stroke.setPat_username(username);

        strokeRepo.save(stroke);
        return "Success";
    }
}
