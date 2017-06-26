package com.doapps.petservices.Adapters;

import android.content.Context;
import android.content.Intent;
import android.support.compat.BuildConfig;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.doapps.petservices.Activities.PhotoActivity;
import com.doapps.petservices.Models.Post;
import com.doapps.petservices.Network.Models.PostResponse;
import com.doapps.petservices.PetServicesApplication;
import com.doapps.petservices.R;
import com.doapps.petservices.Utils.Constants;
import com.doapps.petservices.Utils.PreferenceManager;
import com.doapps.petservices.Utils.Utils;
import com.squareup.picasso.Picasso;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by NriKe on 05/06/2017.
 */

public class PostAdapter extends RecyclerView.Adapter<PostAdapter.MyHolder>{

    Context context;
    List<PostResponse> data;
    private final LayoutInflater inflater;
    private PreferenceManager manager;
    private RecyclerView rv;

    public PostAdapter(Context context, List<PostResponse> data, RecyclerView rv) {
        this.context = context;
        this.data = data;
        inflater = LayoutInflater.from(context);
        manager = PreferenceManager.getInstance(context);
        this.rv = rv;
    }

    public void addPost(PostResponse post){
        data.add(post);
        notifyDataSetChanged();
        rv.scrollToPosition(data.size()-1);
    }

    @Override
    public MyHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        int layout = R.layout.row_publicacion;
        View view = inflater.inflate(layout, parent, false);
        return new PostAdapter.MyHolder(view);
    }

    @Override
    public void onBindViewHolder(final MyHolder holder, final int position) {
        boolean isLikedByMe = false;

        holder.tv_name.setText(data.get(position).getName());

        holder.tv_date.setText(data.get(position).getDate());

        if(data.get(position).getLike().size() != 0){
            holder.tv_like.setText(String.valueOf(data.get(position).getLike().size()));
        }else{
            holder.tv_like.setText("0");
        }

        if(data.get(position).getPicture() != null && !data.get(position).getPicture().isEmpty()){
            Picasso.with(context).load(data.get(position).getPicture()).into(holder.iv_profile);
        }else{
            holder.iv_profile.setImageDrawable(ContextCompat.getDrawable(context,R.drawable.empty));
        }

        holder.tv_post.setText(data.get(position).getDescription());

        if(data.get(position).getImage() != null && data.get(position).getImage().getUrl() != null){
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

        for (String s : data.get(position).getLike()){
            if(s.equals(manager.getUserId())){
                isLikedByMe = true;
                break;
            }
        }

        if(!isLikedByMe){
            setLikeListener(holder.tv_like,position);
        }else{
            setDislikeListener(holder.tv_like,position);
        }
    }

    private void setLikeListener(final TextView tv_like, final int position){
        tv_like.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Map<String, String> map = new HashMap<>();
                map.put("idPublicacion", data.get(position).getId());
                map.put("idUsuario", manager.getUserId());
                Log.e("like","like");
                Call<Void> call = PetServicesApplication.getInstance().getServices().like(map);

                call.enqueue(new Callback<Void>() {
                    @Override
                    public void onResponse(Call<Void> call, Response<Void> response) {
                        if(response.isSuccessful()){
                            String count = tv_like.getText().toString();
                            int new_count = Integer.parseInt(count) + 1;
                            tv_like.setText(String.valueOf(new_count));
                            setDislikeListener(tv_like,position);
                        }
                    }

                    @Override
                    public void onFailure(Call<Void> call, Throwable t) {
                        Utils.showToastInternalServerError(context);
                    }
                });
            }
        });
    }

    private void setDislikeListener(final TextView tv_like, final int position){
        tv_like.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Map<String, String> map = new HashMap<>();
                map.put("idPublicacion", data.get(position).getId());
                map.put("idUsuario", manager.getUserId());
                Log.e("dislike","dislike");
                Call<Void> call = PetServicesApplication.getInstance().getServices().dislike(map);

                call.enqueue(new Callback<Void>() {
                    @Override
                    public void onResponse(Call<Void> call, Response<Void> response) {
                        if(response.isSuccessful()){
                            String count = tv_like.getText().toString();
                            int new_count = Integer.parseInt(count) - 1;
                            tv_like.setText(String.valueOf(new_count));
                            setLikeListener(tv_like,position);
                        }
                    }

                    @Override
                    public void onFailure(Call<Void> call, Throwable t) {
                        Utils.showToastInternalServerError(context);
                    }
                });
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
        TextView tv_like;

        public MyHolder(View itemView) {
            super(itemView);
            iv_photo = (ImageView) itemView.findViewById(R.id.iv_photo);
            iv_profile = (ImageView) itemView.findViewById(R.id.iv_profile);
            tv_name = (TextView) itemView.findViewById(R.id.tv_name);
            tv_post = (TextView) itemView.findViewById(R.id.tv_post);
            tv_date = (TextView) itemView.findViewById(R.id.tv_date);
            tv_like = (TextView) itemView.findViewById(R.id.tv_like);
        }
    }
}
