// 코스 목록 클래스
package com.example.datingcourse;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.Outline;
import android.graphics.drawable.GradientDrawable;
import android.os.Bundle;
import android.view.View;
import android.view.ViewOutlineProvider;
import android.widget.FrameLayout;
import android.widget.ImageButton;
import android.widget.Spinner;

public class CourseListActivity extends AppCompatActivity {

    private Spinner spinner;

    // Android Java 코드
    public class CircleOutlineProvider extends ViewOutlineProvider {
        @Override
        public void getOutline(View view, Outline outline) {
            int diameter = Math.min(view.getWidth(), view.getHeight());
            outline.setOval(0, 0, diameter, diameter);
        }
    }

    // 다음 코드를 사용해 뷰에 원형 아웃라인을 설정합니다.
//    View yourView = findViewById(R.id.write);
//    yourView.setOutlineProvider(new void CircleOutlineProvider());
//    yourView.setClipToOutline(true);
//    yourView.setElevation(4);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_course_list);

        getSupportActionBar().setTitle("코스 목록");

        //검색창 테두리 커스텀
        FrameLayout list_search_edit_frame = findViewById(R.id.list_search_edit_frame);
        GradientDrawable borderDrawable = new GradientDrawable();
        borderDrawable.setShape(GradientDrawable.RECTANGLE);  //테두리 모형
        borderDrawable.setColor(Color.WHITE); //borderDrawable의 내부 배경 색상
        int myColor = Color.parseColor("#FF0063"); //원하는 색상 설정 코드
        borderDrawable.setStroke(10, myColor); //color.xml파일에서 설정한 색상 가져옴, 숫자는 테두리 굵기

        //검색창 테두리의 모서리 둥글게 하기
        float cornerRadiusInDp = 8;
        float cornerRadiusInPx = cornerRadiusInDp * getResources().getDisplayMetrics().density;
        borderDrawable.setCornerRadius(cornerRadiusInPx);

        // 코스 만들기 페이지로 이동
        ImageButton write = (ImageButton) findViewById(R.id.write);
        write.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), CourseMakingActivity.class);
                startActivity(intent);
            }
        });

        //여기부터는 사용자가 만든 코스들의 정보 페이지로 이동
        ImageButton first_button_1 = (ImageButton) findViewById(R.id.first_button_1);
        first_button_1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), info.class);
                startActivity(intent);
            }
        });

        ImageButton first_button_2 = (ImageButton) findViewById(R.id.first_button_2);
        first_button_2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), info.class);
                startActivity(intent);
            }
        });

        ImageButton first_button_3 = (ImageButton) findViewById(R.id.first_button_3);
        first_button_3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), info.class);
                startActivity(intent);
            }
        });

        ImageButton first_button_4 = (ImageButton) findViewById(R.id.first_button_4);
        first_button_4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), info.class);
                startActivity(intent);
            }
        });

        ImageButton first_button_5 = (ImageButton) findViewById(R.id.first_button_5);
        first_button_5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), info.class);
                startActivity(intent);
            }
        });

        ImageButton first_button_6 = (ImageButton) findViewById(R.id.first_button_6);
        first_button_6.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), info.class);
                startActivity(intent);
            }
        });

        ImageButton first_button_7 = (ImageButton) findViewById(R.id.first_button_7);
        first_button_7.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), info.class);
                startActivity(intent);
            }
        });

        ImageButton first_button_8 = (ImageButton) findViewById(R.id.first_button_8);
        first_button_8.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), info.class);
                startActivity(intent);
            }
        });

        ImageButton first_button_9 = (ImageButton) findViewById(R.id.first_button_9);
        first_button_9.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), info.class);
                startActivity(intent);
            }
        });

        ImageButton first_button_10 = (ImageButton) findViewById(R.id.first_button_10);
        first_button_10.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), info.class);
                startActivity(intent);
            }
        });

        ImageButton second_button_1 = (ImageButton) findViewById(R.id.second_button_1);
        second_button_1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), info.class);
                startActivity(intent);
            }
        });

        ImageButton second_button_2 = (ImageButton) findViewById(R.id.second_button_2);
        second_button_2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), info.class);
                startActivity(intent);
            }
        });

        ImageButton second_button_3 = (ImageButton) findViewById(R.id.second_button_3);
        second_button_3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), info.class);
                startActivity(intent);
            }
        });

        ImageButton second_button_4 = (ImageButton) findViewById(R.id.second_button_4);
        second_button_4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), info.class);
                startActivity(intent);
            }
        });

        ImageButton second_button_5 = (ImageButton) findViewById(R.id.second_button_5);
        second_button_5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), info.class);
                startActivity(intent);
            }
        });

        ImageButton second_button_6 = (ImageButton) findViewById(R.id.second_button_6);
        second_button_6.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), info.class);
                startActivity(intent);
            }
        });

        ImageButton second_button_7 = (ImageButton) findViewById(R.id.second_button_7);
        second_button_7.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), info.class);
                startActivity(intent);
            }
        });

        ImageButton second_button_8 = (ImageButton) findViewById(R.id.second_button_8);
        second_button_8.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), info.class);
                startActivity(intent);
            }
        });

        ImageButton second_button_9 = (ImageButton) findViewById(R.id.second_button_9);
        second_button_9.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), info.class);
                startActivity(intent);
            }
        });

        ImageButton second_button_10 = (ImageButton) findViewById(R.id.second_button_10);
        second_button_10.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), info.class);
                startActivity(intent);
            }
        });

        ImageButton third_button_1 = (ImageButton) findViewById(R.id.third_button_1);
        third_button_1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), info.class);
                startActivity(intent);
            }
        });

        ImageButton third_button_2 = (ImageButton) findViewById(R.id.third_button_2);
        third_button_2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), info.class);
                startActivity(intent);
            }
        });

        ImageButton third_button_3 = (ImageButton) findViewById(R.id.third_button_3);
        third_button_3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), info.class);
                startActivity(intent);
            }
        });

        ImageButton third_button_4 = (ImageButton) findViewById(R.id.third_button_4);
        third_button_4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), info.class);
                startActivity(intent);
            }
        });

        ImageButton third_button_5 = (ImageButton) findViewById(R.id.third_button_5);
        third_button_5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), info.class);
                startActivity(intent);
            }
        });

        ImageButton third_button_6 = (ImageButton) findViewById(R.id.third_button_6);
        third_button_6.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), info.class);
                startActivity(intent);
            }
        });

        ImageButton third_button_7 = (ImageButton) findViewById(R.id.third_button_7);
        third_button_7.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), info.class);
                startActivity(intent);
            }
        });

        ImageButton third_button_8 = (ImageButton) findViewById(R.id.third_button_8);
        third_button_8.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), info.class);
                startActivity(intent);
            }
        });

        ImageButton third_button_9 = (ImageButton) findViewById(R.id.third_button_9);
        third_button_9.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), info.class);
                startActivity(intent);
            }
        });

        ImageButton third_button_10 = (ImageButton) findViewById(R.id.third_button_10);
        third_button_10.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), info.class);
                startActivity(intent);
            }
        });

    }
}