package com.telump.seeds;

public class ReviewSeeds {
    private String comment;
    private int rating;
    public ReviewSeeds(String comment, int rating) {
        this.comment = comment;
        this.rating = rating;
    }

    public String getComment(){
        return comment;
    }

    public int getRating(){
        return rating;
    }
}
