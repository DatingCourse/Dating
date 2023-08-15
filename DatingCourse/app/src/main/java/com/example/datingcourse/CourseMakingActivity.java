// 코스 만들기 클래스
package com.example.datingcourse;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class CourseMakingActivity extends AppCompatActivity {

    private MyApp myApp;
    private ArrayList<Photo> photos;
    private PhotoAdapter photoAdapter;

    private ArrayList<HashMap<String, Object>> photosList = new ArrayList<>(); // 여러 장소 정보를 저장할 ArrayList
    private FirebaseAuth mFirebaseAuth;
    private FirebaseUser mFirebaseUser;

    private DatabaseReference mLoadDatabaseRef;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_course_making);

        // MyApp 객체 가져오기
        myApp = (MyApp) getApplicationContext();
        photos = myApp.getPhotos();

        getSupportActionBar().setTitle("코스 만들기");

        if (photos == null) {
            photos = new ArrayList<>(); // Initialize photos if it's null
            myApp.setPhotos(photos);
        }
        mFirebaseAuth = FirebaseAuth.getInstance();
        mFirebaseUser = mFirebaseAuth.getCurrentUser();

        //파이어베이스 인증 및 데이터베이스 초기화등
//        mFirebaseAuth = FirebaseAuth.getInstance();
//        mDatabaseRef = FirebaseDatabase.getInstance().getReference("FirebaseRegister");

        Intent intent = getIntent();
        String imgUrl = intent.getStringExtra("imgUrl");
        String titleName = intent.getStringExtra("titleName");
        String addressName = intent.getStringExtra("addressName");
        Double x = intent.getDoubleExtra("x", 0.0); // 기본값 0.0
        Double y = intent.getDoubleExtra("y", 0.0); // 기본값 0.0
        String tel = intent.getStringExtra("tel");
        String userUid = mFirebaseUser.getUid().toString();

        // 값이 null이거나 빈 문자열인 경우 "정보 없음"으로 설정
        imgUrl = (imgUrl == null || imgUrl.isEmpty()) ? null : imgUrl;
        titleName = (titleName == null || titleName.isEmpty()) ? "정보 없음" : titleName;
        addressName = (addressName == null || addressName.isEmpty()) ? "정보 없음" : addressName;
        tel = (tel == null || tel.isEmpty()) ? "정보 없음" : tel;

        // 받아온 정보를 Photo 객체로 생성
        if (imgUrl != null && titleName != null && addressName != null && x != null && y != null && tel != null) {
            // 값들이 유효하면 Photo 객체 생성
            Photo photo = new Photo(imgUrl, titleName, addressName, x, y, tel,userUid);
            boolean isPhotoExist = false;
            for (Photo p : photos) {
                if (p.getImgUrl().equals(imgUrl) && p.getTitleName().equals(titleName)) {
                    isPhotoExist = true;
                    break;
                }
            }

            if (!isPhotoExist) {
                // 받아온 정보를 Photo 객체로 생성 및 추가
                photos.add(photo);
            }

            // 정보를 HashMap에 저장
            HashMap<String, Object> photoMap = new HashMap<>();
            photoMap.put("imageUrl", photo.getImgUrl());
            photoMap.put("placeName", photo.getTitleName());
            photoMap.put("address", photo.getAddressName());
            photoMap.put("x", photo.getX());
            photoMap.put("y", photo.getY());
            photoMap.put("tel", photo.getTel());
            photoMap.put("userUid",photo.getUserUid());

            // HashMap 객체를 ArrayList에 추가
            photosList.add(photoMap);
            myApp.setPhotos(photos);
            // 리사이클러 뷰
            RecyclerView recyclerView = findViewById(R.id.recyclerViewImg);

            if(myApp.getPhotos().isEmpty()) {
                recyclerView.setVisibility(View.GONE);
            } else {
                recyclerView.setVisibility(View.VISIBLE);
            }
            Log.d("Course_img", imgUrl);
            // 리사이클러 뷰 어댑터 초기화
            photoAdapter = new PhotoAdapter(this, myApp.getPhotos());
            recyclerView.setAdapter(photoAdapter);
            photoAdapter.notifyDataSetChanged();
            LinearLayoutManager horizontalLayoutManager = new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false);
            recyclerView.setLayoutManager(horizontalLayoutManager);
        }

        // 코스 생성 버튼
        Button create_btn = findViewById(R.id.course_create_btns);
        create_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                loadPhotosFromGlobal();
                Intent intent = new Intent(getApplicationContext(), BoardActivity.class);   // RecommendActivity
                startActivity(intent);
            }
        });

        Button search_address = (Button) findViewById(R.id.search_address);
        search_address.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d("TAG","버튼 눌림");
                Intent intent1 = new Intent(getApplicationContext(), MapChoiceActivity.class);
                startActivity(intent1);
            }
        });
    }

    private void loadPhotosFromGlobal() {
        int i = 0;
        for (int j =0; j<myApp.getPhotos().size();j++){
            Photo photo = myApp.getPhotos().get(j);

            HashMap<String,Object> saveCourse = new HashMap<String,Object>();

            saveCourse.put("imgUrl",photo.getImgUrl());
            saveCourse.put("titleName",photo.getTitleName());
            saveCourse.put("addressName",photo.getAddressName());
            saveCourse.put("x",photo.getX());
            saveCourse.put("y",photo.getY());
            saveCourse.put("tel",photo.getTel());
            saveCourse.put("userUid",photo.getUserUid());

            Log.d("CourseMakingActivity", "Photo Title: " + photo.getTitleName());
            mLoadDatabaseRef = FirebaseDatabase.getInstance().getReference("FirebaseRegister"); //getReference안에 " " 앱이름, 프로젝트 이름


            int finalI = i;
            mLoadDatabaseRef.child("UserCourse").addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot) {
                    int currentChild = (int) snapshot.getChildrenCount();

                    int nextIndex = currentChild + 1;
                    // 값을 새 노드에 저장합니다.
                    mLoadDatabaseRef.child("UserCourse")
                            .child(String.valueOf(nextIndex)) // 자식 노드의 이름을 1씩 증가시킴
                            .child((finalI + 1) + "번째 코스")
                            .setValue(saveCourse);
                }

                @Override
                public void onCancelled(@NonNull DatabaseError error) {

                }
            });
            i++;

        }
    }

}