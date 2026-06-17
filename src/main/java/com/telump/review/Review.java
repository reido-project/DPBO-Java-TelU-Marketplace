package com.telump.review;

import java.time.LocalDateTime;

import com.telump.database.ReviewTable;

public class Review {
    private int id;
    private int rating;
    private String reviewer;
    private String targetUsername;
    private String comment;
    private LocalDateTime createdAt;

    public Review(String reviewer, String targetUsername, String comment, int rating) {
        this.reviewer = reviewer;
        this.targetUsername = targetUsername;
        this.comment = comment;
        this.rating = rating;
        this.createdAt = LocalDateTime.now();

        ReviewTable.create(this);
    }

    public int getId() { return id; }
    public int getRating() { return rating; }
    public String getTargetUsername() { return targetUsername; }
    public String getComment() { return comment; }
    public String getReviewer() { return reviewer; }
    public LocalDateTime getCreatedAt() { return createdAt; }

    public void setId(int id) { this.id = id; }
    public void setRating(int rating) { this.rating = rating; }
    public void setComment(String comment) { this.comment = comment; }
    public void setCreatedAt(LocalDateTime createdAt) { this.createdAt = createdAt; }
}