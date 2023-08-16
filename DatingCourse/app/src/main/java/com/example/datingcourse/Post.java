package com.example.datingcourse;

import com.google.firebase.Timestamp;

public class Post {
    private String title;   // 제목
    private String NickName;    // 닉네임
    private String content; // 내용
    private String[] imageUrls; // 이미지 리소스 ID
    private Timestamp when;    // 시간
    private String picture; // 유저 사진
    private String userId;  // 게시물 이름

    public Post() {
    }

    public Post(String title, String NickName, String content, String[] imageUrls, Timestamp when, String picture, String userId) {
        this.title = title;
        this.NickName = NickName;
        this.content = content;
        this.imageUrls = imageUrls;
        this.when = when;
        this.picture = picture;
        this.userId = userId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getNickname() {
        return NickName;
    }

    public void setNickname(String NickName) {
        this.NickName = NickName;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String[] getImageRes() {
        return imageUrls;
    }

    public void setImageRes(String[] imageUrls) {
        this.imageUrls = imageUrls;
    }

    public Timestamp getDate() {
        return when;
    }

    public void setDate(Timestamp when) {
        this.when = when;
    }

    public String getPicture() {
        return picture;
    }

    public void setPicture(String picture) {
        this.picture = picture;
    }

    public String getDocumentId() {
        return userId;
    }

    public void setDocumentId(String userId) {
        this.userId = userId;
    }
}

