package com.blogposts.blogposts.Models;

import java.util.ArrayList;
import java.util.List;

public class BlogPostsModel {
    private int id;
    private String author;
    private int authorId;
    private int likes;
    private float popularity;
    private int reads;
    private List<String> tags = new ArrayList<>();

    public int getId() {
        return id;
    }

    public String getAuthor() {
        return author;
    }

    public int getAuthorId() {
        return authorId;
    }

    public int getLikes() {
        return likes;
    }

    public float getPopularity() {
        return popularity;
    }

    public int getReads() {
        return reads;
    }

    public List<String> getTags() {
        return tags;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public void setAuthorId(int authorId) {
        this.authorId = authorId;
    }

    public void setLikes(int likes) {
        this.likes = likes;
    }

    public void setPopularity(float popularity) {
        this.popularity = popularity;
    }

    public void setReads(int reads) {
        this.reads = reads;
    }

    public void setTags(List<String> tags) {
        this.tags = tags;
    }
}
