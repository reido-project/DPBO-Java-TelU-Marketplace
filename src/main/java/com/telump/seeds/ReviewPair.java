package com.telump.seeds;

public class ReviewPair {

    private final String reviewer;
    private final String target;

    public ReviewPair(String reviewer, String target) {
        this.reviewer = reviewer;
        this.target = target;
    }

    public String getReviewer() {
        return reviewer;
    }

    public String getTarget() {
        return target;
    }
}