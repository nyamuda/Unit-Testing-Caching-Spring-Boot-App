package com.blogposts.blogposts.Controllers;

import com.blogposts.blogposts.Services.BlogPostService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class BlogPostController {
    @Autowired
    private BlogPostService postService;

    public BlogPostController(BlogPostService postService) {
        this.postService = postService;
    }

    @GetMapping("api/posts/**")
    @ResponseBody
    public ResponseEntity<Object> getBlogPosts(@RequestParam(value = "tags", required = false) String tags,
            @RequestParam(value = "sortBy", required = false) String sortBy,
            @RequestParam(value = "direction", required = false) String direction) {
        return ResponseEntity.ok().body(postService.getPostsService(tags, sortBy, direction));
    }

}
