package com.example.datingcourse;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class MyPage extends AppCompatActivity {

    private FirebaseAuth mFirebaseAuth;
    private DatabaseReference mDatabaseRef;
    private Button btn_signOut, btn_logOut,addComment;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_page);
        getSupportActionBar().setTitle("마이 페이지");

        mFirebaseAuth = FirebaseAuth.getInstance(); // -> 초기화하고
        mDatabaseRef = FirebaseDatabase.getInstance().getReference("FirebaseRegister");


        btn_signOut = findViewById(R.id.btn_signOut);
        btn_logOut = findViewById(R.id.btn_logOut);
        addComment = findViewById(R.id.addComment);

        btn_logOut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mFirebaseAuth.signOut();
                Intent intent = new Intent(MyPage.this, com.example.datingcourse.LoginActivity.class);
                startActivity(intent);
            }
        });

        btn_signOut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mFirebaseAuth.getCurrentUser().delete();
                Toast.makeText(MyPage.this,"회원 탈퇴가 완료되었습니다.",Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(MyPage.this, com.example.datingcourse.LoginActivity.class);
                startActivity(intent);
            }
        });
        addComment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MyPage.this, RecycleActivity.class);
                startActivity(intent);
            }
        });


    }
}