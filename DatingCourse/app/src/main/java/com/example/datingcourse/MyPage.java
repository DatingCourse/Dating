package com.example.datingcourse;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

public class MyPage extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_page);

        getSupportActionBar().setTitle("마이 페이지");
    }
}