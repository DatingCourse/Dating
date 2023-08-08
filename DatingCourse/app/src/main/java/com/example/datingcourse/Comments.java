package com.example.datingcourse;

import com.google.firebase.Timestamp;

public class Comments {
    private String userNick, context;

    private Timestamp when;

    public Comments(){
    }

    public Comments(String userNick, String context, Timestamp when) {
        this.userNick = userNick;
        this.context = context;
        this.when = when;
    }

    public String getUserNick() {
        return userNick;
    }

    public void setUserNick(String userNick) {
        this.userNick = userNick;
    }

    public String getContext() {
        return context;
    }

    public void setContext(String context) {
        this.context = context;
    }

    public com.google.firebase.Timestamp getWhen() {
        return when;
    }

    public void setWhen(com.google.firebase.Timestamp when) {
        this.when = when;
    }
}
