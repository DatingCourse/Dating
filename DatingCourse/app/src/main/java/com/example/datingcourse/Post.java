package com.example.datingcourse;

import android.net.Uri;

import com.google.firebase.Timestamp;

import java.util.List;

public class Post {
    private String title;   // 제목
    private String NickName;    // 닉네임
    private String content; // 내용
    private List<String> imageUrls; // 이미지 리소스 ID
    private Timestamp when;    // 시간
    private Uri picture; // 유저 사진
    private String userId;  // 유저 아이디

    private String documentId;   // 게시물 아이디

    public Post() {
    }

    public Post(String title, String NickName, String content, List<String> imageUrls, Timestamp when, Uri picture, String userId) {
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

    public String getNickName() {
        return NickName;
    }

    public void setNickName(String nickName) {
        NickName = nickName;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public List<String> getImageUrls() {
        return imageUrls;
    }

    public void setImageUrls(List<String> imageUrls) {
        this.imageUrls = imageUrls;
    }

    public Timestamp getWhen() {
        return when;
    }

    public void setWhen(Timestamp when) {
        this.when = when;
    }

    public Uri getPicture() {
        return picture;
    }

    public void setPicture(Uri picture) {
        this.picture = picture;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getDocumentId() {
        return documentId;
    }

    public void setDocumentId(String documentId) {
        this.documentId = documentId;
    }
}

