package com.example.datingcourse;

public class Points {
    private int mImgName;
    private String mMainText;
    private String mSubText;

    public Points(int mImgName, String mMainText, String mSubText) {
        this.mImgName = mImgName;
        this.mMainText = mMainText;
        this.mSubText = mSubText;
    }

    public int getImgName() {
        return mImgName;
    }

    public void setImgName(int imgName) {
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
}
