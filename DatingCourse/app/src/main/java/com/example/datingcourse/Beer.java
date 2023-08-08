package com.example.datingcourse;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

public class Beer extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_beer);

        getSupportActionBar().setTitle("술!!");

    }
}