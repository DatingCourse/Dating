package com.example.datingcourse;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class LoginActivity extends AppCompatActivity {

    private FirebaseAuth mFirebaseAuth; // 파이어 베이스 인증 시스템 Auth
    private DatabaseReference mDatabaseRef; // 실시간 데이터 베이스
    private EditText mEtEmail, mEtPwd; // 로그인 입력필드


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        mFirebaseAuth = FirebaseAuth.getInstance();
        mDatabaseRef = FirebaseDatabase.getInstance().getReference("FirebaseRegister"); //getReference안에 " " 앱이름, 프로젝트 이름

        //R.id -> R은 오른쪽에 res 폴더를 가리킴 그 하위 폴더에 있는 id를 찾기 (.이용)
        //findViewById -> View 는 위 setContentView에 들어있는 activity_login 같은 view만 포함한다.
        mEtEmail = findViewById(R.id.et_email);
        mEtPwd = findViewById(R.id.et_password);

        Button btn_login = findViewById(R.id.btn_checkInfo);
        //btn_login이라고 된 id를 눌렀을 때
        btn_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //로그인 요청
                String strEmail = mEtEmail.getText().toString();
                String strPwd = mEtPwd.getText().toString();
                mFirebaseAuth.signInWithEmailAndPassword(strEmail,strPwd).addOnCompleteListener(LoginActivity.this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if(task.isSuccessful()){
                            if(mFirebaseAuth.getCurrentUser().isEmailVerified()){
                                //로그인 성공
                                //성공시 메인 화면으로 이동 시켜주기 Intent 생성자 (현재 페이지, 이동할 페이지)
                                startActivity(new Intent(LoginActivity.this, GuChoose.class));
                            }
                            else{
                                Toast.makeText(LoginActivity.this,"로그인 실패! 이메일 인증을 해주세요!",Toast.LENGTH_SHORT).show();
                            }
                        }
                        else{
                            Toast.makeText(LoginActivity.this,"로그인에 실패하였습니다. 다시 시도해주세요",Toast.LENGTH_SHORT).show();
                        }
                    }
                });
                // 이메일 인증 후 로그인 완료하고, 나갔다가 들어오면 처음 화면이 아니라 로그인한 후의 홈페이지로 들어오게끔 만들기코드
                // 시작 activity에서
//                FirebaseAuth firebaseAuth = FirebaseAuth.getInstance();
//                FirebaseUser firebaseUser = firebaseAuth.getCurrentUser();
//
//                if(firebaseUser != null && firebaseUser.isEmailVerified()){
//                    startActivity(new Intent(시작 페이지, 로그인한 후의 페이지));
//                }


            }
        });

        Button btn_register = findViewById(R.id.btn_register);
        btn_register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // loginActivity에서 회원가입 누르면 registerActivity로 이동하게 만들기
                // 회원 가입 화면으로 이동함, 화면을 이동할 때 사용하는 Intent (현재 자신의 액티비티, 이동할 액티비티)
                Intent intent = new Intent(LoginActivity.this, RegisterActivity.class);
                startActivity(intent);
            }
        });
    }
}