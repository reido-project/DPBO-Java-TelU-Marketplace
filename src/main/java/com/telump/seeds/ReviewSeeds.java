package com.telump.seeds;
import com.telump.review.Review;

public class ReviewSeeds extends Review {
    public ReviewSeeds(String reviewer, String targetUsername, String comment, int rating) {
        super(reviewer, targetUsername, comment, rating);
    }
}
