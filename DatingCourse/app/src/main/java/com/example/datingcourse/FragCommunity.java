package com.example.datingcourse;

import android.animation.ObjectAnimator;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.List;

public class FragCommunity extends Fragment {

    private View view;

    private RecyclerView recyclerView;
    private PostAdapter adapter;

    private boolean fab_main_status = false; //플로팅 버튼 상태
    private FloatingActionButton fab_main;
    private FloatingActionButton fab_myWrite;
    private FloatingActionButton fab_write;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_frag_community);

        //플로팅 버튼 구현
        fab_main = view.findViewById(R.id.fab_main);
        fab_write = view.findViewById(R.id.fab_write);
        fab_myWrite = view.findViewById(R.id.fab_myWrite);

        fab_main.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                toggleFab();
            }
        });

        fab_myWrite.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getContext(), "내가 쓴 글", Toast.LENGTH_LONG).show();
            }
        });

        fab_write.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getContext(), "내가 쓴 글", Toast.LENGTH_LONG).show();
            }
        });


    }

    public void toggleFab() {  //플로팅 버튼 애니메이션

        if(fab_main_status){
            //플로팅 액션 버튼 닫기
            //애니메이션 추가
            ObjectAnimator fm_animation = ObjectAnimator.ofFloat(fab_myWrite, "translationY", 0f);
            fm_animation.start();

            ObjectAnimator fw_animation = ObjectAnimator.ofFloat(fab_write, "translationY", 0f);
            fw_animation.start();
            //메인 플로팅 이미지 변경
            fab_main.setImageResource(R.drawable.heart);
        }else {
            //플로팅 액션 버튼 열기
            ObjectAnimator fm_animation = ObjectAnimator.ofFloat(fab_myWrite, "translationY", -200f);
            fm_animation.start();

            ObjectAnimator fw_animation = ObjectAnimator.ofFloat(fab_write, "translationY", -400f);
            fw_animation.start();
            //메인 플로팅 이미지 변경
            fab_main.setImageResource(R.drawable.plus_wh);
        }
        //플로팅 버튼 상태 변경
        fab_main_status = !fab_main_status;
    }

    private void setContentView(int activity_frag_community) {

        recyclerView = view.findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));

        List<Post> posts = generateDummyPosts(); // 실제 데이터 생성

        adapter = new PostAdapter(posts);
        recyclerView.setAdapter(adapter);
    }

    private List<Post> generateDummyPosts() {
        List<Post> posts = new ArrayList<>();

        // 여기에 사용자가 입력한 게시물 정보를 생성하고 리스트에 추가하는 로직 추가

        return posts;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.activity_frag_community, container, false);
        return view;
    }
}

