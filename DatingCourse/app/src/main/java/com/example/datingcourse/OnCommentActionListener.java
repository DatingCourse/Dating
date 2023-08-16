package com.example.datingcourse;

public interface OnCommentActionListener {
    void onEditClick(Comments item);
    void onDeleteClick(Comments item, int position);
}

interface OnPostActionListener {
    void onPostEditClick(Post item);
    void onPostDeleteClick(Post item, int position);
}