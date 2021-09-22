package com.blogposts.blogposts.Controllers;

import com.blogposts.blogposts.Services.BlogPostsService;

import org.springframework.web.bind.annotation.RestController;

@RestController
public class BlogPostsController {
    private BlogPostsService postService;

    public BlogPostsController(BlogPostsService postService) {
        this.postService = postService;
    }

    

}
