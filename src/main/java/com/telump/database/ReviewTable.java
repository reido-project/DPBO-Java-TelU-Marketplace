package com.telump.database;

import com.telump.review.Review;

import java.util.ArrayList;
import java.util.List;

public class ReviewTable implements IDatabase {
    private static final List<Review> reviewTable = new ArrayList<>();
    private static int nextId = 1;

    public List<Review> getAll() {
        return new ArrayList<>(reviewTable);
    }

    public Review getById(int id) {
        for (Review review : reviewTable) {
            if (review.getId() == id) {
                return review;
            }
        }
        return null;
    }

    public List<Review> getByTargetUsername(String targetUsername) {
        List<Review> result = new ArrayList<>();
        for (Review review : reviewTable) {
            if (review.getTargetUsername().equals(targetUsername)) {
                result.add(review);
            }
        }
        return result;
    }

    public List<Review> getByReviewer(String reviewer) {
        List<Review> result = new ArrayList<>();
        for (Review review : reviewTable) {
            if (review.getReviewer().equals(reviewer)) {
                result.add(review);
            }
        }
        return result;
    }

    public static String create(Review review) {
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