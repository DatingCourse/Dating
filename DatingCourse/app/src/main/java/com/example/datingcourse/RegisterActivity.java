package com.example.datingcourse;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Locale;

public class RegisterActivity extends AppCompatActivity {

    private FirebaseAuth mFirebaseAuth;     //firebase 인증처리 하기 위해 선언
    private DatabaseReference mDatabaseRef; //실시간 서버에 연동 가능한 데이터 베이스
    private EditText mEtEmail,mEtPwd,mEtName,mEtCheckPwd,userBirth, mEtNickName;       //회원가입할 때 입력하는 text 박스 필드에 선언
    private Button mBtnRegister;            //회원가입할 때 누를 버튼
    private ImageButton mbtnGoBack;

    private Calendar c;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        Calendar myCalendar = Calendar.getInstance();
        DatePickerDialog.OnDateSetListener myDatePicker = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                myCalendar.set(Calendar.YEAR, year);
                myCalendar.set(Calendar.MONTH, month);
                myCalendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
                String myFormat = "yyyy/MM/dd";    // 출력형식   2021/07/26
                SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.KOREA);
                EditText et_date = (EditText) findViewById(R.id.Date);
                et_date.setText(sdf.format(myCalendar.getTime()));
            }
        };

        EditText et_Date = (EditText) findViewById(R.id.Date);
        et_Date.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new DatePickerDialog(RegisterActivity.this, myDatePicker, myCalendar.get(Calendar.YEAR),
                        myCalendar.get(Calendar.MONTH), myCalendar.get(Calendar.DAY_OF_MONTH)).show();
            }
        });

        //필드에 선언한 것들 초기화 하기
        mFirebaseAuth = FirebaseAuth.getInstance();
        mDatabaseRef = FirebaseDatabase.getInstance().getReference("FirebaseRegister"); //getReference안에 " " 앱이름, 프로젝트 이름

        mEtEmail = findViewById(R.id.et_emailRegister);     //xml에 설정한 Id로 초기화
        mEtPwd = findViewById(R.id.et_pwdRegister);
        mBtnRegister = findViewById(R.id.btn_registerReal);
        mEtName = findViewById(R.id.et_nameRegister); // 이름 입력받기
        mbtnGoBack = findViewById(R.id.btn_goBack);
        mEtCheckPwd = findViewById(R.id.et_checkPwd);
        mEtNickName = findViewById(R.id.et_nickNameRegister);

        //뒤로가기 버튼
        mbtnGoBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(RegisterActivity.this, com.example.datingcourse.LoginActivity.class);
                startActivity(intent);
            }
        });

        mBtnRegister.setOnClickListener(new View.OnClickListener() { //회원 가입 버튼이 클릭 되었을 때 액션들을 설정할 수 있다. onclick
            @Override
            public void onClick(View view) {

                userBirth = findViewById(R.id.Date);
                String userBirthDate = userBirth.getText().toString();
                //회원가입 처리 시작
                //회원가입 버튼을 눌렀을 때 입력 필드에 있는 값들을 getText()로 값을 받아와서 toString()으로 문자열로 변환해서 strEmail에 넣는다.
                String strEmail = mEtEmail.getText().toString();
                String strPwd = mEtPwd.getText().toString();
                String strName = mEtName.getText().toString();
                String strNickName = mEtNickName.getText().toString();

                if(strName.length()==0){
                    Toast.makeText(RegisterActivity.this,"이름을 입력해주세요",Toast.LENGTH_SHORT).show();
                    mEtName.requestFocus();
                    return;
                }
                if(strNickName.length()==0){
                    Toast.makeText(RegisterActivity.this,"닉네임을 입력해주세요",Toast.LENGTH_SHORT).show();
                    mEtNickName.requestFocus();
                    return;
                }
                if(strEmail.length() == 0){
                    Toast.makeText(RegisterActivity.this,"이메일 아이디를 입력해주세요",Toast.LENGTH_SHORT).show();
                    mEtEmail.requestFocus();
                    return;
                }
                if(strPwd.length() < 8 || strPwd.length() > 16){
                    Toast.makeText(RegisterActivity.this,"비밀번호는 8~16자까지 입력가능합니다.",Toast.LENGTH_SHORT).show();
                    mEtPwd.requestFocus();
                    return;
                }
                if((mEtCheckPwd.getText().toString().length())==0){
                    Toast.makeText(RegisterActivity.this,"비밀번호 확인란을 입력해주세요",Toast.LENGTH_SHORT).show();
                    mEtCheckPwd.requestFocus();
                    return;
                }
                if(!((mEtPwd.getText().toString()).equals(mEtCheckPwd.getText().toString()))){
                    Toast.makeText(RegisterActivity.this,"비밀번호가 일치하지 않습니다",Toast.LENGTH_SHORT).show();
                    mEtPwd.setText("*******");
                    mEtPwd.setText("*******");
                    mEtPwd.requestFocus();
                    return;
                }


                //mFirebaseAuth 객체 이용하여 인증 절차 구현
                //firebase에서 이메일과 비밀번호를 이용하여 유저를 생성한다.
                mFirebaseAuth.createUserWithEmailAndPassword(strEmail,strPwd).addOnCompleteListener(RegisterActivity.this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        //회원가입이 성공되었을 때

                        //파라미터에 있는 task -> 회원가입 처리를 하고 결과값을 받아온 것임
                        //정상적으로 가져와서 가입 성공을 했다면
                        if(task.isSuccessful()){
                            FirebaseUser firebaseUser = mFirebaseAuth.getCurrentUser(); //인증 객체에서 현재 회원 가입된 유저
                            UserAccount account = new UserAccount();

                            account.setUserNickName(strNickName);
                            account.setUserBirth(userBirthDate);
                            //UserAccount 객체에 값들 넣기(캡슐화)
                            account.setUserName(strName);
                            account.setIdToken(firebaseUser.getUid()); //고유값
                            account.setEmailId(firebaseUser.getEmail()); //유저 이메일을 정확하게 가져오는 것이 중요하기 때문에 데이터베이스에서 가져옴
                            account.setPassWord(strPwd);
                            HashMap<Object,String> registerInfo = new HashMap<Object,String>();

                            // registerInfo 라는 HashMap에 account에 저장한 값들 가져와서 저장
                            registerInfo.put("Name",account.getUserName());
                            registerInfo.put("NickName",account.getUserNickName());
                            registerInfo.put("IdToken",account.getIdToken());
                            registerInfo.put("emailId",account.getEmailId());
                            registerInfo.put("password",account.getPassWord());
                            registerInfo.put("birthDate",account.getUserBirth());
                            //getUid를 key값으로 해서
                            //setValue: database에 삽입하는 행위
                            mDatabaseRef.child("UserAccount").child(firebaseUser.getUid()).setValue(registerInfo); //mDatabaseReference에 설정한 path의 하위 개념(child)으로 넣는 코드


                            mFirebaseAuth.getCurrentUser().sendEmailVerification().addOnCompleteListener(new OnCompleteListener<Void>() {
                                @Override
                                public void onComplete(@NonNull Task<Void> task) {
                                    if(task.isSuccessful()){
                                        //메세지 출력 -> 작은 메세지 박스 출력
                                        //LENGTH_SHORT -> 메세지를 짧게 출력해줌
                                        //maketext(컨텍스트,TXT,길이) -> 컨텍스트 : 사용할 어플리케이션 활동 개체 TXT : 출력할 메세지 입력
                                        Toast.makeText(RegisterActivity.this,"회원가입이 완료되었습니다. 이메일 인증 후 로그인 가능합니다.",Toast.LENGTH_LONG).show();
                                        Intent intent = new Intent(RegisterActivity.this, com.example.datingcourse.LoginActivity.class);
                                        startActivity(intent);
                                    }else{
                                        Toast.makeText(RegisterActivity.this,"이메일 발송 중 오류가 발생하였습니다.",Toast.LENGTH_SHORT).show();
                                    }

                                }
                            });

                        }
                        else{
                            Toast.makeText(RegisterActivity.this,"회원가입에 실패하였습니다. 다시 시도해주세요.",Toast.LENGTH_SHORT).show();
                        }
                    }
                });

            }
        });


    }
}