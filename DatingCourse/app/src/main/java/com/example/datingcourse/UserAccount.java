package com.example.datingcourse;

public class UserAccount {

    private String idToken; //Firebase 고유 토큰 정보, 사용자 하나 계정만 유일하게 가질수 있는 키값
    private String emailId;
    private String passWord;

    // 새로 추가한 회원가입 입력값
    private String userName,userNickName; // 사용자 이름

    private String userBirth;


    public UserAccount(){ } //클래스가 생성될 때 가장 먼저 호출, firebase에서는 빈 생성자를 안만들면 데이터베이스 조회시 오류 발생 가능

    //getter setter 메소드
    //Alt + insert 누르면 getter setter 편리하게 가능

    public String getUserNickName() {
        return userNickName;
    }

    public void setUserNickName(String userNickName) {
        this.userNickName = userNickName;
    }

    public String getIdToken() {
        return idToken;
    }

    public void setIdToken(String idToken) {
        this.idToken = idToken;
    }

    public String getEmailId() {
        return emailId;
    }
    public void setEmailId(String emailId) {
        this.emailId = emailId;
    }
    public String getPassWord(){
        return passWord;
    }
    public void setPassWord(String passWord) {
        this.passWord = passWord;
    }

    public String getUserBirth() {
        return userBirth;
    }

    public void setUserBirth(String userBirth) {
        this.userBirth = userBirth;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

}