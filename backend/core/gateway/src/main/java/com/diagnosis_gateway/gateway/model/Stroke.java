package com.diagnosis_gateway.gateway.model;
import javax.persistence.Entity;
import javax.persistence.Id;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.GeneratedValue;
import static javax.persistence.GenerationType.IDENTITY;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Stroke {
    @Id
    @GeneratedValue(strategy = IDENTITY)
    private Integer id;
    private String pat_username;
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
    private Integer stroke;
    private Double Probability;
}
