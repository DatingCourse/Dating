package com.example.datingcourse;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class BoardActivity extends AppCompatActivity {
    private ArrayList<ArrayList<Points>> allBoardList = new ArrayList();

    private String img; // 이미지

    private String categories;  // 카테고리

    private String room_name;   // 가게 이름

    public BoardActivity(String img, String categories, String room_name){
        this.img = img;
        this.categories = categories;
        this.room_name = room_name;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_board);

        this.initializeData();
        RecyclerView view = findViewById(R.id.recyclerView);

        BoardAdapter verticalAdapter = new BoardAdapter(this, allBoardList, "윤겸빈");

        view.setHasFixedSize(true);
        view.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
        view.setAdapter(verticalAdapter);

        // 코스 만들기 페이지로 이동
        ImageButton write1 = (ImageButton) findViewById(R.id.write1);
        write1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), CourseMakingActivity.class);
                startActivity(intent);
            }
        });
    }

    public void initializeData()
    {
        ArrayList<Points> boardList1 = new ArrayList();

        boardList1.add(new Points(null, "어벤져스", "영화", 1.0, 1.0));
        boardList1.add(new Points(null, "미션임파서블", "영화", 1.0, 1.0));
        boardList1.add(new Points(null, "아저씨", "영화", 1.0, 1.0));

        allBoardList.add(boardList1);
//
//        ArrayList<Points> boardList2 = new ArrayList();
//
//        boardList2.add(new Points("R.drawable.pig1", "범죄도시", "영화", 1.0, 1.0));
//        boardList2.add(new Points("R.drawable.pig2", "공공의적", "영화", 1.0, 1.0));
//
//        allBoardList.add(boardList2);
//
//        ArrayList<Points> boardList3 = new ArrayList();
//
//        boardList3.add(new Points("R.drawable.pig1", "고질라", "영화", 1.0, 1.0));
//        boardList3.add(new Points("R.drawable.pig2", "캡틴마블", "영화", 1.0, 1.0));
//        boardList3.add(new Points("R.drawable.pig3", "아이언맨", "영화", 1.0, 1.0));
//        boardList3.add(new Points("R.drawable.pig1", "액션가면", "영화", 1.0, 1.0));
//
//        allBoardList.add(boardList3);
    }
}