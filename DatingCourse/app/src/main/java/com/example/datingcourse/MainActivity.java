package com.example.datingcourse;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.os.Bundle;
import android.view.MenuItem;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationBarView;

public class MainActivity extends AppCompatActivity {

    // bottom navigationbar 구현하기 위함
    private BottomNavigationView bottomNavigationView;
    private FragmentManager fm;
    private FragmentTransaction ft;
    private FragHome fh;
    private FragCommunity fc;
    private FragLocation fl;
    private FragMyPage fp;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // bottom navigationbar 구현
        bottomNavigationView = findViewById(R.id.nav_view);
        bottomNavigationView.setOnItemSelectedListener(new NavigationBarView.OnItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
                int itemId = menuItem.getItemId();
                if (itemId == R.id.action_Home) {
                    setFrag(0);
                } else if (itemId == R.id.action_Community) {
                    setFrag(1);
                } else if (itemId == R.id.action_Location) {
                    setFrag(2);
                } else if (itemId == R.id.action_MyPage) {
                    setFrag(3);
                }
                return true;

            }

        });
        fh = new FragHome();
        fc = new FragCommunity();
        fl = new FragLocation();
        fp = new FragMyPage();
        setFrag(0); //첫 프래그먼트 화면을 무엇으로 지정해줄 것인지 선택

    }

    // 프래그먼트 교체가 일어나는 실행문
    private void setFrag(int n) {
        fm = getSupportFragmentManager();
        ft = fm.beginTransaction();
        switch(n){
            case 0 :
                ft.replace(R.id.main_frame, fh);
                ft.commit();
                break;
            case 1 :
                ft.replace(R.id.main_frame, fc);
                ft.commit();
                break;
            case 2 :
                ft.replace(R.id.main_frame, fl);
                ft.commit();
                break;
            case 3 :
                ft.replace(R.id.main_frame, fp);
                ft.commit();
                break;
        }
    }
}