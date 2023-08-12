package com.example.datingcourse;

public class Points {
    private String mImgName;    // 이미지
    private String mMainText;   // 장소 이름
    private String mSubText;    // 카테고리

    private Double x;         // 경도(Longitude)

    private Double y;         // 위도(Latitude)

    public Points(String mImgName, String mMainText, String mSubText, Double x, Double y) {
        this.mImgName = mImgName;
        this.mMainText = mMainText;
        this.mSubText = mSubText;
        this.x = x;
        this.y = y;
    }

    public String getImgName() {
        return mImgName;
    }

    public void setImgName(String imgName) {
        this.mImgName = imgName;
    }

    public String getMainText() {
        return mMainText;
    }

    public void setMainText(String mMainCount) {
        this.mMainText = mMainCount;
    }

    public String getSubText() {
        return mSubText;
    }

    public void setSubText(String subText) {
        this.mSubText = subText;
    }

    public Double getX() {
        return x;
    }

    public void setX(Double x) {
        this.x = x;
    }

    public Double getY() {
        return y;
    }

    public void setY(Double y) {
        this.y = y;
    }
}
