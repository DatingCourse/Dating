package com.example.datingcourse;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;

import java.util.ArrayList;

public class CafeAdapter extends RecyclerView.Adapter<CafeAdapter.HorizontalViewHolder> {
    private ArrayList<Cafe> dataList;
    private CafeAdapter.OnItemClickListener itemClickListener;
    public Context context;

    public CafeAdapter(Context context, ArrayList<Cafe> dataList) {
        this.context = context;
        this.dataList = dataList;
    }

    public class HorizontalViewHolder extends RecyclerView.ViewHolder {
        ImageView imgView_item;
        TextView txt_main;
        TextView txt_sub;


        public HorizontalViewHolder(View itemView) {
            super(itemView);

            imgView_item = itemView.findViewById(R.id.place_image);
            txt_main = itemView.findViewById(R.id.place_title);
            txt_sub = itemView.findViewById(R.id.place_content);

        }
    }

    // 아이템 뷰를 위한 뷰홀더 객체를 생성하여 리턴
    @NonNull
    @Override
    public HorizontalViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater
                .from(parent.getContext())
                .inflate(R.layout.activity_cafe, null);

        return new CafeAdapter.HorizontalViewHolder(v);
    }

    // position에 해당하는 데이터를 뷰홀더의 아이템뷰에 표시
    @Override
    public void onBindViewHolder(@NonNull HorizontalViewHolder holder, int position) {
        Cafe item = dataList.get(position);

        if(item != null){
            if(item.getImgName().equals("정보 없음")){
                Glide.with(context)
                        .asGif()  // GIF 파일로 로드
                        .load(R.drawable.no_data)  // 'no_data'는 res/drawable 폴더에 있는 GIF 파일의 이름입니다.
                        .into(holder.imgView_item);
            } else if(item.getImgName() != null){
                Glide.with(context)
                        .load(item.getImgName())
                        .placeholder(R.drawable.no_data)  // 로딩 중에 표시할 이미지 설정
                        .error(R.drawable.no_data)  // 이미지 로딩 실패 시 표시할 이미지 설정
                        .into(holder.imgView_item);  // 결과 이미지를 표시할 ImageView
            }
        }

        if(item != null && item.getImgName() != null){
            Glide.with(context).load(item.getImgName()).into(holder.imgView_item);
        } else{
            holder.imgView_item.setImageResource(R.drawable.pig1);
        }

        holder.txt_main.setText(item.getMainText());
        holder.txt_sub.setText(item.getSubText());

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(itemClickListener != null){
                    itemClickListener.onClick(v, position);
                }
            }
        });
    }

    public interface OnItemClickListener {
        void onClick(View v, int position);
    }

    @Override
    public int getItemCount() {
        return dataList.size();
    }

    public void setItemClickListener(OnItemClickListener onItemClickListener) {
        this.itemClickListener = onItemClickListener;
    }
}
