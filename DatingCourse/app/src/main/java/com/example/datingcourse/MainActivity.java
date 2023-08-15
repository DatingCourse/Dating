package com.example.datingcourse;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    private long backPressedTime = 0;    // 뒤로가기 버튼을 누른 시간
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        getSupportActionBar().setTitle("메인 화면");

        // 카페 페이지로 이동
        ImageButton imageButton1 = (ImageButton) findViewById(R.id.imageButton1);
        imageButton1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int sigunguCode = 1;
                int contentTypeId = 39;
                String cat1 = "A05";
                String cat2 = "A0502";
                String cat3 = "A05020900";
                Intent intent = new Intent(getApplicationContext(), PlaceList.class);    // Cafe
                intent.putExtra("contentTypeId", contentTypeId);
                intent.putExtra("sigunguCode", sigunguCode);
                intent.putExtra("cat1", cat1);
                intent.putExtra("cat2", cat2);
                intent.putExtra("cat3", cat3);
                intent.putExtra("selectedRegion", "서울특별시 강남구");
                startActivity(intent);
            }
        });

        // 식당 페이지로 이동
        ImageButton imageButton2 = (ImageButton) findViewById(R.id.imageButton2);
        imageButton2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int sigunguCode = 1;
                int contentTypeId = 39;
                String cat1 = "";
                String cat2 = "";
                String cat3 = "";
                Intent intent = new Intent(getApplicationContext(), PlaceList.class);    // Cafe
                intent.putExtra("contentTypeId", contentTypeId);
                intent.putExtra("sigunguCode", sigunguCode);
                intent.putExtra("cat1", cat1);
                intent.putExtra("cat2", cat2);
                intent.putExtra("cat3", cat3);
                intent.putExtra("selectedRegion", "서울특별시 강남구");
                startActivity(intent);
            }
        });

        // 숙박 페이지로 이동
        ImageButton imageButton3 = (ImageButton) findViewById(R.id.imageButton3);
        imageButton3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int sigunguCode = 1;
                int contentTypeId = 32;
                String cat1 = "";
                String cat2 = "";
                String cat3 = "";
                Intent intent = new Intent(getApplicationContext(), PlaceList.class);    // Cafe
                intent.putExtra("contentTypeId", contentTypeId);
                intent.putExtra("sigunguCode", sigunguCode);
                intent.putExtra("cat1", cat1);
                intent.putExtra("cat2", cat2);
                intent.putExtra("cat3", cat3);
                intent.putExtra("selectedRegion", "서울특별시 강남구");
                startActivity(intent);
            }
        });

        // 코스 만들기 페이지로 이동
        ImageButton imageButton4 = (ImageButton) findViewById(R.id.imageButton4);
        imageButton4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), CourseMakingActivity.class);
//                intent.putExtra("selectedNum", selectedNum);  // 구 정보 추가
                startActivity(intent);
            }
        });

        // 코스 추천 페이지로 이동
        ImageButton imageButton5 = (ImageButton) findViewById(R.id.imageButton5);
        imageButton5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), BoardActivity.class);   // RecommendActivity
//                intent.putExtra("selectedNum", selectedNum);  // 구 정보 추가
                startActivity(intent);
            }
        });

        // 랜덤 코스 페이지로 이동
        ImageButton imageButton6 = (ImageButton) findViewById(R.id.imageButton6);
        imageButton6.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), RandomActivity.class);  // Random
//                intent.putExtra("selectedNum", selectedNum);  // 구 정보 추가
                startActivity(intent);
            }
        });
    }
    @Override
    public void onBackPressed() {
        // 현재 시간을 가져온다.
        long tempTime = System.currentTimeMillis();
        // 마지막으로 뒤로가기 버튼을 누른 시간과 현재 시간을 비교한다.
        long intervalTime = tempTime - backPressedTime;

        // 뒤로가기 버튼을 한 번 눌렀을 때
        if (0 <= intervalTime && intervalTime <= 2000) {
            // 뒤로가기 버튼을 연속으로 두 번 눌렀을 때, 앱 종료
            super.onBackPressed();
            finishAffinity();  // 액티비티 스택을 모두 종료
            System.runFinalization();
            System.exit(0);
        } else {
            // 마지막으로 뒤로가기 버튼을 누른 시간을 현재 시간으로 갱신
            backPressedTime = tempTime;
            // 앱을 종료하려면 한 번 더 누르라는 토스트 메시지 표시
            showToast("앱을 종료하려면 한 번 더 누르세요.");
        }
    }

    // 토스트 메시지를 출력하는 메소드
    public void showToast(String message) {
        Toast.makeText(MainActivity.this, message, Toast.LENGTH_SHORT).show();
    }
}