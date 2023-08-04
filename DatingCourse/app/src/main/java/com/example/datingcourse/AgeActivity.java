// 연령별 코스 클래스
package com.example.datingcourse;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

public class AgeActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_age_ativity);

        getSupportActionBar().setTitle("연령별 코스");

    }
}