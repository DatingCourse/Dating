package com.example.datingcourse;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

public class Random extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_random);

        getSupportActionBar().setTitle("랜덤");
    }
}