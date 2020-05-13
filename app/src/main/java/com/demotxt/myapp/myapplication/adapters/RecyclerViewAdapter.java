package com.demotxt.myapp.myapplication.adapters;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;

import com.demotxt.myapp.myapplication.activities.ProductActivity;
import com.demotxt.myapp.myapplication.model.Product;
import com.demotxt.myapp.myapplication.R ;


import java.util.List;



public class RecyclerViewAdapter extends RecyclerView.Adapter<RecyclerViewAdapter.MyViewHolder> {

    private Context mContext ;
    private List<Product> mData ;
    RequestOptions option;


    public RecyclerViewAdapter(Context mContext, List<Product> mData) {
        this.mContext = mContext;
        this.mData = mData;

        // Request option for Glide
        option = new RequestOptions().centerCrop().placeholder(R.drawable.loading_shape).error(R.drawable.loading_shape);

    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View view ;
        LayoutInflater inflater = LayoutInflater.from(mContext);
        view = inflater.inflate(R.layout.product_row_item,parent,false) ;
        final MyViewHolder viewHolder = new MyViewHolder(view) ;
        viewHolder.view_container.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent i = new Intent(mContext, ProductActivity.class);
                i.putExtra("product_title",mData.get(viewHolder.getAdapterPosition()).getTitle());

                i.putExtra("product_id",mData.get(viewHolder.getAdapterPosition()).getId());

                i.putExtra("product_filename",mData.get(viewHolder.getAdapterPosition()).getFilename());
                //i.putExtra("anime_content",mData.get(viewHolder.getAdapterPosition()).getContent());

                mContext.startActivity(i);

            }
        });




        return viewHolder;
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {

        holder.tv_title.setText(mData.get(position).getTitle());
        holder.tv_id.setText(mData.get(position).getId());

        //holder.tv_content.setText(mData.get(position).getContent());



        // Load Image from the internet and set it into Imageview using Glide

        Glide.with(mContext).load(mData.get(position).getFilename()).apply(option).into(holder.img_thumbnail);



    }

    @Override
    public int getItemCount() {
        return mData.size();
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder {

        TextView tv_title ;
        TextView tv_id ;
        //TextView tv_content;
        ImageView img_thumbnail;
        LinearLayout view_container;





        public MyViewHolder(View itemView) {
            super(itemView);

            view_container = itemView.findViewById(R.id.container);
            tv_title = itemView.findViewById(R.id.product_name);

            tv_id = itemView.findViewById(R.id.product_id);
            //tv_content = itemView.findViewById(R.id.aa_content);

            img_thumbnail = itemView.findViewById(R.id.thumbnail);

        }
    }

}
