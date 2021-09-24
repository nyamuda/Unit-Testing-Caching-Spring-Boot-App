package com.blogposts.blogposts.Services;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import com.blogposts.blogposts.Exceptions.ApiException;
import com.blogposts.blogposts.Models.BlogPost;
import com.blogposts.blogposts.Models.Post;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import static java.util.Comparator.comparing;

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
        // else extract the tags
        String[] allTags = tags.split(",");
        // default value for the sortBy query parameter in case none was provided
        String sortByField = "";
        if (sortBy == null) {
            sortByField = "id";
        }

        // NOW WE GET THE DATA AND DEAL WITH IT
        String url = "https://api.hatchways.io/assessment/blog/posts?tag=";

        // this is where we'll put all our combined posts.
        // we using a set to avoid adding duplicate objects
        Set<BlogPost> postsCombinedSet = new HashSet<>();

        // we make an API call for every tag in the allTags array
        for (int i = 0; i < allTags.length; i++) {
            // make the request
            // the result will be an object (parent object) with one key "posts"
            // the value of that key will be all the posts-->the posts objects
            // so first we need to bind the parent object to the Post model
            Post result = restTemplate.getForObject(url + allTags[i], Post.class);

            // then we use the Post model's getPosts method to get the actual posts(objects)
            List<BlogPost> allPosts = result.getPosts();

            // we then add all the objects to the "postsCombinedSet" set
            postsCombinedSet.addAll(allPosts);

        }

        // we convert our set to an List so that we can use the sort method to sort our
        // posts.
        List<BlogPost> postsCombinedList = new ArrayList<>(postsCombinedSet);
        // if the direction value is "desc"
        // we sort the posts in descending order-->reversed()
        if (direction.equals("desc")) {
            
            // we now begin sorting based on the value sortByFiled
            switch (sortByField) {
                case "id":
                    postsCombinedList.sort(comparing(BlogPost::getId).reversed());
                    break;
                case "reads":
                    postsCombinedList.sort(comparing(BlogPost::getReads).reversed());
                    break;
                case "likes":
                    postsCombinedList.sort(comparing(BlogPost::getLikes).reversed());
                    break;
                case "popularity":
                    postsCombinedList.sort(comparing(BlogPost::getPopularity).reversed());
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
                    postsCombinedList.sort(comparing(BlogPost::getId));
                    break;
                case "reads":
                    postsCombinedList.sort(comparing(BlogPost::getReads));
                    break;
                case "likes":
                    postsCombinedList.sort(comparing(BlogPost::getLikes));
                    break;
                case "popularity":
                    postsCombinedList.sort(comparing(BlogPost::getPopularity));
                    break;
                // else if the sortBy value is not an accepted value
                default:
                    throw new ApiException("sortBy parameter is invalid");
            }

        }

        return postsCombinedList;

    }
}