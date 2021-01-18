package com.redditb.reddit.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

import com.redditb.reddit.dto.SubredditDto;
import com.redditb.reddit.model.Subreddit;
import com.redditb.reddit.model.UserTesting;
import com.redditb.reddit.service.SecuredApiService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;


@RestController
@AllArgsConstructor
@RequestMapping("/api/securedController")
public class SecuredController {

    public final SecuredApiService securedApiService;

    @PostMapping(value = "/create")
    public String createTest(@RequestBody SubredditDto subRedditDto) {

        System.out.println("Here Secured Controller PostMapping"); // 1
        securedApiService.save(subRedditDto);
        return "Saved";
        
    }
    
    
    
}
