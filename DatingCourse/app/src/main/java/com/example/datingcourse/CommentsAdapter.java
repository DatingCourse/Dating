package com.example.datingcourse;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Locale;

public class CommentsAdapter extends RecyclerView.Adapter<CommentsAdapter.ViewHolder> {

    private DatabaseReference mDatabaseRef;

    private ArrayList<Comments> mCommentList;
    private Context context;

    public CommentsAdapter(Context context, ArrayList<Comments> mCommentList) {
        this.mCommentList = mCommentList;
        this.context = context;

        FirebaseFirestore db = FirebaseFirestore.getInstance();
    }

    @NonNull
    @Override
    public CommentsAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_recycle, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull CommentsAdapter.ViewHolder holder, int position) {
        holder.onBind(mCommentList.get(position));
    }

    @Override
    public int getItemCount() {
        return mCommentList.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        TextView userNick,context, when;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            userNick = (TextView) itemView.findViewById(R.id.tv_userid);
            context = (TextView) itemView.findViewById(R.id.tv_content);
            when = (TextView) itemView.findViewById(R.id.tv_date);

        }

        void onBind(Comments item){
            userNick.setText(item.getUserNick());
            context.setText(item.getContext());
            // Timestamp를 문자열로 변환
            com.google.firebase.Timestamp whenTimestamp = item.getWhen();
            if (whenTimestamp != null) {
                Date whenDate = whenTimestamp.toDate();
                String whenString = new SimpleDateFormat("yyyy.MM.dd HH:mm", Locale.getDefault()).format(whenDate);
                when.setText(whenString); // 변환한 문자열로 TextView 업데이트
            } else {
                when.setText(""); // when 값이 없는 경우, 비워 둡니다.
            }
        }
    }
}
