package com.redditb.reddit.repository;

import java.util.Optional;

import com.redditb.reddit.model.Post;
import com.redditb.reddit.model.User;
import com.redditb.reddit.model.Vote;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface VoteRepository extends JpaRepository<Vote,Long>{

	Optional<Vote> findTopByPostAndUserOrderByVoteIdDesc(Post post, User currentUser);
    
}
