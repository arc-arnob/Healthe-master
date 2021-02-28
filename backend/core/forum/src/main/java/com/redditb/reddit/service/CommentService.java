package com.redditb.reddit.service;

import java.util.List;

import com.redditb.reddit.dto.CommentRequest;
import com.redditb.reddit.exceptions.SpringRedditException;
import com.redditb.reddit.mapper.CommentMapper;
import com.redditb.reddit.model.Comment;
import com.redditb.reddit.model.Post;
import com.redditb.reddit.model.User;
import com.redditb.reddit.repository.CommentRepository;
import com.redditb.reddit.repository.PostRepository;
import com.redditb.reddit.repository.UserRepository;

import org.springframework.stereotype.Service;

import lombok.AllArgsConstructor;

import static java.util.stream.Collectors.toList;

@Service
@AllArgsConstructor
public class CommentService {
    private PostRepository postRepository;
    private AuthService authService;
    private CommentRepository commentRepository;
    private CommentMapper commentMapper;
    private UserRepository userRepository;

    public String save(CommentRequest commentRequest){
        Post post = postRepository.findById(commentRequest.getPostId())
                    .orElseThrow(() -> new SpringRedditException("Post with id doesnt exist"+ commentRequest.getPostId()));
        Comment comment = commentMapper.map(commentRequest, post, authService.getCurrentUser());

        commentRepository.save(comment);
        return "Commented!";
    }

    public List<CommentRequest> getAllCommentsForPost(Long postId) {
        Post post = postRepository.findById(postId)
                    .orElseThrow(() -> new SpringRedditException(postId.toString()));
        return commentRepository.findByPost(post)
                .stream()
                .map(commentMapper::mapToDto).collect(toList());
    }

    public List<CommentRequest> getAllCommentsForUser() {
        String username = authService.getCurrentUser().getUsername();
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new SpringRedditException(username));
        return commentRepository.findAllByUser(user)
                .stream()
                .map(commentMapper::mapToDto)
                .collect(toList());
    }

    public String deleteCommentById(Long id){
        commentRepository.deleteById(id);
        return "Comment Deleted";
    }
}
