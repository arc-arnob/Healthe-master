package com.diagnosis_gateway.gateway.Repository;

import com.diagnosis_gateway.gateway.model.Diabetes;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DiabetesRepository extends JpaRepository<Diabetes,String> {
    
}
