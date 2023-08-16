package com.example.datingcourse;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager2.widget.ViewPager2;

import java.util.ArrayList;
import java.util.List;

public class PostAdapter extends RecyclerView.Adapter<PostAdapter.ViewHolder> {

    private Context context;
    private List<Post> posts;
    private String currentUserId;
    private ArrayList<String> documentId;

    private OnCommentActionListener mOnCommentActionListener;

    public PostAdapter(Context context, ArrayList<Post> posts, String currentUserId, ArrayList<String> documentId) {
        this.context = context;
        this.posts = posts;
        this.currentUserId = currentUserId;
        this.documentId = documentId;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView titleTextView, nicknameTextView, contentTextView, dateTextView;
        ViewPager2 imageView;

        public ViewHolder(View itemView) {
            super(itemView);
            titleTextView = itemView.findViewById(R.id.postTitle);
            nicknameTextView = itemView.findViewById(R.id.postNickname);
            contentTextView = itemView.findViewById(R.id.postContent);
            dateTextView = itemView.findViewById(R.id.postDate); // 추가
            imageView = itemView.findViewById(R.id.post_image);
        }
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_post, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Post post = posts.get(position);
        holder.titleTextView.setText(post.getTitle());
        holder.nicknameTextView.setText(post.getNickname());
        holder.contentTextView.setText(post.getContent());
//        holder.dateTextView.setText(post.getDate()); // 날짜 표시를 위해 추가
//        holder.imageView.setCurrentItem(post.getImageRes()); // 이미지 리소스 설정
    }

    @Override
    public int getItemCount() {
        return posts.size();
    }
}
