package com.example.datingcourse;

public class List_Layout {
    private String name;      // 장소명
    private String road;      // 도로명 주소
    private String address;   // 지번 주소
    private Double x;         // 경도(Longitude)
    private Double y;         // 위도(Latitude)

    // 생성자(Constructor)
    public List_Layout(String name, String road, String address, Double x, Double y) {
        this.name = name;
        this.road = road;
        this.address = address;
        this.x = x;
        this.y = y;
    }

    // Getter와 Setter 메소드(Getters and Setters)
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getRoad() {
        return road;
    }

    public void setRoad(String road) {
        this.road = road;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
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
