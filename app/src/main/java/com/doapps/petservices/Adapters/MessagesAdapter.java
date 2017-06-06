package com.doapps.petservices.Adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.doapps.petservices.Models.Message;
import com.doapps.petservices.R;

import java.util.List;

/**
 * Created by NriKe on 05/06/2017.
 */

public class MessagesAdapter extends RecyclerView.Adapter<MessagesAdapter.MyHolder>{

    Context context;
    List<Message> data;
    private final LayoutInflater inflater;

    public MessagesAdapter(Context context, List<Message> data) {
        this.context = context;
        this.data = data;
        inflater = LayoutInflater.from(context);
    }

    @Override
    public MyHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        int layout = R.layout.row_message;
        View view = inflater.inflate(layout, parent, false);
        return new MyHolder(view);
    }

    @Override
    public void onBindViewHolder(MyHolder holder, int position) {
        holder.tv_message.setText("test");
        holder.tv_username.setText("RONALD");
    }

    @Override
    public int getItemCount() {
        return 10;
    }

    public class MyHolder extends RecyclerView.ViewHolder{

        TextView tv_username;
        TextView tv_message;

        public MyHolder(View itemView) {
            super(itemView);
            tv_username = (TextView) itemView.findViewById(R.id.tv_username);
            tv_message = (TextView) itemView.findViewById(R.id.tv_message);
        }
    }
}
