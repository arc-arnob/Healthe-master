package com.medication.medicationSystem.UserService.Repository;

import java.util.Optional;

import com.medication.medicationSystem.UserService.model.User;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<User, Long>{

    Optional<User> findByUsername(String username);
    
}
