package com.example.datingcourse;

import android.content.Context;
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
public class PostAdapter extends RecyclerView.Adapter<PostAdapter.ViewHolder> {

    private ArrayList<Post> mCommentList;
    private Context context;
    private String currentUserId;
    private ArrayList<String> documentId;

    private OnPostActionListener mOnPostActionListener;

    //어댑터 생성자
    public PostAdapter(Context context, ArrayList<Post> mCommentList, String currentUserId, ArrayList<String> documentId) {
        this.mCommentList = mCommentList;
        this.context = context;
        this.currentUserId = currentUserId;
        this.documentId = documentId;
    }
    public void setOnBtnClickListener(OnPostActionListener clickListener){

        mOnPostActionListener = clickListener;
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
        return new ViewHolder(view, mOnPostActionListener);
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


    // 어댑터에서 createViewHolder할때
    //뷰 홀더 이너클래스
    class ViewHolder extends RecyclerView.ViewHolder {
        TextView userNick, postTitle, postContent, postDate;
        ViewPager2 post_image;
        ImageView picture;
        FirebaseFirestore db;

        OnPostActionListener mOnPostActionListener;

        public ViewHolder(@NonNull View itemView,OnPostActionListener onPostActionListener) {
            super(itemView);

            userNick = (TextView) itemView.findViewById(R.id.postNickname);
            postTitle = (TextView) itemView.findViewById(R.id.postTitle);
            postContent = (TextView) itemView.findViewById(R.id.postContent);
            postDate = (TextView) itemView.findViewById(R.id.postDate);

            picture = (ImageView) itemView.findViewById(R.id.postprofile);

            post_image = (ViewPager2) itemView.findViewById(R.id.post_image);

            db = FirebaseFirestore.getInstance();

            mOnPostActionListener = onPostActionListener;

        }

        //데이터 바인딩 메소드
        //어댑터 클래스에서 데이터를 표시하기 위한 뷰와 실제 데이터 연결
        void onBind(Post item) {
            Log.d("TAG", "Nickname in Adapter: " + item.getUserId());
            userNick.setText(item.getNickName());
            postTitle.setText(item.getTitle());
            postContent.setText(item.getContent());

            // Timestamp를 문자열로 변환
            com.google.firebase.Timestamp whenTimestamp = item.getWhen();
            if (whenTimestamp != null) {
                Date whenDate = whenTimestamp.toDate();
                String whenString = new SimpleDateFormat("yyyy.MM.dd HH:mm", Locale.getDefault()).format(whenDate);
                postDate.setText(whenString); // 변환한 문자열로 TextView 업데이트
            } else {
                postDate.setText(""); // when 값이 없는 경우, 비워 둡니다.
            }
            downloadImg(item);
            for (int i = 0; i < item.getImageUrls().size(); i++){
                Log.d("images 제발", item.getImageUrls().get(i));
            }
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
}
