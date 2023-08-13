package com.example.datingcourse;

public class Photo {
    private String imgUrl;
    private String titleName;
    private String addressName;
    private Double x;
    private Double y;
    private String tel;

    // 생성자
    public Photo(String imgUrl, String titleName, String addressName, Double x, Double y, String tel) {
        this.imgUrl = imgUrl;
        this.titleName = titleName;
        this.addressName = addressName;
        this.x = x;
        this.y = y;
        this.tel = tel;
    }

    public String getImgUrl() {
        return imgUrl;
    }

    public void setImgUrl(String imgUrl) {
        this.imgUrl = imgUrl;
    }

    public String getTitleName() {
        return titleName;
    }

    public void setTitleName(String titleName) {
        this.titleName = titleName;
    }

    public String getAddressName() {
        return addressName;
    }

    public void setAddressName(String addressName) {
        this.addressName = addressName;
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

    public String getTel() {
        return tel;
    }

    public void setTel(String tel) {
        this.tel = tel;
    }
}
