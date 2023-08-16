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
import com.google.firebase.firestore.DocumentChange;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;

public class FragCommunity extends Fragment {
    private String nickName;
    private PostAdapter mRecyclerAdapter; //어댑터 클래스
    private ArrayList<Post> mCommentsItems; //코멘트 리스트 객체 생성
    private FirebaseFirestore db;
    private EditText et_comment;
    private FirebaseAuth mFirebaseAuth;
    private DatabaseReference mDatabaseRef;
    private ProgressDialog progressDialog;

    private ArrayList<String> documentIds; // 문서 ID들을 저장할 ArrayList 추가

    private View view;

    private RecyclerView mRecyclerView;
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
        List<Post> posts = new ArrayList<Post>();    // 실제 데이터 생성
        documentIds = new ArrayList<String>(); // ArrayList 초기화



        // RecyclerView 초기화 코드
        mRecyclerView = view.findViewById(R.id.recyclerView);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        mRecyclerView.setHasFixedSize(true);

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
//            mRecyclerAdapter = new PostAdapter(getActivity(), posts, mFirebaseAuth.getCurrentUser().getUid(), documentIds);
        } else {
            // Handle the error, e.g., by logging out the user and/or redirecting to a login screen.
        }
        mRecyclerView.setAdapter(mRecyclerAdapter); // 리사이클러뷰에 어댑터 설정

        loadComments(); // 데이터를 로드하는 함수 호출

        if (progressDialog.isShowing()) {
            progressDialog.dismiss();
        }

        return view;
    }

    private void loadComments() {
        db.collection("posts")
                .orderBy("when", Query.Direction.ASCENDING)
                .addSnapshotListener(new EventListener<QuerySnapshot>() {
                    @Override
                    public void onEvent(@Nullable QuerySnapshot value, @Nullable FirebaseFirestoreException error) {
                        if (error != null) {
                            Log.w("TAG", "Listen failed.", error);
                            progressDialog.dismiss();
                            return;
                        }

                        ArrayList<Post> newPosts = new ArrayList<>();
                        ArrayList<String> newDocumentIds = new ArrayList<>();

                        for (DocumentChange dc : value.getDocumentChanges()) {
                            if (dc.getType() == DocumentChange.Type.ADDED) {
                                Post postItem = dc.getDocument().toObject(Post.class);
                                String documentId = dc.getDocument().getId();
                                postItem.setDocumentId(documentId);
                                newDocumentIds.add(documentId);

                                newPosts.add(postItem);
                                mRecyclerView.scrollToPosition(newPosts.size() - 1);
                            }
                        }
                        documentIds.clear();
                        documentIds.addAll(newDocumentIds);

                        mCommentsItems.clear();
                        mCommentsItems.addAll(newPosts);

//                        mRecyclerAdapter.setCommentList(mCommentsItems);
//                        mRecyclerAdapter.setDocumentId(documentIds);
                        mRecyclerAdapter.notifyDataSetChanged();

                        if (progressDialog.isShowing()) {
                            progressDialog.dismiss();
                        }
                    }
                });
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        if (progressDialog != null && progressDialog.isShowing()) {
            progressDialog.dismiss();
        }
    }

}

