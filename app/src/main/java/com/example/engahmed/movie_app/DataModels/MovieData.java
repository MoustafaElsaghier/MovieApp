package com.example.engahmed.movie_app.DataModels;

public class MovieData {
    String id;
    String imgUrl;
    String overView;
    String title;
    String voteAVG;
    String date;
    String voteCount;
    String backdrop;
    String selected = "0";

    public String isSelected() {
        return selected;
    }

    public void setSelected(String selected) {
        this.selected = selected;
    }

    public String getTitle() {
        return title;
    }

    public String getOverView() {
        return overView;
    }

    public String getVoteAVG() {
        return voteAVG;
    }

    public String getDate() {
        return date;
    }

    public String getVoteCount() {
        return voteCount;
    }

    public String getBackdrop() {
        return backdrop;
    }


    public String getId() {
        return id;
    }

    public MovieData(String imgUrl, String overView, String title, String voteAVG,
                     String date, String voteCount, String backdrop, String id) {
        this.imgUrl = imgUrl;
        this.overView = overView;
        this.title = title;
        this.voteAVG = voteAVG;
        this.date = date;
        this.voteCount = voteCount;
        this.backdrop = backdrop;
        this.id = id;
    }

    public String getImgUrl() {
        return imgUrl;
    }

}

