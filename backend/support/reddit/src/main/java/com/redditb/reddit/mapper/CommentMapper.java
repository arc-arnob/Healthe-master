package com.redditb.reddit.mapper;

import com.redditb.reddit.dto.CommentRequest;
import com.redditb.reddit.model.Comment;
import com.redditb.reddit.model.Post;
import com.redditb.reddit.model.User;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface CommentMapper {
    
    @Mapping(target = "createdDate", expression = "java(java.time.Instant.now())")
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "text", source = "commentRequest.text")
    @Mapping(target = "post", source = "post")
    @Mapping(target = "user", source = "user")
    Comment map(CommentRequest commentRequest, Post post, User user);

    @Mapping(target = "postId", expression = "java(comment.getPost().getPostId())")
    @Mapping(target = "username", expression = "java(comment.getUser().getUsername())")
    CommentRequest mapToDto(Comment comment);
}
