package com.redditb.reddit.repository;

import java.util.List;
import java.util.Optional;

import com.redditb.reddit.model.Post;
import com.redditb.reddit.model.Subreddit;
import com.redditb.reddit.model.User;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PostRepository extends JpaRepository<Post,Long>{

	List<Post> findAllBySubreddit(Subreddit subreddit);

	List<Post> findByUser(User user);
    
}
