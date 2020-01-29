package com.example.fbt;

import android.os.Bundle;

import com.google.firebase.database.IgnoreExtraProperties;
@IgnoreExtraProperties
public class Rating {
    // 내용 시간 맛 가성비
    String 내용;
    String 시간;
    int 맛;
    int 가성비;

    public Rating() {
        //no instance
    }
    public Rating(String content, String date, int taste, int price){
        this.내용 = content;
        this.시간 = date;
        this.맛 = taste;
        this.가성비 = price;
    }

}
