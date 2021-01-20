package com.redditb.reddit.repository;

import java.util.Optional;

import com.redditb.reddit.model.RefreshToken;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RefreshTokenRepository extends JpaRepository<RefreshToken, Long>{

    Optional<RefreshToken> findByToken(String token);

    void deleteByToken(String token);
    
}
