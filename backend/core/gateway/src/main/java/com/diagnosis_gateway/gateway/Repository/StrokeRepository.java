package com.diagnosis_gateway.gateway.Repository;

import com.diagnosis_gateway.gateway.model.Stroke;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface StrokeRepository extends JpaRepository<Stroke, Integer>{
    
}
