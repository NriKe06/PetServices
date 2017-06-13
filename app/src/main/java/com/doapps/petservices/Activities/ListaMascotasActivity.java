package com.doapps.petservices.Activities;

import android.content.Intent;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.doapps.petservices.R;

public class ListaMascotasActivity extends AppCompatActivity {

    RecyclerView rv_pets;
    TextView tv_empty;
    FloatingActionButton fab;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lista_mascotas);

        setupViews();
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
