package com.example.datingcourse;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;

import com.example.datingcourse.Beer;
import com.example.datingcourse.Cafe;
import com.example.datingcourse.CourseListActivity;
import com.example.datingcourse.R;
import com.example.datingcourse.Random;
import com.example.datingcourse.RecommendActivity;
import com.example.datingcourse.Restuarant;
import com.google.android.material.navigation.NavigationBarView;

public class FragHome extends Fragment {

    private View view;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        view = inflater.inflate(R.layout.activity_frag_home, container, false);
        return view;


    }
}



