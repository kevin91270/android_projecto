package com.demotxt.myapp.myapplication.adapters;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import com.bumptech.glide.request.RequestOptions;
import com.demotxt.myapp.myapplication.R;

import com.demotxt.myapp.myapplication.activities.ProductActivity;

import com.demotxt.myapp.myapplication.model.Comment;
import com.demotxt.myapp.myapplication.model.Product;

import java.util.List;

public class RecyclerViewComment extends RecyclerView.Adapter<RecyclerViewComment.MyViewHolder>{
    private Context mContextComment;
    private List<Comment> mDataComment;
    RequestOptions optionComment;

    public RecyclerViewComment(Context mContextComment, List<Comment> mDataComment) {
        this.mContextComment = mContextComment;
        this.mDataComment = mDataComment;
        optionComment = new RequestOptions().centerCrop().placeholder(R.drawable.loading_shape).error(R.drawable.loading_shape);
    }
    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view ;
        LayoutInflater inflater = LayoutInflater.from(mContextComment);
        view = inflater.inflate(R.layout.comment_row_item,parent,false);

        final MyViewHolder viewHolder = new MyViewHolder(view);
        viewHolder.view_container.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(mContextComment, ProductActivity.class);
                i.putExtra("comment_content",mDataComment.get(viewHolder.getAdapterPosition()).getContent());
                i.putExtra("comment_email",mDataComment.get(viewHolder.getAdapterPosition()).getEmail());
                mContextComment.startActivity(i);
            }
        });


        return viewHolder;
    }
    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        holder.tv_content.setText(mDataComment.get(position).getContent());
        holder.tv_email.setText(mDataComment.get(position).getEmail());
    }
    @Override
    public int getItemCount() {
        return mDataComment.size();
    }


    public static class MyViewHolder extends RecyclerView.ViewHolder {
        TextView tv_content ;
        TextView tv_email ;
        LinearLayout view_container;

        public MyViewHolder(View itemView) {
            super(itemView);
            view_container = itemView.findViewById(R.id.containerComment);
            tv_content = itemView.findViewById(R.id.comment_content);
            tv_email = itemView.findViewById(R.id.comment_email);
            Log.i("TAG_MERDE", String.valueOf(tv_content));
            Log.i("TAG_MERDE", String.valueOf(tv_email));
            Log.i("TAG_MERDE", "-----------------");
        }
    }
}