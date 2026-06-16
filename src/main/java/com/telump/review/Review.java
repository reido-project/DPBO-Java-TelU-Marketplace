package com.telump.review;

import com.telump.database.ReviewTable;
import java.time.LocalDateTime;

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
}