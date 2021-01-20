package com.redditb.reddit.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

import java.util.List;

import com.netflix.ribbon.proxy.annotation.Http;
import com.redditb.reddit.dto.CommentRequest;
import com.redditb.reddit.dto.PostRequest;
import com.redditb.reddit.dto.PostResponse;
import com.redditb.reddit.dto.SubredditDto;
import com.redditb.reddit.dto.VoteRequest;
import com.redditb.reddit.model.Subreddit;
import com.redditb.reddit.model.UserTesting;
import com.redditb.reddit.service.CommentService;
import com.redditb.reddit.service.PostService;
import com.redditb.reddit.service.SecuredApiService;
import com.redditb.reddit.service.VoteService;

import org.apache.http.protocol.HTTP;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;


@RestController
@AllArgsConstructor
@RequestMapping("/api/securedController")
public class SecuredController {

    public final SecuredApiService securedApiService;
    public PostService postService;
    public CommentService commentService;
    public VoteService voteService;


    @PostMapping(value = "/subreddit/create")
    public String createTest(@RequestBody SubredditDto subRedditDto) {

        System.out.println("Here Secured Controller PostMapping"); // 1
        securedApiService.save(subRedditDto);
        return "Saved";
        
    }

    @GetMapping(value="/subreddit/getAll")
    public ResponseEntity<List<SubredditDto>> getAllSubreddits(){
        return ResponseEntity
            .status(HttpStatus.OK)
            .body(securedApiService.getAll());
    }

    @GetMapping(value = "/subreddit/getOne/{id}")
    public ResponseEntity<SubredditDto> getOneSubreddit(@PathVariable Long id){

        return ResponseEntity
                .status(HttpStatus.OK)
                .body(securedApiService.getById(id));

    }

    @PostMapping("/posts/create")
    public ResponseEntity<Void> saveOne(@RequestBody PostRequest postRequest){
        postService.save(postRequest);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }
    //Nothing to get all posts?
    @GetMapping("/posts/getall")
    public ResponseEntity<List<PostResponse>> getAll(){
        return ResponseEntity.status(HttpStatus.OK).body(postService.getAllPosts());
    }
    @PostMapping("/comment/create")
    public ResponseEntity<Void> saveOne(@RequestBody CommentRequest commentRequest){
        commentService.save(commentRequest);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @GetMapping("/comment/by-username/{username}")
    public ResponseEntity<List<CommentRequest>> findCommentByUsername(@PathVariable String username){
        return ResponseEntity
            .status(HttpStatus.OK)
            .body(commentService.getAllCommentsForUser(username));
    }
    @GetMapping("comment/by-post/{postId}")
    public ResponseEntity<List<CommentRequest>> getAllCommentsForPost(@PathVariable Long postId) {
        return ResponseEntity.status(HttpStatus.OK)
                .body(commentService.getAllCommentsForPost(postId));
    }

    @PostMapping("/vote")
    public ResponseEntity<Void> votePost(@RequestBody VoteRequest voteRequest){
        voteService.vote(voteRequest);
        return new ResponseEntity<>(HttpStatus.OK);
    }



    
    
    
}
