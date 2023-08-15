package com.example.datingcourse;

public class Post {
    private String title;
    private String nickname;
    private String content;
    private int imageRes; // 이미지 리소스 ID
    private String date;

    public Post(String title, String nickname, String content, int imageRes, String date) {
        this.title = title;
        this.nickname = nickname;
        this.content = content;
        this.imageRes = imageRes;
        this.date = date;
    }

    public String getTitle() {
        return title;
    }

    public String getNickname() {
        return nickname;
    }

    public String getContent() {
        return content;
    }

    public int getImageRes() {
        return imageRes;
    }

    public String getDate() {
        return date;
    }
}

