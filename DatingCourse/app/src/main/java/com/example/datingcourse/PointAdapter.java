package com.example.datingcourse;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class PointAdapter extends RecyclerView.Adapter<PointAdapter.HorizontalViewHolder> {
    private ArrayList<Points> dataList;

    public PointAdapter(ArrayList<Points> dataList) {
        this.dataList = dataList;
    }

    public class HorizontalViewHolder extends RecyclerView.ViewHolder {
        ImageView imgView_item;
        TextView txt_main;
        TextView txt_sub;


        public HorizontalViewHolder(View itemView) {
            super(itemView);

            imgView_item = itemView.findViewById(R.id.imgView_item);
            txt_main = itemView.findViewById(R.id.txt_main);
            txt_sub = itemView.findViewById(R.id.txt_sub);
        }
    }

    // 아이템 뷰를 위한 뷰홀더 객체를 생성하여 리턴
    @NonNull
    @Override
    public HorizontalViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater
                .from(parent.getContext())
                .inflate(R.layout.item_point, null);

        return new PointAdapter.HorizontalViewHolder(v);
    }

    // position에 해당하는 데이터를 뷰홀더의 아이템뷰에 표시
    @Override
    public void onBindViewHolder(@NonNull HorizontalViewHolder holder, int position) {
        holder.imgView_item.setImageResource(dataList.get(position).getImgName());
        holder.txt_main.setText(dataList.get(position).getMainText());
        holder.txt_sub.setText(dataList.get(position).getSubText());
    }

    @Override
    public int getItemCount() {
        return dataList.size();
    }
}
