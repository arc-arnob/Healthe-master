package com.diagnosis_gateway.gateway.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@NoArgsConstructor
@AllArgsConstructor
public class HeartAttackDto {
    private Double age;
    private Double sex;
    private Double cp;
    private Double trestbps;  
    private Double chol;
    private Double fbs;  
    private Double restecg;  
    private Double thalach;  
    private Double exang; 
    private Double oldpeak; 
    private Double slope;
    private Double ca;
    private Double thal;
    
}
