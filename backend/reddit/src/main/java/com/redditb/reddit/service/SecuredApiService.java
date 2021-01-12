package com.redditb.reddit.service;

import java.util.List;

import javax.transaction.Transactional;

import com.redditb.reddit.dto.SubredditDto;
import com.redditb.reddit.exceptions.SpringRedditException;
import com.redditb.reddit.model.Subreddit;

import com.redditb.reddit.model.UserTesting;
import com.redditb.reddit.repository.SubredditRepository;
import com.redditb.reddit.repository.TestingRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.extern.slf4j.Slf4j;

@Service
@AllArgsConstructor
@Slf4j
public class SecuredApiService {

    @Autowired
    private final SubredditRepository subRedditRepository;

    public String test(){
        return "Test Ran";
    }
    

    @Transactional
    public String save(SubredditDto subreddit) { //8 problem must be here

        System.out.println("Here inside securedAPiService save"); // 7 not reaching here
        Subreddit subReddit = mapSubredditDto(subreddit);
        subRedditRepository.save(subReddit);
        return "Saved Success";

    }

    private static Subreddit mapSubredditDto(SubredditDto subreddit) {

        return Subreddit.builder()
                .name(subreddit.getName())
                .description(subreddit.getDescription())
                .build();


    }

    
}
