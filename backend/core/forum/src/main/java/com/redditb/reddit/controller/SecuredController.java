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
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;


@RestController
@AllArgsConstructor
@RequestMapping("/forum")
public class SecuredController {

    public final SecuredApiService securedApiService;
    public PostService postService;
    public CommentService commentService;
    public VoteService voteService;

    //forum/subreddit/create
    @PostMapping(value = "/subreddit/create")
    public String createTest(@RequestBody SubredditDto subRedditDto) {

        System.out.println("Here Secured Controller PostMapping"); // 1
        securedApiService.save(subRedditDto);
        return "Saved";
        
    }
    //forum/subreddit/getAll -- D
    @GetMapping(value="/subreddit/getAll") //get all threads that I am following
    public ResponseEntity<List<SubredditDto>> getAllSubreddits(){
        return ResponseEntity
            .status(HttpStatus.OK)
            .body(securedApiService.getAll());
    }
    //forum/subreddit/getOne/{id} -- D
    @GetMapping(value = "/subreddit/getOne/{id}") 
    public ResponseEntity<SubredditDto> getOneSubreddit(@PathVariable Long id){

        return ResponseEntity
                .status(HttpStatus.OK)
                .body(securedApiService.getById(id));

    }
    //forum/posts/create -- D
    @PostMapping("/posts/create")
    public String saveOne(@RequestBody PostRequest postRequest){
        String status = postService.save(postRequest);
        return status;
    }
    //forum/posts/getallByUsername -- D
    @GetMapping("/posts/getallByUsername") //by username
    public ResponseEntity<List<PostResponse>> getAllByUsername(){
        return ResponseEntity.status(HttpStatus.OK).body(postService.getPostsByUsername());
    }

    // get all posts by subreditid 
    //uses: 1. when all followed subredits are listed 
    //      2. when all posts by a user is listed
    //forum/posts/getallBySId/{subredditId} -- D
    @GetMapping("/posts/getallBySId/{subredditId}") //this will expand the thread
    public ResponseEntity<List<PostResponse>> getAllPostsBySubredditId(@PathVariable Long subredditId){
        return ResponseEntity.status(HttpStatus.OK).body(postService.getPostsBySubreddit(subredditId));
    }


    // forum/comment/create -- D
    @PostMapping("/comment/create")
    public ResponseEntity<String> saveOne(@RequestBody CommentRequest commentRequest){
        return ResponseEntity.status(HttpStatus.OK).body(commentService.save(commentRequest));
    } 
    
    
    //Delete comment
    @DeleteMapping("/comment/delete/{commentId}")
    public ResponseEntity<String> saveOne(@PathVariable Long commentId){
        return ResponseEntity.status(HttpStatus.OK).body(commentService.deleteCommentById(commentId));
    } 

    // forum/comment/by-username/ -- D
    @GetMapping("/comment/by-username")
    public ResponseEntity<List<CommentRequest>> findCommentByUsername(){
        return ResponseEntity
            .status(HttpStatus.OK)
            .body(commentService.getAllCommentsForUser());
    }
    // forum/comment/by-post/{postId} -- D
    @GetMapping("/comment/by-post/{postId}")
    public ResponseEntity<List<CommentRequest>> getAllCommentsForPost(@PathVariable Long postId) {
        return ResponseEntity.status(HttpStatus.OK)
                .body(commentService.getAllCommentsForPost(postId));
    }
    // forum/vote -- D
    @PostMapping("/vote")
    public ResponseEntity<Void> votePost(@RequestBody VoteRequest voteRequest){
        voteService.vote(voteRequest);
        return new ResponseEntity<>(HttpStatus.OK);
    }



    
    
    
}
