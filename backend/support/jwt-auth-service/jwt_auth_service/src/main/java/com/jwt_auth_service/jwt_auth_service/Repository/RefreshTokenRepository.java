package com.jwt_auth_service.jwt_auth_service.Repository;

import java.util.Optional;

import com.jwt_auth_service.jwt_auth_service.model.RefreshToken;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RefreshTokenRepository extends JpaRepository<RefreshToken, Long>{

    Optional<RefreshToken> findByToken(String token);

    void deleteByToken(String token);
    
}
