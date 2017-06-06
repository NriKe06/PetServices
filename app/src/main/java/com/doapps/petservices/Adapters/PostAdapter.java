package com.doapps.petservices.Adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.doapps.petservices.Models.Post;
import com.doapps.petservices.R;

import java.util.List;

/**
 * Created by NriKe on 05/06/2017.
 */

public class PostAdapter extends RecyclerView.Adapter<PostAdapter.MyHolder>{

    Context context;
    List<Post> data;
    private final LayoutInflater inflater;

    public PostAdapter(Context context, List<Post> data) {
        this.context = context;
        this.data = data;
        inflater = LayoutInflater.from(context);
    }

    @Override
    public MyHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        int layout = R.layout.row_publicacion;
        View view = inflater.inflate(layout, parent, false);
        return new PostAdapter.MyHolder(view);
    }

    @Override
    public void onBindViewHolder(MyHolder holder, int position) {
        holder.tv_name.setText("NAME TEST");
        holder.tv_post.setText("Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua. Ut enim ad minim veniam, quis nostrud exercitation ullamco laboris nisi ut aliquip ex ea commodo consequat. Duis aute irure dolor in reprehenderit in voluptate velit esse cillum dolore eu fugiat nulla pariatur. Excepteur sint occaecat cupidatat non proident, sunt in culpa qui officia deserunt mollit anim id est laborum.");
    }

    @Override
    public int getItemCount() {
        return 10;
    }

    public class MyHolder extends RecyclerView.ViewHolder{

        ImageView iv_user;
        TextView tv_name;
        TextView tv_post;

        public MyHolder(View itemView) {
            super(itemView);
            iv_user = (ImageView) itemView.findViewById(R.id.iv_user);
            tv_name = (TextView) itemView.findViewById(R.id.tv_name);
            tv_post = (TextView) itemView.findViewById(R.id.tv_post);
        }
    }
}
