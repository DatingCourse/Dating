package com.example.datingcourse;

import android.animation.ObjectAnimator;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
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

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.firestore.DocumentChange;
import com.google.firebase.firestore.DocumentSnapshot;
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

        fetchSingleValueFromUserRef(mFirebaseAuth,mDatabaseRef);

        if (getActivity() != null) {
            progressDialog = new ProgressDialog(getActivity());
        }
        progressDialog.setCancelable(false);
        progressDialog.setMessage("Fetching Data... ");
        progressDialog.show();

        // RecyclerView 초기화 코드
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManagerWrapper(getActivity(), LinearLayoutManager.VERTICAL, false);
        mRecyclerView = view.findViewById(R.id.recyclerView);
        mRecyclerView.setHasFixedSize(true);
        mRecyclerView.setLayoutManager(mLayoutManager);

        db = FirebaseFirestore.getInstance();

        //새로운 Comments 클래스 타입의 arraylist만들어서
        mCommentsItems = new ArrayList<Post>(); // ArrayList 초기화
        Log.d("TAG", "activity1 Comments list: " + mCommentsItems.toString());

        documentIds = new ArrayList<String>(); // ArrayList 초기화

        loadComments();

        Log.d("TAG", "activity2 Comments list: " + mCommentsItems.toString());
        mRecyclerAdapter = new PostAdapter(getActivity(), mCommentsItems, mFirebaseAuth.getCurrentUser().getUid(), documentIds); // documentIds를 어댑터로 전달

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
                intent.putExtra("nickName", nickName);
                startActivity(intent);
                Toast.makeText(getContext(), "글 쓰기", Toast.LENGTH_LONG).show();
            }
        });

        //여기 recycerView에 어탭더 적용시켜줌
        mRecyclerView.setAdapter(mRecyclerAdapter)  ;
        mRecyclerAdapter.setOnBtnClickListener(new OnPostActionListener() {

            @Override
            public void onPostEditClick(Post item) {
                Log.d("TAG","수정 버튼 눌림");
                //선택한 댓글의 문서 ID 가져오기
                String editDocumentId = item.getDocumentId();

                // 다이얼 로그 생성
                AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
                //레이아웃 인플레이터를 사용하여 앱이 실행할 때 레이아웃 파일을 뷰 객체로 변환함
                View view = getLayoutInflater().inflate(R.layout.item_edit_comment,null);
                //item_edit_comment.xml 에 있는 editTextComment 버튼 인식
                EditText editTextComment = view.findViewById(R.id.editTextComment);
                //builder.setView(view)를 통해 레이아웃을 설정
                builder.setView(view);

                //posts밑에있는 문서 id에 있는 context 필드의 값을 가져와서
                //editText에 설정
                db.collection("posts").document(editDocumentId)
                        .get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
                            @Override
                            public void onSuccess(DocumentSnapshot documentSnapshot) {
                                String editCommentText = documentSnapshot.getString("context");
                                editTextComment.setText(editCommentText);
                            }
                        });
                //AlertDialog 세팅 및 버튼 리스너 등록
                builder.setTitle("댓글 수정")
                        .setPositiveButton("수정", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                //입력한 댓글 텍스트 가져오기
                                String updateCommentText = editTextComment.getText().toString();

                                db.collection("posts").document(editDocumentId)
                                        .update("context",updateCommentText)
                                        .addOnSuccessListener(new OnSuccessListener<Void>() {
                                            @Override
                                            public void onSuccess(Void unused) {
                                                mRecyclerAdapter.notifyDataSetChanged();
                                                loadComments();
                                                Toast.makeText(getActivity(),"댓글이 수정되었습니다.",Toast.LENGTH_SHORT).show();
                                            }
                                        }).addOnFailureListener(new OnFailureListener() {
                                            @Override
                                            public void onFailure(@NonNull Exception e) {
                                                Toast.makeText(getActivity(),"댓글 수정에 실패하였습니다.",Toast.LENGTH_SHORT).show();
                                            }
                                        });
                            }
                        }).setNegativeButton("취소", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {

                            }
                        });
                AlertDialog alertDialog = builder.create();
                alertDialog.show();
            }

            @Override
            public void onPostDeleteClick(Post item, int position) {
                Log.d("TAG", "Deleting doc ID: " + item.getDocumentId() + ", Position: " + position);
                String commentUserId = item.getDocumentId();
                String documentId = item.getDocumentId();

                if (FirebaseAuth.getInstance().getCurrentUser().getUid().equals(commentUserId)) {
                    if(position >= 0 && position < mCommentsItems.size()) {
                        onDeleteConfirmed(documentId, position);
                    }
                }else {
                    Toast.makeText(getActivity(), "지정한 위치에 해당하는 댓글이 없습니다.", Toast.LENGTH_SHORT).show();
                }
            }
        });

        EventChangeListener(); //스냅샷 이벤트 변경 리스너 설정

        return view;
    }

    private void onDeleteConfirmed(String documentId, int position) {
        if (position >= 0 && position < mCommentsItems.size() && position < documentIds.size()) {
            db.collection("posts").document(documentId)
                    .delete()
                    .addOnSuccessListener(new OnSuccessListener<Void>() {
                        @Override
                        public void onSuccess(Void aVoid) {
                            mRecyclerAdapter.notifyItemRemoved(position);
                            mRecyclerAdapter.notifyItemRangeChanged(position, mCommentsItems.size());
                            loadComments();
                            Toast.makeText(getActivity(), "댓글이 삭제되었습니다.", Toast.LENGTH_SHORT).show();
                        }
                    })
                    .addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            Toast.makeText(getActivity(), "삭제에 실패하였습니다. 다시 시도해주세요.", Toast.LENGTH_SHORT).show();
                        }
                    });
        } else {
            Toast.makeText(getActivity(), "지정한 위치에 해당하는 댓글이 없습니다.", Toast.LENGTH_SHORT).show();
        }
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
                            mCommentsItems.add(doc.toObject(Post.class));
                        }

                        mRecyclerAdapter.notifyDataSetChanged();
                        if (progressDialog.isShowing()) {
                            progressDialog.dismiss();
                        }
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

                        mRecyclerAdapter.setCommentList(mCommentsItems);
                        mRecyclerAdapter.setDocumentId(documentIds);
                        mRecyclerAdapter.notifyDataSetChanged();

                        if (progressDialog.isShowing()) {
                            progressDialog.dismiss();
                        }
                    }
                });
    }

}

