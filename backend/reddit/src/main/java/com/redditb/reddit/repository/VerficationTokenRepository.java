package com.redditb.reddit.repository;

import java.util.Optional;

import com.redditb.reddit.model.VerificationToken;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface VerficationTokenRepository extends JpaRepository<VerificationToken,Long>{

	Optional<VerificationToken> findByToken(String token);
    
}
