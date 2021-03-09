package com.diagnosis_gateway.gateway.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.GeneratedValue;
import static javax.persistence.GenerationType.IDENTITY;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class StrokeDto {
  
    private Double gender;
    private Double ever_married;
    private Double work_type;
    private Double residence_type;  
    private Double smoking_status;
    private Double age; 
    private Double hypertension;
    private Double heart_disease;
    private Double avg_glucose_level;
    private Double bmi;
    
}
