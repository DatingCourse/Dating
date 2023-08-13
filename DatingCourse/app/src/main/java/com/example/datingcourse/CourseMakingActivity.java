package com.example.datingcourse;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.HashMap;

public class CourseMakingActivity extends AppCompatActivity {

    private MyApp myApp;
    private ArrayList<Photo> photos;

    private PhotoAdapter photoAdapter;
    private ArrayList<HashMap<String, Object>> photosList = new ArrayList<>(); // 여러 장소 정보를 저장할 ArrayList

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_course_making);

        // MyApp 객체 가져오기
        myApp = (MyApp) getApplicationContext();
        photos = myApp.getPhotos();

        getSupportActionBar().setTitle("코스 만들기");

        Button search_address = (Button) findViewById(R.id.search_address);

        Intent intent = getIntent();
        String imgUrl = intent.getStringExtra("imgUrl");
        String titleName = intent.getStringExtra("titleName");
        String addressName = intent.getStringExtra("addressName");
        Double x = intent.getDoubleExtra("x", 0.0); // 기본값 0.0
        Double y = intent.getDoubleExtra("y", 0.0); // 기본값 0.0
        String tel = intent.getStringExtra("tel");

        // 받아온 정보를 Photo 객체로 생성
        Photo photo = new Photo(imgUrl, titleName, addressName, x, y, tel);

        // 정보를 HashMap에 저장
        HashMap<String, Object> photoMap = new HashMap<>();
        photoMap.put("imageUrl", photo.getImgUrl());
        photoMap.put("placeName", photo.getTitleName());
        photoMap.put("address", photo.getAddressName());
        photoMap.put("x", photo.getX());
        photoMap.put("y", photo.getY());
        photoMap.put("tel", photo.getTel());

        // HashMap 객체를 ArrayList에 추가
        photosList.add(photoMap);

        // 리사이클러 뷰
        RecyclerView recyclerView = findViewById(R.id.recyclerViewImg);
        // 리사이클러 뷰 어댑터 초기화
        photoAdapter = new PhotoAdapter(this, photos);
        recyclerView.setAdapter(photoAdapter);
        LinearLayoutManager horizontalLayoutManager = new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false);
        recyclerView.setLayoutManager(horizontalLayoutManager);

        // 코스 생성 버튼
        Button create_btn = findViewById(R.id.course_create_btns);
        create_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                loadPhotosFromGlobal();
            }
        });

        search_address.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intents = new Intent(getApplicationContext(), MapChoiceActivity.class);
                startActivity(intents);
            }
        });
    }

    private void loadPhotosFromGlobal() {
        for (Photo photo : photos) {
            // TODO: 불러온 사진 정보를 여기서 처리합니다.
            Log.d("CourseMakingActivity", "Photo Title: " + photo.getTitleName());
        }
    }
}
