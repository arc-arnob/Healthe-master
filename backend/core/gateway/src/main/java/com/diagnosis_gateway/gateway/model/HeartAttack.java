package com.diagnosis_gateway.gateway.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import static javax.persistence.GenerationType.IDENTITY;


@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class HeartAttack {
    @Id
    @GeneratedValue(strategy = IDENTITY)
    private Integer id;
    private String username;
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
    private Integer outcome;
    private Double probability;
}
