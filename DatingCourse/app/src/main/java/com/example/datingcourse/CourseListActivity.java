package com.example.datingcourse;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import net.daum.mf.map.api.MapPOIItem;
import net.daum.mf.map.api.MapPoint;
import net.daum.mf.map.api.MapPolyline;
import net.daum.mf.map.api.MapView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class CourseListActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private CourseAdapter courseAdapter;
    private ArrayList<Photo> photos;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_course_list); // 여기서는 여러분의 레이아웃 XML 이름으로 교체해주세요.

        // 카카오 맵 초기화
        MapView mapView = new MapView(this);
        RelativeLayout mapViewContainer = findViewById(R.id.course_mapView);
        mapViewContainer.addView(mapView);

        Intent intent = getIntent();
//        ArrayList<HashMap<String, Object>> course = intent.getStringArrayListExtra("course");
        String imgUrl = intent.getStringExtra("imgUrl");
        String titleName = intent.getStringExtra("titleName");
        String addressName = intent.getStringExtra("addressName");
        Double x = intent.getDoubleExtra("x", 0.0); // 기본값 0.0
        Double y = intent.getDoubleExtra("y", 0.0); // 기본값 0.0
        String tel = intent.getStringExtra("tel");

        // 값이 null이거나 빈 문자열인 경우 "정보 없음"으로 설정
        imgUrl = (imgUrl == null || imgUrl.isEmpty()) ? null : imgUrl;
        titleName = (titleName == null || titleName.isEmpty()) ? "정보 없음" : titleName;
        addressName = (addressName == null || addressName.isEmpty()) ? "정보 없음" : addressName;
        tel = (tel == null || tel.isEmpty()) ? "정보 없음" : tel;

        Photo photo = new Photo(imgUrl, titleName, addressName, x, y, tel);

        // RecyclerView 설정
        recyclerView = findViewById(R.id.course_list);
        courseAdapter = new CourseAdapter(this, photos);
        recyclerView.setAdapter(courseAdapter);
        courseAdapter.notifyDataSetChanged();
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        // TODO: 여기서 photos 데이터를 로드하거나 업데이트 할 수 있습니다.
        // 예: photos.add(new Photo(...));
        // courseAdapter.notifyDataSetChanged();

        courseAdapter.setItemClickListener(new CourseAdapter.OnItemClickListener() {
            @Override
            public void onClick(View v, int position) {
                // 아이템을 클릭했을 때의 동작을 여기에 작성하세요.
                Toast.makeText(CourseListActivity.this, "Item " + position + " clicked", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void addMarkerToMap(MapView mapView, double x, double y, String name) {
        MapPoint mapPoint = MapPoint.mapPointWithGeoCoord(x, y);
        net.daum.mf.map.api.MapPOIItem marker = new net.daum.mf.map.api.MapPOIItem();
        marker.setItemName(name);
        marker.setTag(0);
        marker.setMapPoint(mapPoint);
        marker.setMarkerType(MapPOIItem.MarkerType.BluePin);
        marker.setSelectedMarkerType(MapPOIItem.MarkerType.RedPin);
        mapView.addPOIItem(marker);
    }

    private void addPolylineToMap(MapView mapView, List<Photo> photos) {
        MapPolyline polyline = new MapPolyline();
        polyline.setTag(1000);
        for (Photo photo : photos) {
            polyline.addPoint(MapPoint.mapPointWithGeoCoord(photo.getX(), photo.getY()));
        }
        mapView.addPolyline(polyline);
    }

}
