package com.example.datingcourse;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

public class Cafe extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cafe);

        getSupportActionBar().setTitle("카페");
    }
}