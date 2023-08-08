package com.example.datingcourse;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class RecycleActivity extends AppCompatActivity {
    private String nickName;    // 유저 이름
    private Button btn_write; //쓰기 버튼
    private RecyclerView mRecyclerView; //RecyclerView
    private CommentsAdapter mRecyclerAdapter; //어댑터 클래스
    private ArrayList<Comments> mCommentsItems;
    private FirebaseFirestore db;
    private EditText et_comment;
    private FirebaseAuth mFirebaseAuth;
    private DatabaseReference mDatabaseRef;
    private ProgressDialog progressDialog;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recycle);

        //입력한 댓글 가져오기
        et_comment = findViewById(R.id.et_comment);

        //댓글 쓰기 버튼 눌렀을 때 이벤트
        btn_write = findViewById(R.id.btn_write);
        btn_write.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String commentText = et_comment.getText().toString();

                createComment(commentText);
            }
        });
        mFirebaseAuth = FirebaseAuth.getInstance();
        mDatabaseRef = FirebaseDatabase.getInstance().getReference();
        progressDialog = new ProgressDialog(this);
        progressDialog.setCancelable(false);
        progressDialog.setMessage("Fetching Data... ");
        progressDialog.show();

        mRecyclerView = findViewById(R.id.rv_RecycleView);
        mRecyclerView.setHasFixedSize(true);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));

        db = FirebaseFirestore.getInstance();

        mCommentsItems = new ArrayList<Comments>();

        mRecyclerAdapter = new CommentsAdapter(RecycleActivity.this, mCommentsItems);

        mRecyclerView.setAdapter(mRecyclerAdapter);

        EventChangeListener();

        ArrayList<Comments> filteredCommentList = new ArrayList<>();
        for (Comments comment : mCommentsItems) {
            if (comment.getUserNick() != null && !comment.getUserNick().isEmpty()
                    && comment.getContext() != null && !comment.getContext().isEmpty()) {
                filteredCommentList.add(comment);
            }
        }

        CommentsAdapter adapter = new CommentsAdapter(this, filteredCommentList);


    }
    private void EventChangeListener() {

        db.collection("posts").orderBy("when", Query.Direction.ASCENDING)
                .addSnapshotListener(new EventListener<QuerySnapshot>() {

                    //새로운 데이터가 생성
                    //데이터 변경할 때
                    //데이터 삭제할 때
                    //처음 실행할 때 firebase에서 데이터 가져올 때 실행
                    @Override
                    public void onEvent(@Nullable QuerySnapshot value, @Nullable FirebaseFirestoreException error) {

                        if(error != null){

                            if(progressDialog.isShowing()){
                                progressDialog.dismiss();
                            }
                            Log.e("FireStore error",error.getMessage());

                            return;
                        }

                        mCommentsItems.clear();

                        // 스냅샷에서 새로운 데이터를 로드합니다.
                        for (DocumentSnapshot doc : value.getDocuments()) {
                            mCommentsItems.add(doc.toObject(Comments.class));
                        }

                        mRecyclerAdapter.notifyDataSetChanged();
                        if (progressDialog.isShowing()) {
                            progressDialog.dismiss();
                        }
                    }
                });

    }
    public void createComment(String commentText){

        String userId = mFirebaseAuth.getCurrentUser().getUid();

        com.google.firebase.Timestamp whenTimestamp = com.google.firebase.Timestamp.now();

        Map<String,Object> commentData = new HashMap<>();
        commentData.put("userNick",userId);
        commentData.put("when",whenTimestamp);
        commentData.put("context",commentText);

        db.collection("posts")
                .add(commentData)
                .addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                    @Override
                    public void onSuccess(DocumentReference documentReference) {
                        // Successfully saved comment
                        Log.d("createComment", "Comment added with ID: " + documentReference.getId());
                        et_comment.setText(""); // Clear the EditText after successful submission
                        Toast.makeText(RecycleActivity.this, "Comment Added Successfully!", Toast.LENGTH_SHORT).show();
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Log.w("createComment", "Error adding comment", e);
                        Toast.makeText(RecycleActivity.this, "Failed to Add Comment!", Toast.LENGTH_SHORT).show();
                    }
                });


    }


}