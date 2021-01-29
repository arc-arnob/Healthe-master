package com.redditb.reddit.mapper;

import java.util.List;

import com.redditb.reddit.dto.SubredditDto;
import com.redditb.reddit.model.Post;
import com.redditb.reddit.model.Subreddit;
import com.redditb.reddit.model.User;

import org.mapstruct.InheritInverseConfiguration;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;



@Mapper(componentModel = "spring")
public interface SubRedditMapper {
    
    @Mapping(target = "numberOfPosts", expression = "java(mapPosts(subreddit.getPosts()))")
    SubredditDto mapSubredditToDto(Subreddit subreddit);

    default Integer mapPosts(List<Post> numberOfPosts) {
        return numberOfPosts.size();
    }

    @InheritInverseConfiguration
    @Mapping(target = "posts", ignore = true) // Ignore the post field.
    @Mapping(target = "user", source = "user") // This is for supplying current user.
    Subreddit mapDtoToSubreddit(SubredditDto subredditDto, User user);
}
