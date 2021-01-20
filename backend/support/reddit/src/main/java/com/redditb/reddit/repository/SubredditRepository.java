package com.redditb.reddit.repository;

import java.util.Optional;

import com.redditb.reddit.model.Subreddit;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SubredditRepository extends JpaRepository<Subreddit,Long> {

	Optional<Subreddit> findByName(String subredditName);
    
}
