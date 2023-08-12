package com.example.datingcourse;

import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ScrollView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.bumptech.glide.Glide;

import net.daum.mf.map.api.MapPOIItem;
import net.daum.mf.map.api.MapPoint;
import net.daum.mf.map.api.MapPolyline;
import net.daum.mf.map.api.MapView;

public class infos extends AppCompatActivity implements MapView.CurrentLocationEventListener, MapView.MapViewEventListener  {
    private static final String BASE_URL = "https://dapi.kakao.com/";
    private static final String API_KEY = "KakaoAK 4b857970dbbfec9a77078e89f8b363cc"; // REST API 키

    private MapView mapView;

    private MapPolyline polyline;

    private ViewGroup mapViewContainer;

    public static InformationAPI.DataListener dataListener;

    private String keyword = ""; // 검색 키워드
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_infos);

        // 데이터 받아오기
        Intent intent = getIntent();
        String imgUrl = intent.getStringExtra("imgUrl");
        String titleName = intent.getStringExtra("titleName");
        String addressName = intent.getStringExtra("addressName");
        Double x = intent.getDoubleExtra("x", 0.0); // 기본값 0.0
        Double y = intent.getDoubleExtra("y", 0.0); // 기본값 0.0
        String tel = intent.getStringExtra("tel");

        // 값이 null이거나 빈 문자열인 경우 "정보 없음"으로 설정
        titleName = (titleName == null || titleName.isEmpty()) ? "정보 없음" : titleName;
        addressName = (addressName == null || addressName.isEmpty()) ? "정보 없음" : addressName;
        tel = (tel == null || tel.isEmpty()) ? "정보 없음" : tel;

        getSupportActionBar().setTitle(titleName);

        TextView representTitle = findViewById(R.id.represent_titles);
        representTitle.setText(titleName);

        TextView representContent = findViewById(R.id.represent_contents);
        representContent.setText(addressName);

        TextView representPhoneNum = findViewById(R.id.represent_phone_nums);
        representPhoneNum.setText(tel);

        ImageView representImage = findViewById(R.id.represents_image);
        Glide.with(this)
                .load(imgUrl)
                .into(representImage);

        mapView = new MapView(this);
        final ScrollView scrollView = findViewById(R.id.scroll_view_infos);
        mapViewContainer = (ViewGroup) findViewById(R.id.infos_mapView);
        mapViewContainer.addView(mapView);
        mapView.setMapViewEventListener(this);
        mapView.setCurrentLocationTrackingMode(MapView.CurrentLocationTrackingMode.TrackingModeOnWithoutHeading);

        // 지도에 polyline 추가
        polyline = new MapPolyline();
        polyline.setTag(1000);
        polyline.setLineColor(Color.argb(128, 255, 51, 0));

        MapPoint mapPoint = MapPoint.mapPointWithGeoCoord(y, x);
        mapView.setMapCenterPointAndZoomLevel(mapPoint, 1, true);

        // 지도에 마커 추가
        MapPOIItem point = new MapPOIItem();
        point.setItemName(titleName);
        point.setMapPoint(MapPoint.mapPointWithGeoCoord(y, x));
        point.setMarkerType(MapPOIItem.MarkerType.BluePin);
        point.setSelectedMarkerType(MapPOIItem.MarkerType.RedPin);
        mapView.addPOIItem(point);

        // Polyline에 좌표 추가
        polyline.addPoint(mapPoint);
        mapView.addPolyline(polyline);  // Polyline 지도에 올리기.

        Button btnSearch = findViewById(R.id.finding_btns);
        btnSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openKakaoMapForNavigation(x, y);
            }
        });

    }

    @Override
    protected void onPause() {
        super.onPause();
        mapView.onPause();
    }

    @Override
    protected void onResume() {
        super.onResume();
        mapView.onResume();
    }


    private void openKakaoMapForNavigation(Double x, Double y) {
        Intent intent;
        String kakaoUri = "kakaomap://route?sp=&ep=" + y + "," + x + "&by=FOOT"; // 도보 길찾기
        // 카카오맵 앱이 설치되어 있는지 확인
        try {
            getPackageManager().getPackageInfo("net.daum.android.map", 0);
            intent = new Intent(Intent.ACTION_VIEW, Uri.parse(kakaoUri));
        } catch (PackageManager.NameNotFoundException e) {
            // 카카오맵 앱이 설치되어 있지 않은 경우, Play Store로 연결
            intent = new Intent(Intent.ACTION_VIEW, Uri.parse("market://details?id=net.daum.android.map"));
        }

        startActivity(intent);
    }

    @Override
    public void onCurrentLocationUpdate(MapView mapView, MapPoint mapPoint, float v) {

    }

    @Override
    public void onCurrentLocationDeviceHeadingUpdate(MapView mapView, float v) {

    }

    @Override
    public void onCurrentLocationUpdateFailed(MapView mapView) {

    }

    @Override
    public void onCurrentLocationUpdateCancelled(MapView mapView) {

    }

    @Override
    public void onMapViewInitialized(MapView mapView) {

    }

    @Override
    public void onMapViewCenterPointMoved(MapView mapView, MapPoint mapPoint) {

    }

    @Override
    public void onMapViewZoomLevelChanged(MapView mapView, int i) {

    }

    @Override
    public void onMapViewSingleTapped(MapView mapView, MapPoint mapPoint) {

    }

    @Override
    public void onMapViewDoubleTapped(MapView mapView, MapPoint mapPoint) {

    }

    @Override
    public void onMapViewLongPressed(MapView mapView, MapPoint mapPoint) {

    }

    @Override
    public void onMapViewDragStarted(MapView mapView, MapPoint mapPoint) {

    }

    @Override
    public void onMapViewDragEnded(MapView mapView, MapPoint mapPoint) {

    }

    @Override
    public void onMapViewMoveFinished(MapView mapView, MapPoint mapPoint) {

    }
}