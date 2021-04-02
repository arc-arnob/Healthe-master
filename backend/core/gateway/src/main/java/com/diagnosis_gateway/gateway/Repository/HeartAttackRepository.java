package com.diagnosis_gateway.gateway.Repository;

import java.util.List;

import com.diagnosis_gateway.gateway.model.HeartAttack;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface HeartAttackRepository extends JpaRepository<HeartAttack, Integer> {

    List<HeartAttack> findByUsername(String username);
    
}
