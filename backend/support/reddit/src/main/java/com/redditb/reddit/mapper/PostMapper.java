package com.redditb.reddit.mapper;

import com.github.marlonlom.utilities.timeago.TimeAgo;
import com.redditb.reddit.dto.PostRequest;
import com.redditb.reddit.dto.PostResponse;
import com.redditb.reddit.model.Post;
import com.redditb.reddit.model.Subreddit;
import com.redditb.reddit.model.User;
import com.redditb.reddit.repository.CommentRepository;
import com.redditb.reddit.repository.VoteRepository;
import com.redditb.reddit.service.AuthService;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.springframework.beans.factory.annotation.Autowired;

@Mapper(componentModel = "spring")
public abstract class PostMapper {

    @Autowired
    private CommentRepository commentRepository;
    @Autowired
    private VoteRepository voteRepository;
    @Autowired
    private AuthService authService;
    // This is creating a pogo for Post Model using postrequest dto, subreddit and user models
    @Mapping(target = "createdDate", expression = "java(java.time.Instant.now())")
    @Mapping(target = "description", source = "postRequest.description")
    @Mapping(target = "user", source = "user")
    @Mapping(target = "subreddit", source = "subreddit")
    @Mapping(target = "voteCount", constant = "0")
    public abstract Post map(PostRequest postRequest, Subreddit subreddit, User user);

    // This here is converting Post model to be returned in the form of dto
    @Mapping(target="id", source = "postId")
    @Mapping(target="postName", source = "postName")
    @Mapping(target="description", source = "description")
    @Mapping(target="userName", source = "user.username")
    @Mapping(target="subredditName", source = "subreddit.name")
    @Mapping(target = "commentCount", expression = "java(commentCount(post))")
    @Mapping(target = "duration", expression = "java(getDuration(post))")
    public abstract PostResponse maptoDto(Post post);

    Integer commentCount(Post post) {
        return commentRepository.findByPost(post).size();
    }

    String getDuration(Post post) {
        return TimeAgo.using(post.getCreatedDate().toEpochMilli());
    }
}
