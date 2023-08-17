package com.example.datingcourse;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager2.adapter.FragmentStateAdapter;
import androidx.viewpager2.widget.ViewPager2;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageException;
import com.google.firebase.storage.StorageReference;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Locale;

//리사이클러 뷰 어댑터 생성
public class MyPostAdapter extends RecyclerView.Adapter<MyPostAdapter.ViewHolder> {

    private ArrayList<Post> mCommentList;
    private Context context;
    private String currentUserId;
    private ArrayList<String> documentId;

    private MyPostAdapter.OnItemClickListener itemClickListener;

    private OnMyPostActionListener mOnMyPostActionListener;

    //어댑터 생성자
    public MyPostAdapter(Context context, ArrayList<Post> mCommentList, String currentUserId, ArrayList<String> documentId) {
        this.mCommentList = mCommentList;
        this.context = context;
        this.currentUserId = currentUserId;
        this.documentId = documentId;
    }
    public void setOnBtnClickListener(OnMyPostActionListener clickListener){

        mOnMyPostActionListener = clickListener;
    }
    public void setCommentList(ArrayList<Post> mCommentList) {
        this.mCommentList = mCommentList;
        notifyDataSetChanged();
    }

    public void setDocumentId(ArrayList<String> documentId) {

        this.documentId = documentId;
        notifyDataSetChanged();
    }
    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        //뷰 홀더 생성
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_post, parent, false);
        return new ViewHolder(view, mOnMyPostActionListener);
    }

    //데이터 바인딩
    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Post comments = mCommentList.get(position);
        comments.setDocumentId(documentId.get(position));
        //댓글 생성
        holder.onBind(comments);
    }

    //아이템 개수 반환
    @Override
    public int getItemCount() {
        return mCommentList.size();
    }

    public interface OnItemClickListener {
        void onClick(View v, int position, Post post);
    }

    public void setItemClickListener(MyPostAdapter.OnItemClickListener onItemClickListener) {
        this.itemClickListener = onItemClickListener;
    }


    // 어댑터에서 createViewHolder할때
    //뷰 홀더 이너클래스
    class ViewHolder extends RecyclerView.ViewHolder {
        TextView userNick, postTitle, postContent, postDate, postLikeSize, postRecycleSize;
        ViewPager2 post_image;
        ImageView picture, recycle;
        FirebaseFirestore db;

        Button edit_post, delete_post;

        OnMyPostActionListener mOnMyPostActionListener;

        public ViewHolder(@NonNull View itemView,OnMyPostActionListener onMyPostActionListener) {
            super(itemView);

            userNick = (TextView) itemView.findViewById(R.id.postNickname);
            postTitle = (TextView) itemView.findViewById(R.id.postTitle);
            postContent = (TextView) itemView.findViewById(R.id.postContent);
            postDate = (TextView) itemView.findViewById(R.id.postDate);
            postLikeSize = (TextView) itemView.findViewById(R.id.post_like_size);
            postRecycleSize = (TextView) itemView.findViewById(R.id.post_recycle_size);

            picture = (ImageView) itemView.findViewById(R.id.postprofile);
            recycle = (ImageView) itemView.findViewById(R.id.post_recycle);

            post_image = (ViewPager2) itemView.findViewById(R.id.post_image);

            edit_post = (Button) itemView.findViewById(R.id.edit_post);
            delete_post = (Button) itemView.findViewById(R.id.delete_post);

            db = FirebaseFirestore.getInstance();

            mOnMyPostActionListener = onMyPostActionListener;
        }

        //데이터 바인딩 메소드
        //어댑터 클래스에서 데이터를 표시하기 위한 뷰와 실제 데이터 연결
        void onBind(Post item) {
            Log.d("TAG", "Nickname in Adapter: " + item.getContext());

            if (currentUserId != null && currentUserId.equals(item.getUserId())) {
                userNick.setText(item.getNickName());
                postTitle.setText(item.getTitle());
                postContent.setText(item.getContext());

                // Timestamp를 문자열로 변환
                com.google.firebase.Timestamp whenTimestamp = item.getWhen();
                if (whenTimestamp != null) {
                    Date whenDate = whenTimestamp.toDate();
                    String whenString = new SimpleDateFormat("yyyy.MM.dd HH:mm", Locale.getDefault()).format(whenDate);
                    postDate.setText(whenString); // 변환한 문자열로 TextView 업데이트
                } else {
                    postDate.setText(""); // when 값이 없는 경우, 비워 둡니다.
                }
                edit_post.setVisibility(View.VISIBLE);
                delete_post.setVisibility(View.VISIBLE);
                Log.d("TAG", "UID 일치: " + currentUserId + " == " + item.getUserId());

                edit_post.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Log.d("TAG", "수정버튼눌림" + item.getDocumentId());
                        mOnMyPostActionListener.onMyPostEditClick(item);
                    }
                });
                delete_post.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Log.d("TAG", "Deleting doc ID: " + item.getDocumentId() + ", Position: " + getAdapterPosition());
                        mOnMyPostActionListener.onMyPostDeleteClick(item, getAdapterPosition());
                    }
                });
            }

            recycle.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(context, RecycleActivity.class);
                    intent.putExtra("documentId", item.getDocumentId());
                    intent.putExtra("title", item.getTitle());
                    context.startActivity(intent);
                }
            });

            downloadImg(item);
            // 이미지 URL 리스트를 가져와 ImageAdapter에 연결
            List<String> imageUrls = item.getImageUrls();
            ImageAdapter imageAdapter = new ImageAdapter(context, imageUrls);

            // ViewPager2에 ImageAdapter 연결
            post_image.setAdapter(imageAdapter);
        }

        private void downloadImg(Post item) {
            FirebaseStorage storage = FirebaseStorage.getInstance();
            StorageReference storageRef = storage.getReference();
            String imgName = "profile" + item.getUserId();

            List<String> extensions = Arrays.asList(".jpg", ".jpeg", ".png");

            StorageReference defaultImageRef = storageRef.child("profile_img/default_profile_image.png"); // 기본 이미지 파일 경로

            loadProfileImage(storageRef, imgName, extensions, 0, defaultImageRef, item);
        }

        private void loadProfileImage(StorageReference storageRef, String imgName, List<String> extensions, int index, StorageReference defaultImageRef, Post item) {
            if (index < extensions.size()) {
                StorageReference profileImageRef = storageRef.child("profile_img/" + imgName + extensions.get(index));
                profileImageRef.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                    @Override
                    public void onSuccess(Uri uri) {
                        Glide.with(context)
                                .load(uri)
                                .error(R.drawable.noimage)
                                .fallback(R.drawable.noimage)
                                .placeholder(R.drawable.noimage)
                                .into(picture);
                    }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        if (e instanceof StorageException) {
                            StorageException storageException = (StorageException) e;
                            if (storageException.getErrorCode() == StorageException.ERROR_OBJECT_NOT_FOUND) {
                                // 파일이 존재하지 않을 때 다음 확장자로 시도
                                loadProfileImage(storageRef, imgName, extensions, index + 1, defaultImageRef, item);
                            } else {
                                e.printStackTrace();
                            }
                        } else {
                            e.printStackTrace();
                        }
                    }
                });
            } else {
                // 모든 확장자를 시도했음에도 프로필 이미지가 존재하지 않을 경우 기본 이미지를 로드
                defaultImageRef.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                    @Override
                    public void onSuccess(Uri uri) {
                        Glide.with(context)
                                .load(uri)
                                .error(R.drawable.noimage)
                                .fallback(R.drawable.noimage)
                                .placeholder(R.drawable.noimage)
                                .into(picture);
                    }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception exception) {
                        // 기본 이미지 로드에 실패한 경우 로그를 출력하거나 에러 처리를 할 수 있습니다.
                    }
                });
            }
        }
    }

    public static class ImageAdapter extends RecyclerView.Adapter<ImageAdapter.ImageViewHolder> {

        private Context context;
        private List<String> imageUrls;

        public ImageAdapter(Context context, List<String> imageUrls) {
            this.context = context;
            this.imageUrls = imageUrls;
        }

        @NonNull
        @Override
        public ImageViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_post_image, parent, false);
            return new ImageViewHolder(view);
        }

        @Override
        public void onBindViewHolder(@NonNull ImageViewHolder holder, int position) {
            String imageUrl = imageUrls.get(position);

            Glide.with(context)
                    .load(imageUrl)
                    .error(R.drawable.noimage)
                    .fallback(R.drawable.noimage)
                    .placeholder(R.drawable.noimage)
                    .into(holder.imageView);
        }

        @Override
        public int getItemCount() {
            if (imageUrls == null) {
                return 0;
            }
            return imageUrls.size();
        }

        public class ImageViewHolder extends RecyclerView.ViewHolder {

            ImageView imageView;

            public ImageViewHolder(@NonNull View itemView) {
                super(itemView);
                imageView = itemView.findViewById(R.id.imageView);
            }
        }
    }
}
