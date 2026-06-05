package com.telump.database;

import com.telump.review.Review;

import java.util.ArrayList;
import java.util.List;

public class ReviewTable implements IDatabase {
    private static final List<Review> reviewTable = new ArrayList<>();
    private static int nextId = 1;

    public static List<Review> getReviewsByItem(int itemId) {
        List<Review> result = new ArrayList<>();
        for (Review r : reviewTable) {
            if (r.getItemId() == itemId) {
                result.add(r);
            }
        }
        return result;
    }

    public static double getAverageRating(int itemId) {
        List<Review> reviews = getReviewsByItem(itemId);
        if (reviews.isEmpty()) {
            return 0.0;
        }

        int totalRating = 0;
        for (Review r : reviews) {
            totalRating += r.getRating();
        }
        return (double) totalRating / reviews.size();
    }

    public String create(Review review) {
        if (review == null) {
            return null;
        }

        review.setId(nextId++);
        reviewTable.add(review);
        return "Review created successfully with ID: " + review.getId();
    }

    public String update(Review review) {
        if (review == null) {
            return null;
        }

        for (int i = 0; i < reviewTable.size(); i++) {
            if (reviewTable.get(i).getId() == review.getId()) {
                reviewTable.set(i, review);
                return "Review updated successfully.";
            }
        }
        return "Error: Review with ID " + review.getId() + " not found.";
    }

    public String delete(Review review) {
        reviewTable.removeIf(r -> r.getId() == review.getId());
        return "Review deleted successfully.";
    }
}