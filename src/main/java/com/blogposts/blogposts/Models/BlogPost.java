package com.blogposts.blogposts.Models;

import java.util.ArrayList;
import java.util.List;

public class BlogPost {
    private int id;
    private String author;
    private int authorId;
    private int likes;
    private Double popularity;
    private int reads;
    private List<String> tags = new ArrayList<>();

    public BlogPost(int id, String author, int authorId, int likes, double d, int reads, List<String> tags) {
        this.id = id;
        this.author = author;
        this.authorId = authorId;
        this.likes = likes;
        this.popularity = d;
        this.reads = reads;
        this.tags = tags;
    }

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

    public Double getPopularity() {
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

    public void setPopularity(Double popularity) {
        this.popularity = popularity;
    }

    public void setReads(int reads) {
        this.reads = reads;
    }

    public void setTags(List<String> tags) {
        this.tags = tags;
    }

    @Override
    public String toString() {
        return super.toString();
    }
}
