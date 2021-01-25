package com.redditb.reddit.repository;

import java.util.List;
import java.util.Optional;

import com.redditb.reddit.model.Comment;
import com.redditb.reddit.model.Post;
import com.redditb.reddit.model.User;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CommentRepository extends JpaRepository <Comment, Long> {

	List<Comment> findByPost(Post post);

    List<Comment> findAllByUser(User user);
    
}
