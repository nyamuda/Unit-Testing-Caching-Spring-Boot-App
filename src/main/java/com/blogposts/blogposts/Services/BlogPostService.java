package com.blogposts.blogposts.Services;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import com.blogposts.blogposts.Exceptions.ApiException;
import com.blogposts.blogposts.Models.BlogPost;
import com.blogposts.blogposts.Models.Post;
import com.fasterxml.jackson.databind.ObjectMapper;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class BlogPostService {
    @Autowired
    private RestTemplate restTemplate;

    public BlogPostService(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    public List<BlogPost> getPostsService(String tags, String sortBy, String direction) {
        // if the tags query parameter is not there--->throw an exception
        // if (tags == null) {
        // throw new ApiException("Tags parameter is required");
        // }
        // if (sortBy == null) {
        // sortBy = "id";
        // }
        // if (direction == null) {
        // direction = "asc";
        // }
        // NOW WE GET THE DATA AND DEAL WITH IT""\
        String url = "https://api.hatchways.io/assessment/blog/posts?tag=";
        String[] alltags = { "tech", "health" };

        List<BlogPost> allData = new ArrayList<>();

        for (int i = 0; i < alltags.length; i++) {
            // make the request
            Post result = restTemplate.getForObject(url + alltags[i], Post.class);

            List<BlogPost> allPosts = result.getPosts();

            allPosts.forEach(val -> {
                if (!allData.contains(val)) {
                    allData.add(val);
                }
            });

        }

        return allData;
        // List<Object> alll = Arrays.asList(result);

        // return alll;

    }
}