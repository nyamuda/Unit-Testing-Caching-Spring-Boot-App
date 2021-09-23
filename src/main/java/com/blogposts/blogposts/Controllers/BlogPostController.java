package com.blogposts.blogposts.Controllers;

import java.util.HashMap;

import com.blogposts.blogposts.Services.BlogPostService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
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

    // GET PING
    @GetMapping("api/ping")
    @ResponseBody
    public ResponseEntity<Object> getPing() {
        HashMap<String, Boolean> successMessage = new HashMap<>();
        successMessage.put("success", true);
        return new ResponseEntity<Object>(successMessage, HttpStatus.OK);
    }

    // GET POSTS
    @GetMapping("api/posts**")
    @ResponseBody
    public ResponseEntity<Object> getBlogPosts(@RequestParam(value = "tags", required = false) String tags,
            @RequestParam(value = "sortBy", required = false) String sortBy,
            @RequestParam(value = "direction", required = false) String direction) {
        return ResponseEntity.ok().body(postService.getPostsService(tags, sortBy, direction));
    }

}
