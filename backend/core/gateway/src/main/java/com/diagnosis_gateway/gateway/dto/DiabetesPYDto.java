package com.diagnosis_gateway.gateway.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class DiabetesPYDto {
    private Double glucose;
    private Double insulin;
    private Double bmi;
    private Double age;
}
