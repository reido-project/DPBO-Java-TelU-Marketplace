package com.telump.database;

import java.util.ArrayList;
import java.util.List;

import com.telump.review.Review;

public class ReviewTable implements IDatabase {
    private static final List<Review> reviewTable = new ArrayList<>();
    private static int nextId = 1;

    /**
     * Dapatkan semua review
     * @return List berisi semua review
     */
    public static List<Review> getAll() {
        return new ArrayList<>(reviewTable);
    }

    /**
     * Cari review berdasarkan ID
     * @param id ID review yang dicari
     * @return Review object jika ditemukan, null jika tidak ada
     */
    public static Review getById(int id) {
        for (Review review : reviewTable) {
            if (review.getId() == id) {
                return review;
            }
        }
        return null;
    }

    /**
     * Ambil semua review yang ditujukan ke target user
     * @param targetUsername username user yang menerima review
     * @return List berisi review untuk user tersebut
     */
    public static List<Review> getByTargetUsername(String targetUsername) {
        List<Review> result = new ArrayList<>();
        for (Review review : reviewTable) {
            if (review.getTargetUsername().equals(targetUsername)) {
                result.add(review);
            }
        }
        return result;
    }

    /**
     * Ambil semua review yang dibuat oleh reviewer tertentu
     * @param reviewer username reviewer
     * @return List berisi review dari reviewer tersebut
     */
    public static List<Review> getByReviewer(String reviewer) {
        List<Review> result = new ArrayList<>();
        for (Review review : reviewTable) {
            if (review.getReviewer().equals(reviewer)) {
                result.add(review);
            }
        }
        return result;
    }

    /**
     * Cari review dari reviewer tertentu ke target user tertentu
     * @param reviewer username reviewer
     * @param targetUsername username target user
     * @return Review jika ada, null jika tidak ada
     */
    public static Review findReviewByReviewerAndTarget(String reviewer, String targetUsername) {
        for (Review review : reviewTable) {
            if (review.getReviewer().equals(reviewer) && review.getTargetUsername().equals(targetUsername)) {
                return review;
            }
        }
        return null;
    }

    /**
     * Simpan review baru ke database
     * @param review review yang akan disimpan
     * @return true jika berhasil, false jika review null
     */
    public static String create(Review review) {
        if (review == null) {
            return null;
        }

        review.setId(nextId++);
        reviewTable.add(review);
        return "Review created successfully with ID: " + review.getId();
    }

    /**
     * Update review yang sudah ada
     * @param review review yang akan diupdate
     * @return pesan status
     */
    public static String update(Review review) {
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

    /**
     * Hapus review dari database
     * @param review review yang akan dihapus
     * @return pesan status
     */
    public static String delete(Review review) {
        reviewTable.removeIf(r -> r.getId() == review.getId());
        return "Review deleted successfully.";
    }

    /**
     * Hitung rating rata-rata user
     * @param targetUsername username user yang akan dihitung rating-nya
     * @return rating rata-rata (0 jika belum ada review)
     */
    public static double getAverageRating(String targetUsername) {
        List<Review> reviews = getByTargetUsername(targetUsername);
        
        if (reviews.isEmpty()) {
            return 0;
        }

        int totalRating = 0;
        for (Review review : reviews) {
            totalRating += review.getRating();
        }

        return (double) totalRating / reviews.size();
    }
}