// 지도 선택 클래스
package com.example.datingcourse;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

public class MapChoiceActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_map_choice);

        getSupportActionBar().setTitle("장소 검색");
    }
}