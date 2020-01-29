package com.example.fbt;

public class MyItem {

    private String nickname;
    private String time;
    private String contents;

    public String getNickname(){
        return nickname;
    }

    public void setNickname(String nickname){
        this.nickname = nickname;
    }

    public String getTime(){
        return time;
    }

    public void setTime(String time){
        this.time = time;
    }

    public String getContents(){
        return contents;
    }

    public MyItem(String nickname, String time, String contents) {
        this.nickname = nickname;
        this.time = time;
        this.contents = contents;
    }

    public void setContents(String contents){
        this.contents = contents;
    }
}
