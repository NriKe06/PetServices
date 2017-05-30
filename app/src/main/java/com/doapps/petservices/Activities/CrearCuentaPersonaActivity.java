package com.doapps.petservices.Activities;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.doapps.petservices.R;

public class CrearCuentaPersonaActivity extends AppCompatActivity {

    Button bt_siguiente;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_crear_cuenta_persona);

        bt_siguiente = (Button) findViewById(R.id.bt_siguiente);

        bt_siguiente.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(CrearCuentaPersonaActivity.this,CrearMascotaActivity.class));
            }
        });
    }
}
