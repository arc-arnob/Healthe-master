package com.medication.medicationSystem.model;

import java.util.List;
import org.springframework.data.mongodb.core.mapping.Document;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Document
@Data
@AllArgsConstructor
@NoArgsConstructor
public class AssignMedication {
    private String patId; // this has to be sent from backend
    private String docId; // this can be searched by name in appservice and can be sent
    private List<Double> coordinates; // current location or leaflet map
    private Medication medication; // you know.
}
