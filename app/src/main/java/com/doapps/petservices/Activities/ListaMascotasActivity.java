package com.doapps.petservices.Activities;

import android.content.Intent;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.doapps.petservices.Adapters.MascotasAdapter;
import com.doapps.petservices.Network.Models.Mascota;
import com.doapps.petservices.PetServicesApplication;
import com.doapps.petservices.R;
import com.doapps.petservices.Utils.PreferenceManager;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ListaMascotasActivity extends AppCompatActivity {

    RecyclerView rv_pets;
    TextView tv_empty;
    FloatingActionButton fab;

    PreferenceManager manager;

    MascotasAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lista_mascotas);

        manager = PreferenceManager.getInstance(this);

        setupViews();

        getMascotas();
    }

    private void getMascotas() {
        Call<ArrayList<Mascota>> call = PetServicesApplication.getInstance().getServices().getMascotas(manager.getUserId());

        call.enqueue(new Callback<ArrayList<Mascota>>() {
            @Override
            public void onResponse(Call<ArrayList<Mascota>> call, Response<ArrayList<Mascota>> response) {
                if(response.isSuccessful()){
                    setDataIntoRv(response.body());
                }
            }

            @Override
            public void onFailure(Call<ArrayList<Mascota>> call, Throwable t) {

            }
        });
    }

    private void setDataIntoRv(ArrayList<Mascota> body) {
        adapter = new MascotasAdapter(body,this);
        rv_pets.setLayoutManager(new LinearLayoutManager(this));
        rv_pets.setAdapter(adapter);
    }

    private void setupViews() {
        fab = (FloatingActionButton) findViewById(R.id.fab);
        tv_empty = (TextView) findViewById(R.id.tv_empty);
        rv_pets = (RecyclerView) findViewById(R.id.rv_pets);

        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(ListaMascotasActivity.this,CrearMascotaActivity.class));
            }
        });
    }
}
