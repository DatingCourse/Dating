package com.example.datingcourse;

import android.animation.ObjectAnimator;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;
import java.util.List;

public class FragCommunity extends Fragment {
    private String nickName;
    private PostAdapter mRecyclerAdapter; //어댑터 클래스
    private ArrayList<Post> mPostItems = new ArrayList<>(); //코멘트 리스트 객체 생성
    private FirebaseFirestore db;
    private EditText et_comment;
    private FirebaseAuth mFirebaseAuth;
    private DatabaseReference mDatabaseRef;
    private ProgressDialog progressDialog;

    private ArrayList<String> documentIds; // 문서 ID들을 저장할 ArrayList 추가

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

    private List<Post> generateDummyPosts() {
        List<Post> posts = new ArrayList<>();

        // 여기에 사용자가 입력한 게시물 정보를 생성하고 리스트에 추가하는 로직 추가

        return posts;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.activity_frag_community, container, false);

        //파이어베이스 인증 및 데이터베이스 초기화등
        mFirebaseAuth = FirebaseAuth.getInstance();
        mDatabaseRef = FirebaseDatabase.getInstance().getReference("FirebaseRegister");

        if (getActivity() != null) {
            progressDialog = new ProgressDialog(getActivity());
        }
        progressDialog.setCancelable(false);
        progressDialog.setMessage("Fetching Data... ");
        progressDialog.show();

        db = FirebaseFirestore.getInstance();

        //새로운 Comments 클래스 타입의 arraylist만들어서
        List<Post> posts = generateDummyPosts();    // 실제 데이터 생성
        documentIds = new ArrayList<String>(); // ArrayList 초기화

        // RecyclerView 초기화 코드
        recyclerView = view.findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        recyclerView.setHasFixedSize(true);

        // 플로팅 버튼 구현
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
                Context context = getContext();
                if (context != null) {
                    Toast.makeText(context, "내가 쓴 글", Toast.LENGTH_LONG).show();
                }
            }
        });

        fab_write.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), PostMaking.class); // 글 쓰기
                startActivity(intent);
                Toast.makeText(getContext(), "글 쓰기", Toast.LENGTH_LONG).show();
            }
        });

        //adapter 생성자에 매개변수로 전달해줌
        if (mFirebaseAuth != null && mFirebaseAuth.getCurrentUser() != null) {
            mRecyclerAdapter = new PostAdapter(getActivity(), posts, mFirebaseAuth.getCurrentUser().getUid(), documentIds);
        } else {
            // Handle the error, e.g., by logging out the user and/or redirecting to a login screen.
        }
        recyclerView.setAdapter(mRecyclerAdapter); // 리사이클러뷰에 어댑터 설정

        loadComments(); // 데이터를 로드하는 함수 호출

        if (progressDialog.isShowing()) {
            progressDialog.dismiss();
        }

        return view;
    }

    private void loadComments() {
        DatabaseReference postReference = FirebaseDatabase.getInstance().getReference("posts");

        postReference.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {
                Post post = dataSnapshot.getValue(Post.class);
                String documentId = dataSnapshot.getKey(); // 문서 ID를 얻어옵니다.
                mPostItems.add(post);  // 데이터를 리스트에 추가합니다.
                documentIds.add(documentId);  // 문서 ID를 리스트에 추가합니다.

                mRecyclerAdapter.notifyDataSetChanged();  // 어댑터에게 데이터가 변경되었음을 알립니다.
                progressDialog.dismiss();  // ProgressDialog를 닫습니다.
            }

            @Override
            public void onChildChanged(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {
                // 데이터 변경 시 호출됩니다.
            }

            @Override
            public void onChildRemoved(@NonNull DataSnapshot dataSnapshot) {
                // 데이터 삭제 시 호출됩니다.
            }

            @Override
            public void onChildMoved(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {
                // 데이터 순서 변경 시 호출됩니다.
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                // 에러 발생 시 호출됩니다.
                progressDialog.dismiss();
                Toast.makeText(getContext(), "Failed to load data", Toast.LENGTH_SHORT).show();
                Log.d("FragCommunity", "Error: ", databaseError.toException());
            }
        });
        mRecyclerAdapter.notifyDataSetChanged();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        if (progressDialog != null && progressDialog.isShowing()) {
            progressDialog.dismiss();
        }
    }

}

