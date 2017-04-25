package com.example.engahmed.movie_app.DataModels;


public class reviewData {
    String rev_name , rev_word;

    public String getRev_name() {
        return rev_name;
    }

    public String getRev_word() {
        return rev_word;
    }

    public reviewData(String rev_name, String rev_word) {
        this.rev_name = rev_name;
        this.rev_word = rev_word;
    }
}