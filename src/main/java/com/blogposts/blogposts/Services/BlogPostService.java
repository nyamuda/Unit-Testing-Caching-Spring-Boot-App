package com.blogposts.blogposts.Services;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
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
import static java.util.Comparator.comparing;
import static java.util.Comparator.reverseOrder;

@Service
public class BlogPostService {
    @Autowired
    private RestTemplate restTemplate;

    public BlogPostService(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    public List<BlogPost> getPostsService(String tags, String sortBy, String direction) {
        // if the tags query parameter is not there--->throw an exception
        if (tags == null) {
            throw new ApiException("Tags parameter is required");
        }
        // default value for the sortBy query parameter in case none was provided
        String sortByField = "";
        if (sortBy == null) {
            sortByField = "id";
        }
        // default value for the direction query parameter in case none was provided
        String directionField = "";
        if (direction == null) {
            directionField = "desc";
        }

        // NOW WE GET THE DATA AND DEAL WITH IT
        String url = "https://api.hatchways.io/assessment/blog/posts?tag=";
        String[] alltags = { "tech", "health" };

        List<BlogPost> allPostsCombined = new ArrayList<>();

        for (int i = 0; i < alltags.length; i++) {
            // make the request
            // the result will be an object (parent object) with one key "posts"
            // the value of that key will be all the posts-->the posts objects
            // so first we need to bind the parent to the Post model
            Post result = restTemplate.getForObject(url + alltags[i], Post.class);

            // then we use the Post model getPosts methods to get the actual posts(objects)
            List<BlogPost> allPosts = result.getPosts();

            // we loop through the posts and add them to the allPostsCombined List
            // if an object already exists in allPostsCombined, we don't add it
            allPosts.forEach(val -> {
                if (!allPostsCombined.contains(val)) {
                    allPostsCombined.add(val);
                }
            });

        }
        // if the direction value is "desc"
        // we sort the posts in descending order
        if (directionField.equals("desc")) {
            // we now begin sorting based on the value sortByFiled
            switch (sortByField) {
                case "id":
                    allPostsCombined.sort(comparing(BlogPost::getId).reversed());
                    break;
                case "reads":
                    allPostsCombined.sort(comparing(BlogPost::getReads).reversed());
                    break;
                case "likes":
                    allPostsCombined.sort(comparing(BlogPost::getLikes).reversed());
                    break;
                case "popularity":
                    allPostsCombined.sort(comparing(BlogPost::getPopularity).reversed());
                    break;
                // else if the sortBy value is not an accepted value
                default:
                    throw new ApiException("sortBy parameter is invalid");
            }

        }

        // else we sort in the default order--->asc
        else {
            // we now begin sorting based on the value sortByFiled
            switch (sortByField) {
                case "id":
                    allPostsCombined.sort(comparing(BlogPost::getId));
                    break;
                case "reads":
                    allPostsCombined.sort(comparing(BlogPost::getReads));
                    break;
                case "likes":
                    allPostsCombined.sort(comparing(BlogPost::getLikes));
                    break;
                case "popularity":
                    allPostsCombined.sort(comparing(BlogPost::getPopularity));
                    break;
                // else if the sortBy value is not an accepted value
                default:
                    throw new ApiException("sortBy parameter is invalid");
            }

        }

        // then we choose the direction
        // the default is asc-->is also the default of the sort method used above
        // meaning when we sort the posts they will already be in ascending order

        return allPostsCombined;

    }
}