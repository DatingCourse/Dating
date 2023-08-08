// 코스 만들기 클래스
package com.example.datingcourse;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

public class CourseMakingActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_course_making);

        getSupportActionBar().setTitle("코스 만들기");

        Button search_address = (Button) findViewById(R.id.search_address);
        search_address.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), MapChoiceActivity.class);
                startActivity(intent);
            }
        });



    }
}