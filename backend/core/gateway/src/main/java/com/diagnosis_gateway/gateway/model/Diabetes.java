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
public class Diabetes {
    @Id
    @GeneratedValue(strategy = IDENTITY)
    private Integer id;
    private String pat_username;
    private Integer pregnacies;
    private Double Glucose; // yes
    private Double BloodPressure;  
    private Double SkinThickness;  
    private Double Insulin;   // yes
    private Double BMI; // yes
    private Double DiabetesPedigreeFunction;
    private Double age;
    private Integer outcome;
    private Double probability;

}
