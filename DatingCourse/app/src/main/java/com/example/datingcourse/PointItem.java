package com.example.datingcourse;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import java.util.ArrayList;
import java.util.List;

public class PointItem extends AppCompatActivity {

    private RecyclerView recyclerView;
    private ProductAdapter adapter;

    private List<Product> productList;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_point_item);

        recyclerView = findViewById(R.id.recycler_view);
        GridLayoutManager gridLayoutManager = new GridLayoutManager(this, 2);
        recyclerView.setLayoutManager(gridLayoutManager);

        productList = new ArrayList<>();

        productList.add(new Product("스타벅스","아이스 카페 아메리카노 T", 4950, R.drawable.item_1));
        productList.add(new Product("맘스터치", "싸이버거 단품",5000, R.drawable.item_2));
        productList.add(new Product("BHC","뿌링클+치즈볼+콜라1.25L", 27500, R.drawable.item_3));
        productList.add(new Product("죠스떡볶이","죠스떡볶이",4950, R.drawable.item_4));
        productList.add(new Product("던킨","도너츠 6개팩", 11000, R.drawable.item_5));
        productList.add(new Product("맥도날드","불고기 버거 세트", 4950, R.drawable.item_6));
        productList.add(new Product("문화상품권","문화상품권[PIN]교환권 3000원권", 3300, R.drawable.item_7));
        productList.add(new Product("조말론런던","[선물포장]핸드 크림 50ML", 4500, R.drawable.item_8));
        productList.add(new Product("GS25","모바일 상품권 5천원권", 5500, R.drawable.item_9));
        productList.add(new Product("CU","모바일 상품권 5천원권", 5500, R.drawable.item_10));

        adapter = new ProductAdapter(productList);

        recyclerView.setAdapter(adapter);
        adapter.setOnItemClickListener(new ProductAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                // 클릭된 아이템에 대한 동작을 여기에 추가합니다.
                // position 매개변수는 클릭된 아이템의 위치를 나타냅니다.
                Product clickedProduct = productList.get(position);
                AlertDialog.Builder builder = new AlertDialog.Builder(PointItem.this);
                builder.setTitle("제품 구매 확인");
                builder.setMessage("제품명: "+clickedProduct.getName2()+"\n가격: " + clickedProduct.getPrice() + " 포인트\n구매하시겠습니까?");

                // "확인" 버튼 클릭 시 동작 설정
                builder.setPositiveButton("확인", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        // 여기에 확인 버튼을 클릭했을 때 수행할 동작 추가
                        // 예: 제품 구매 처리 등
                        dialog.dismiss(); // 다이얼로그 닫기
                    }
                });

                // "취소" 버튼 클릭 시 동작 설정
                builder.setNegativeButton("취소", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        // 여기에 취소 버튼을 클릭했을 때 수행할 동작 추가 (예: 다이얼로그 닫기)
                        dialog.dismiss();
                    }
                });

                // 다이얼로그 표시
                AlertDialog dialog = builder.create();
                dialog.show();
            }
        });


    }
}
