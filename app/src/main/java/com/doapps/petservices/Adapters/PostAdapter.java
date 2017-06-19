package com.doapps.petservices.Adapters;

import android.content.Context;
import android.content.Intent;
import android.support.compat.BuildConfig;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.doapps.petservices.Activities.PhotoActivity;
import com.doapps.petservices.Models.Post;
import com.doapps.petservices.Network.Models.PostResponse;
import com.doapps.petservices.R;
import com.doapps.petservices.Utils.Constants;
import com.squareup.picasso.Picasso;

import java.util.List;

/**
 * Created by NriKe on 05/06/2017.
 */

public class PostAdapter extends RecyclerView.Adapter<PostAdapter.MyHolder>{

    Context context;
    List<PostResponse> data;
    private final LayoutInflater inflater;

    public PostAdapter(Context context, List<PostResponse> data) {
        this.context = context;
        this.data = data;
        inflater = LayoutInflater.from(context);
    }

    public void addPost(PostResponse post){
        data.add(post);
        notifyDataSetChanged();
    }

    @Override
    public MyHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        int layout = R.layout.row_publicacion;
        View view = inflater.inflate(layout, parent, false);
        return new PostAdapter.MyHolder(view);
    }

    @Override
    public void onBindViewHolder(MyHolder holder, final int position) {
        holder.tv_name.setText("NAME TEST");
        holder.tv_post.setText(data.get(position).getDescription());
        if(data.get(position).getImage() != null){
            Picasso.with(context).load(data.get(position).getImage().getUrl()).into(holder.iv_photo);
            holder.iv_photo.setVisibility(View.VISIBLE);
        }else{
            holder.iv_photo.setVisibility(View.GONE);
        }
        holder.iv_photo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(context, PhotoActivity.class);
                i.putExtra(Constants.EXTRA_PHOTO,data.get(position).getImage().getUrl());
                context.startActivity(i);
            }
        });
    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    public class MyHolder extends RecyclerView.ViewHolder{

        ImageView iv_profile;
        TextView tv_date;
        TextView tv_name;
        TextView tv_post;
        ImageView iv_photo;

        public MyHolder(View itemView) {
            super(itemView);
            iv_photo = (ImageView) itemView.findViewById(R.id.iv_photo);
            iv_profile = (ImageView) itemView.findViewById(R.id.iv_profile);
            tv_name = (TextView) itemView.findViewById(R.id.tv_name);
            tv_post = (TextView) itemView.findViewById(R.id.tv_post);
            tv_date = (TextView) itemView.findViewById(R.id.tv_date);
        }
    }
}
