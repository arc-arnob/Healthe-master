package com.medication.medicationSystem.repository;

import java.util.List;

import com.medication.medicationSystem.model.AssignMedication;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface MedicationRepository extends MongoRepository<AssignMedication, Integer>{
    
    public List<AssignMedication> findByDocId(String docId);
    public List<AssignMedication> findByPatId(String patId);

    @Query("db.Medication.find({ patId: ?1 }, { coordiantes:1 })")
    public List<Double> findLocationByPatId(String patId);


}
