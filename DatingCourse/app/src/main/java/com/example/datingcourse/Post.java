package com.example.datingcourse;

public class Post {
    private String title;   // 제목
    private String nickname;    // 닉네임
    private String content; // 내용
    private int imageRes; // 이미지 리소스 ID
    private String date;    // 시간
    private String picture; // 유저 사진

    public Post(String title, String nickname, String content, int imageRes, String date, String picture) {
        this.title = title;
        this.nickname = nickname;
        this.content = content;
        this.imageRes = imageRes;
        this.date = date;
        this.picture = picture;
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

    public String getPicture() {
        return picture;
    }
}

