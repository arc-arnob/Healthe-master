package com.redditb.reddit.service;

import java.util.List;

import javax.transaction.Transactional;

import com.redditb.reddit.dto.SubredditDto;
import com.redditb.reddit.exceptions.SpringRedditException;
import com.redditb.reddit.mapper.SubRedditMapper;
import com.redditb.reddit.model.Subreddit;

import com.redditb.reddit.model.UserTesting;
import com.redditb.reddit.repository.SubredditRepository;
import com.redditb.reddit.repository.TestingRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import static java.util.stream.Collectors.toList;

@Service
@AllArgsConstructor
@Slf4j
public class SecuredApiService {

    @Autowired
    private final SubredditRepository subRedditRepository;
    @Autowired
    private SubRedditMapper subRedditMapper;
    private AuthService authservice;

    public String test(){
        return "Test Ran";
    }
    

    @Transactional
    public String save(SubredditDto subreddit) { //8 problem must be here

        System.out.println("Here inside securedAPiService save"); // 7 not reaching here
        Subreddit subReddit = subRedditMapper.mapDtoToSubreddit(subreddit, authservice.getCurrentUser());
        subRedditRepository.save(subReddit);
        return "Saved Success";

    }
    @Transactional
    public List<SubredditDto> getAll(){
        return subRedditRepository.findAll()
                .stream()
                .map(subRedditMapper::mapSubredditToDto)
                .collect(toList());
    }

    @Transactional
    public SubredditDto  getById(Long id){
        Subreddit subreddit = subRedditRepository.findById(id)
                .orElseThrow(() -> new SpringRedditException("No subreddit found with ID - " + id));
        return subRedditMapper.mapSubredditToDto(subreddit);
    }

    
}
