package com.redditb.reddit.service;

import java.util.List;

import com.redditb.reddit.dto.PostRequest;
import com.redditb.reddit.dto.PostResponse;
import com.redditb.reddit.exceptions.SpringRedditException;
import com.redditb.reddit.mapper.PostMapper;
import com.redditb.reddit.model.Post;
import com.redditb.reddit.model.Subreddit;
import com.redditb.reddit.model.User;
import com.redditb.reddit.repository.PostRepository;
import com.redditb.reddit.repository.SubredditRepository;
import com.redditb.reddit.repository.UserRepository;

import org.springframework.stereotype.Service;

import lombok.AllArgsConstructor;
import static java.util.stream.Collectors.toList;

@Service
@AllArgsConstructor
public class PostService {
    
    // @Autowired
    private SubredditRepository subredditrepository;
    private AuthService authservice;
    private PostMapper postMapper;
    private PostRepository postRepository;
    private UserRepository userRepository;

    public void save(PostRequest postRequest){
       Subreddit subreddit = subredditrepository.findByName(postRequest.getSubredditName())
                                .orElseThrow(()-> new SpringRedditException("No subreddit found with this name"+postRequest.getSubredditName()));
    
        postRepository.save(postMapper.map(postRequest, subreddit, authservice.getCurrentUser()));
    }

    public PostResponse getPost(Long id) {
        Post post = postRepository.findById(id)
                .orElseThrow(() -> new SpringRedditException(id.toString()));
        return postMapper.maptoDto(post);
    }

    
    public List<PostResponse> getAllPosts() {
        return postRepository.findAll()
                .stream()
                .map(postMapper::maptoDto)
                .collect(toList());
    }

   
    public List<PostResponse> getPostsBySubreddit(Long subredditId) {
        Subreddit subreddit = subredditrepository.findById(subredditId)
                .orElseThrow(() -> new SpringRedditException(subredditId.toString()));
        List<Post> posts = postRepository.findAllBySubreddit(subreddit);
        return posts.stream().map(postMapper::maptoDto).collect(toList());
    }

    
    public List<PostResponse> getPostsByUsername(String username) {
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new SpringRedditException(username));
        return postRepository.findByUser(user)
                .stream()
                .map(postMapper::maptoDto)
                .collect(toList());
    }

    
}
