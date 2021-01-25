package com.jwt_auth_service.jwt_auth_service.Repository;

import java.util.Optional;

import com.jwt_auth_service.jwt_auth_service.model.User;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<User, Long>{

    Optional<User> findByUsername(String username);
    
}
