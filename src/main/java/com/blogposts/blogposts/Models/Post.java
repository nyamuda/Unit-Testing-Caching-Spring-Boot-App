package com.blogposts.blogposts.Models;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class Post {
    private List<BlogPost> posts = new ArrayList<>();

    public List<BlogPost> getPosts() {
        return posts;
    }

    public void setPosts(List<BlogPost> posts) {
        this.posts = posts;
    }

}
