package com.example.datingcourse;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.firestore.FirebaseFirestore;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
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
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_point, parent, false);
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
        TextView userNick, context, when;
        Button btnDel, btnEdit;

        FirebaseFirestore db;

        OnPostActionListener mOnPostActionListener;

        public ViewHolder(@NonNull View itemView,OnPostActionListener onPostActionListener) {
            super(itemView);

            userNick = (TextView) itemView.findViewById(R.id.tv_userid);
            context = (TextView) itemView.findViewById(R.id.tv_content);
            when = (TextView) itemView.findViewById(R.id.tv_date);

            btnDel = (Button) itemView.findViewById(R.id.btn_delete);
            btnEdit = (Button) itemView.findViewById(R.id.btn_edit);

            db = FirebaseFirestore.getInstance();

            mOnPostActionListener = onPostActionListener;

        }

        //데이터 바인딩 메소드
        //어댑터 클래스에서 데이터를 표시하기 위한 뷰와 실제 데이터 연결
        void onBind(Post item) {
            userNick.setText(item.getNickname());
            context.setText(item.getContent());
            // Timestamp를 문자열로 변환
            com.google.firebase.Timestamp whenTimestamp = item.getDate();
            if (whenTimestamp != null) {
                Date whenDate = whenTimestamp.toDate();
                String whenString = new SimpleDateFormat("yyyy.MM.dd HH:mm", Locale.getDefault()).format(whenDate);
                when.setText(whenString); // 변환한 문자열로 TextView 업데이트
            } else {
                when.setText(""); // when 값이 없는 경우, 비워 둡니다.
            }



            if (currentUserId != null && currentUserId.equals(item.getDocumentId())) {
                btnEdit.setVisibility(View.VISIBLE);
                btnDel.setVisibility(View.VISIBLE);
                Log.d("TAG", "UID 일치: " + currentUserId + " == " + item.getDocumentId());

                btnEdit.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Log.d("TAG", "수정버튼눌림" + item.getDocumentId());
                        mOnPostActionListener.onPostEditClick(item);
                    }
                });
                btnDel.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Log.d("TAG", "Deleting doc ID: " + item.getDocumentId() + ", Position: " + getAdapterPosition());
                        mOnPostActionListener.onPostDeleteClick(item, getAdapterPosition());
                    }
                });
            }
            else{
                btnDel.setVisibility(View.GONE);
                btnEdit.setVisibility(View.GONE);
                Log.d("TAG", "UID 불일치: " + currentUserId + " != " + item.getDocumentId());
            }
        }
    }
}
