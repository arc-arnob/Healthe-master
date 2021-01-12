package com.redditb.reddit.repository;

import com.redditb.reddit.model.UserTesting;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TestingRepository extends JpaRepository<UserTesting, Long> {
    
}
