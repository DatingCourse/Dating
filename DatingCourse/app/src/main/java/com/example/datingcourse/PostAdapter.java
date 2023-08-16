package com.example.datingcourse;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

public class PostAdapter extends RecyclerView.Adapter<PostAdapter.ViewHolder> {

    private Context context;  // 필드명 변경: content -> context
    private List<Post> posts;
    private String currentUserId;
    private ArrayList<String> documentId;

    public PostAdapter(List<Post> posts){
        this.posts = posts;
    }

    public PostAdapter(Context context, List<Post> posts, String currentUserId, ArrayList<String> documentId) {
        this.context = context; // 수정된 필드명으로 변경
        this.posts = posts; // 초기화 코드 수정
        this.currentUserId = currentUserId;
        this.documentId = documentId;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView titleTextView, nicknameTextView, contentTextView, dateTextView;
        ImageView imageView;

        public ViewHolder(View itemView) {
            super(itemView);
            titleTextView = itemView.findViewById(R.id.postTitle);
            nicknameTextView = itemView.findViewById(R.id.postNickname);
            contentTextView = itemView.findViewById(R.id.postContent);
            imageView = itemView.findViewById(R.id.postprofile);
            //imageView = itemView.findViewById(R.id.postImage); // 주석 처리된 코드 제거
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
        holder.imageView.setImageResource(post.getImageRes()); // 중복 코드 제거
    }

    @Override
    public int getItemCount() {
        return posts.size();
    }
}
