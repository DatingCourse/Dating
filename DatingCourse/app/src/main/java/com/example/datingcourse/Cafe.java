package com.example.datingcourse;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

public class Cafe extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cafe);

        getSupportActionBar().setTitle("카페");
    }
}