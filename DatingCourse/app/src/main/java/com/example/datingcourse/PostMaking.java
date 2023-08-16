package com.example.datingcourse;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.firestore.DocumentChange;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class PostMaking extends AppCompatActivity {
    private String userId;
    private String nickName;
    private ArrayList<Model> models;
    private FirebaseFirestore db;
    private TextView et_nickName;
    private EditText et_title;
    private EditText et_comment;
    private FirebaseAuth mFirebaseAuth;
    private DatabaseReference mDatabaseRef;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_post_making);

        //파이어베이스 인증 및 데이터베이스 초기화등
        mFirebaseAuth = FirebaseAuth.getInstance();
        mDatabaseRef = FirebaseDatabase.getInstance().getReference("FirebaseRegister");

        et_nickName = findViewById(R.id.postNickname);
        //fetchSingleValueFromUserRef 메소드 이용해서 realtimeDatabase에 있는 현재 사용자의 닉네임을 recycleActivity의 필드 nickName에 넣어줌
        fetchSingleValueFromUserRef(mFirebaseAuth,mDatabaseRef);

        //입력한 댓글 가져오기
        et_title = findViewById(R.id.postTitle);
        et_comment = findViewById(R.id.postContent);

        Button saveBtn = findViewById(R.id.save_btn);
        saveBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // 게시물 내용
                String titleText = et_title.getText().toString();
                String commentText = et_comment.getText().toString();
                createPostFromGlobal(titleText, commentText, nickName);
            }
        });
    }

    public void createPostFromGlobal(String titleText, String commentText, String nickName){
        com.google.firebase.Timestamp whenTimestamp = com.google.firebase.Timestamp.now();

        Map<String,Object> commentData = new HashMap<>();
        commentData.put("when",whenTimestamp);
        commentData.put("title", titleText);
        commentData.put("context",commentText);
        commentData.put("userId",mFirebaseAuth.getCurrentUser().getUid()); //현재 사용자의 uid를 Authentication에서 가져와서 commentData에 넣어준다.
        db.collection("posts")
                .add(commentData)
                .addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                    @Override
                    public void onSuccess(DocumentReference documentReference) {
                        // Successfully saved comment
                        Log.d("createComment", "Comment added with ID: " + documentReference.getId());
                        et_comment.setText(""); // Clear the EditText after successful submission

                        Toast.makeText(PostMaking.this, "Comment Added Successfully!", Toast.LENGTH_SHORT).show();

                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Log.w("createComment", "Error adding comment", e);
                        Toast.makeText(PostMaking.this, "Failed to Add Comment!", Toast.LENGTH_SHORT).show();
                    }
                });
    }

    //realtimeDatabase에서 특정 값 하나만 가져오기(별명)
    public void fetchSingleValueFromUserRef(FirebaseAuth mFirebaseAuth, DatabaseReference mDatabaseRef){
        FirebaseUser currentUser = mFirebaseAuth.getCurrentUser(); //현재 로그인한 사용자
        if(currentUser != null){
            String uid = currentUser.getUid();

            DatabaseReference userRef = mDatabaseRef.child("UserAccount").child(uid);
            DatabaseReference specificValueRef = userRef.child("NickName");
            specificValueRef.addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot) {
                    //데이터 스냅샷이 한 번 호출되어 값을 가져옴
                    if(snapshot.exists()){
                        //값을 String으로 캐스팅하고 사용
                        nickName = snapshot.getValue(String.class);
                    } else {
                        Log.w("TAG", "해당하는 닉네임 없음");
                    }
                }

                @Override
                public void onCancelled(@NonNull DatabaseError error) {
                    //에러 발생시 호출됨
                    Log.w("TAG", "데이터 불러오는 과정에서 오류 발생");
                }
            });

        }

    }
}