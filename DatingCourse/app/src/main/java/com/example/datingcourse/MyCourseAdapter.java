package com.example.datingcourse;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class MyCourseAdapter extends RecyclerView.Adapter<MyCourseAdapter.BoardViewHolder> {

    private ArrayList<ArrayList<Photo>> AllBoardList;
    private Context context;


    public MyCourseAdapter(Context context, ArrayList<ArrayList<Photo>> board) {
        this.context = context;
        this.AllBoardList = board;
    }

    public class BoardViewHolder extends RecyclerView.ViewHolder {
        protected RecyclerView recyclerView;

        public BoardViewHolder(View view)
        {
            super(view);

            this.recyclerView = (RecyclerView)view.findViewById(R.id.recyclerViewVertical);
        }
    }

    @NonNull
    @Override
    public BoardViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View v = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item_mycourse, null);
        return new MyCourseAdapter.BoardViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull BoardViewHolder holder, int position) {
        PointAdapter pointAdapter = new PointAdapter(context.getApplicationContext(), AllBoardList.get(position));
        pointAdapter.setItemClickListener(new PointAdapter.OnItemClickListener() {
            @Override
            public void onClick(View v, int pos, Photo photo) {
                Intent intent = new Intent(context, CourseListActivity.class);
                intent.putExtra("photo", photo);
                intent.putExtra("photosList", AllBoardList.get(position));
                context.startActivity(intent);
            }
        });


        holder.recyclerView.setHasFixedSize(true);
        holder.recyclerView.setLayoutManager(new LinearLayoutManager(context
                , LinearLayoutManager.HORIZONTAL
                ,false));
        holder.recyclerView.setAdapter(pointAdapter);
    }

    @Override
    public int getItemCount() {
        return AllBoardList.size();
    }
}