package com.example.datingcourse;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

public class Beer extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_beer);

        getSupportActionBar().setTitle("술!!");

    }
}