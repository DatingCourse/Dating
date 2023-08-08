// 지도 선택 클래스
package com.example.datingcourse;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import net.daum.mf.map.api.MapPOIItem;
import net.daum.mf.map.api.MapPoint;
import net.daum.mf.map.api.MapView;

import java.io.IOException;
import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MapChoiceActivity extends AppCompatActivity implements MapView.CurrentLocationEventListener, MapView.MapViewEventListener {

    private static final String BASE_URL = "https://dapi.kakao.com/";
    private static final String API_KEY = "KakaoAK 4b857970dbbfec9a77078e89f8b363cc"; // REST API 키

    private MapView mapView;

    private ViewGroup mapViewContainer;

    private ArrayList<List_Layout> listItems = new ArrayList<>(); // 리사이클러 뷰 아이템
    private ListAdapter listAdapter = new ListAdapter(listItems); // 리사이클러 뷰 어댑터
    private int pageNumber = 1; // 검색 페이지 번호
    private String keyword = ""; // 검색 키워드

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_map_choice);
        getSupportActionBar().setTitle("장소 검색");

        mapView = new MapView(this);
        mapViewContainer = (ViewGroup) findViewById(R.id.mapView);
        mapViewContainer.addView(mapView);
        mapView.setMapViewEventListener(this);
        mapView.setCurrentLocationTrackingMode(MapView.CurrentLocationTrackingMode.TrackingModeOnWithoutHeading);

        // 리사이클러 뷰
        RecyclerView rvList = findViewById(R.id.rv_list);
        rvList.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
        rvList.setAdapter(listAdapter);
        // 리스트 아이템 클릭 시 해당 위치로 이동
        listAdapter.setItemClickListener(new ListAdapter.OnItemClickListener() {
            @Override
            public void onClick(View v, int position) {
                MapPoint mapPoint = MapPoint.mapPointWithGeoCoord(listItems.get(position).getY(), listItems.get(position).getX());
                mapView.setMapCenterPointAndZoomLevel(mapPoint, 1, true);
            }
        });


        Button btnSearch = findViewById(R.id.btn_search);
        // 검색 버튼
        btnSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // 검색 키워드를 사용자 입력에서 가져옵니다.
                EditText etSearchField = findViewById(R.id.et_search_field);
                keyword = etSearchField.getText().toString();
                pageNumber = 1;
                searchKeyword(keyword, pageNumber);
            }
        });

        // 이전 페이지 버튼
        Button btnPrevPage = findViewById(R.id.btn_prevPage);
        btnPrevPage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                pageNumber--;
                TextView tvPageNumber = findViewById(R.id.tv_pageNumber);
                tvPageNumber.setText(String.valueOf(pageNumber));
                searchKeyword(keyword, pageNumber);
            }
        });

        // 다음 페이지 버튼
        Button btnNextPage = findViewById(R.id.btn_nextPage);
        btnNextPage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                pageNumber++;
                TextView tvPageNumber = findViewById(R.id.tv_pageNumber);
                tvPageNumber.setText(String.valueOf(pageNumber));
                searchKeyword(keyword, pageNumber);
            }
        });
    }

    // 키워드 검색 함수
    private void searchKeyword(String keyword, int page) {
        Retrofit retrofit = new Retrofit.Builder() // Retrofit 구성
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        KakaoAPI api = retrofit.create(KakaoAPI.class); // 통신 인터페이스를 객체로 생성
        Call<ResultSearchKeyword> call = api.getSearchKeyword(API_KEY, keyword, page); // 검색 조건 입력

        // API 서버에 요청
        call.enqueue(new Callback<ResultSearchKeyword>() {
            @Override
            public void onResponse(Call<ResultSearchKeyword> call, Response<ResultSearchKeyword> response) {
                if (response.isSuccessful()) {
                    // 통신 성공 여부 확인
                    ResultSearchKeyword result = response.body();
                    Log.d("KakaoAPI", "Search success: " + result);
//                    Log.d("LocalSearch", "Search success: " + response.body());
                    addItemsAndMarkers(response.body());
                } else {
                    try {
                        String errorBody = response.errorBody().string();
                        Log.w("LocalSearch", "Search failed: " + errorBody);
                    } catch (IOException e) {
                        Log.e("LocalSearch", "Error reading errorBody", e);
                    }
                }
            }

            @Override
            public void onFailure(Call<ResultSearchKeyword> call, Throwable t) {
                // 통신 실패
                Log.w("LocalSearch", "통신 실패: " + t.getMessage());
            }
        });
    }

    // 검색 결과 처리 함수
    private void addItemsAndMarkers(ResultSearchKeyword searchResult) {
        if (searchResult != null && searchResult.getDocuments() != null && !searchResult.getDocuments().isEmpty()) {
            // 검색 결과 있음
            listItems.clear(); // 리스트 초기화
            mapView.removeAllPOIItems(); // 지도의 마커 모두 제거
            for (Place document : searchResult.getDocuments()) {
                // 결과를 리사이클러 뷰에 추가
                List_Layout item = new List_Layout(document.getPlaceName(), document.getRoadAddressName(),
                        document.getAddressName(), Double.parseDouble(document.getX()), Double.parseDouble(document.getY()));
                listItems.add(item);

                // 지도에 마커 추가
                MapPOIItem point = new MapPOIItem();
                point.setItemName(document.getPlaceName());
                point.setMapPoint(MapPoint.mapPointWithGeoCoord(Double.parseDouble(document.getY()), Double.parseDouble(document.getX())));
                point.setMarkerType(MapPOIItem.MarkerType.BluePin);
                point.setSelectedMarkerType(MapPOIItem.MarkerType.RedPin);
                mapView.addPOIItem(point);
            }
            listAdapter.notifyDataSetChanged();

            Button btnNextPage = findViewById(R.id.btn_nextPage);
            btnNextPage.setEnabled(!searchResult.getMeta().isEnd()); // 페이지가 더 있을 경우 다음 버튼 활성화

            Button btnPrevPage = findViewById(R.id.btn_prevPage);
            btnPrevPage.setEnabled(pageNumber != 1); // 1페이지가 아닐 경우 이전 버튼 활성화

        } else {
            // 검색 결과 없음
            Toast.makeText(this, "검색 결과가 없습니다", Toast.LENGTH_SHORT).show();
        }
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