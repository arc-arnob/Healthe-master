package com.redditb.reddit.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.lang.Nullable;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import java.time.Instant;

import static javax.persistence.FetchType.LAZY;
import static javax.persistence.GenerationType.IDENTITY;

@Data
@Entity
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Post {
    @Id
    @GeneratedValue(strategy = IDENTITY)
    private Long postId;
    @NotBlank(message = "Post Name cannot be empty or Null")
    private String postName;
    @Nullable
    private String url;
    @Nullable
    @Lob
    private String description;
    private Integer voteCount = 0;
    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "username", referencedColumnName = "username")
    private User user;
    private Instant createdDate;
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "id", referencedColumnName = "id")
    private Subreddit subreddit;
}
