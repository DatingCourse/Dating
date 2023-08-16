package com.example.datingcourse;

import com.google.firebase.Timestamp;

public class Post {
    private String title;   // 제목
    private String nickname;    // 닉네임
    private String content; // 내용
    private String[] imageRes; // 이미지 리소스 ID
    private Timestamp date;    // 시간
    private String picture; // 유저 사진
    private String documentId;  // 게시물 이름

    public Post(String title, String nickname, String content, String[] imageRes, Timestamp date, String picture, String documentId) {
        this.title = title;
        this.nickname = nickname;
        this.content = content;
        this.imageRes = imageRes;
        this.date = date;
        this.picture = picture;
        this.documentId = documentId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String[] getImageRes() {
        return imageRes;
    }

    public void setImageRes(String[] imageRes) {
        this.imageRes = imageRes;
    }

    public Timestamp getDate() {
        return date;
    }

    public void setDate(Timestamp date) {
        this.date = date;
    }

    public String getPicture() {
        return picture;
    }

    public void setPicture(String picture) {
        this.picture = picture;
    }

    public String getDocumentId() {
        return documentId;
    }

    public void setDocumentId(String documentId) {
        this.documentId = documentId;
    }
}

