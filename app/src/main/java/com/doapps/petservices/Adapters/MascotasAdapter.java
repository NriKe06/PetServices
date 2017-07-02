package com.doapps.petservices.Adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.doapps.petservices.Network.Models.Mascota;
import com.doapps.petservices.R;

import java.util.ArrayList;

/**
 * Created by NriKe on 02/07/2017.
 */

public class MascotasAdapter extends RecyclerView.Adapter<MascotasAdapter.MyHolder>{

    private final LayoutInflater inflater;

    Context context;
    ArrayList<Mascota> data;


    public MascotasAdapter(ArrayList<Mascota> data, Context context) {
        this.data = data;
        this.context = context;
        inflater = LayoutInflater.from(context);
    }

    @Override
    public MyHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        int layout = R.layout.row_mascota;
        View view = inflater.inflate(layout, parent, false);
        return new MascotasAdapter.MyHolder(view);
    }

    @Override
    public void onBindViewHolder(MyHolder holder, int position) {
        holder.tv_name.setText(data.get(position).getName_pet());
        holder.tv_race.setText(data.get(position).getRace());
        holder.tv_age.setText(data.get(position).getAge());
        holder.tv_weight.setText(data.get(position).getWeight());
    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    public class MyHolder extends RecyclerView.ViewHolder{

        TextView tv_name;
        TextView tv_race;
        TextView tv_age;
        TextView tv_weight;

        public MyHolder(View itemView) {
            super(itemView);

            tv_name = (TextView) itemView.findViewById(R.id.tv_name);
            tv_race = (TextView) itemView.findViewById(R.id.tv_race);
            tv_age = (TextView) itemView.findViewById(R.id.tv_age);
            tv_weight = (TextView) itemView.findViewById(R.id.tv_weight);
        }
    }
}
