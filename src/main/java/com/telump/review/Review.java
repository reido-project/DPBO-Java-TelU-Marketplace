package com.telump.review;

import java.time.LocalDateTime;

public class Review {
    private int id;
    private int itemId;
    private int userId;
    private String comment;
    private int rating;
    private LocalDateTime createdAt;

    public Review(int itemId, int userId, String comment, int rating) {
        this.itemId = itemId;
        this.userId = userId;
        this.comment = comment;
        this.rating = rating;
        this.createdAt = LocalDateTime.now();
    }

    public int getId() { return id; }
    public int getItemId() { return itemId; }
    public int getUserId() { return userId; }
    public String getComment() { return comment; }
    public int getRating() { return rating; }
    public LocalDateTime getCreatedAt() { return createdAt; }
    
    public void setId(int id) { this.id = id; }
}