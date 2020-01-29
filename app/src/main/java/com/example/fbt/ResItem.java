package com.example.fbt;

import android.widget.RatingBar;

public class ResItem {
    private String restname;
    private String score;
    private RatingBar score_star;

    public String getRestname(){
        return restname;
    }

    public void setRestname(String restname){
        this.restname = restname;
    }

    public String getScore(){
        return score;
    }

    public void setScore(String score){
        this.score = score;
    }
}
