package com.example.datingcourse;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

public class FragHome extends Fragment {
    private View view;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        view = inflater.inflate(R.layout.activity_main, container, false);

        // 카페 페이지로 이동
        ImageButton imageButton1 = view.findViewById(R.id.imageButton1);
        imageButton1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // 다른 액티비티로 이동
                int sigunguCode = 1;
                int contentTypeId = 39;
                String cat1 = "A05";
                String cat2 = "A0502";
                String cat3 = "A05020900";
                Intent intent = new Intent(getActivity(), PlaceList.class);    // Cafe
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
        ImageButton imageButton2 = view.findViewById(R.id.imageButton2);
        imageButton2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int sigunguCode = 1;
                int contentTypeId = 39;
                String cat1 = "";
                String cat2 = "";
                String cat3 = "";
                Intent intent = new Intent(getActivity(), PlaceList.class);    // Cafe
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
        ImageButton imageButton3 = view.findViewById(R.id.imageButton3);
        imageButton3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // 다른 액티비티로 이동
                int sigunguCode = 1;
                int contentTypeId = 32;
                String cat1 = "";
                String cat2 = "";
                String cat3 = "";
                Intent intent = new Intent(getActivity(), PlaceList.class);    // Cafe
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
        ImageButton imageButton4 = view.findViewById(R.id.imageButton4);
        imageButton4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // 다른 액티비티로 이동
                Intent intent = new Intent(getActivity(), CourseMakingActivity.class);
                startActivity(intent);
            }
        });

        // 코스 추천 페이지로 이동
        ImageButton imageButton5 = view.findViewById(R.id.imageButton5);
        imageButton5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // 다른 액티비티로 이동
                Intent intent = new Intent(getActivity(), BoardActivity.class);
                startActivity(intent);
            }
        });

        // 랜덤 코스 페이지로 이동
        ImageButton imageButton6 = view.findViewById(R.id.imageButton6);
        imageButton6.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // 다른 액티비티로 이동
                Intent intent = new Intent(getActivity(), RandomActivity.class);
                startActivity(intent);
            }
        });
        return view;
    }
}



