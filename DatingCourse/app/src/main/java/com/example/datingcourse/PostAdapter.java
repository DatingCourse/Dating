package com.example.datingcourse;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class PostAdapter extends RecyclerView.Adapter<PostAdapter.ViewHolder> {

    private List<Post> posts;

    public PostAdapter(List<Post> posts) {
        this.posts = posts;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView titleTextView, nicknameTextView, contentTextView, dateTextView;
        ImageView imageView;

        public ViewHolder(View itemView) {
            super(itemView);
            titleTextView = itemView.findViewById(R.id.postTitle);
            nicknameTextView = itemView.findViewById(R.id.postNickname);
            imageView = itemView.findViewById(R.id.postprofile);
            contentTextView = itemView.findViewById(R.id.postContent);
            //imageView = itemView.findViewById(R.id.postImage);
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
        holder.imageView.setImageResource(post.getImageRes());
        holder.imageView.setImageResource(post.getImageRes());
    }

    @Override
    public int getItemCount() {
        return posts.size();
    }
}

