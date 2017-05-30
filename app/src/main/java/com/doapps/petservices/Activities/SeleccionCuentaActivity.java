package com.doapps.petservices.Activities;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.doapps.petservices.R;

public class SeleccionCuentaActivity extends AppCompatActivity {

    Button bt_persona;
    Button bt_empresa;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_crear_cuenta);
        bt_persona = (Button) findViewById(R.id.bt_persona);
        bt_empresa = (Button) findViewById(R.id.bt_empresa);

        bt_persona.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(SeleccionCuentaActivity.this,CrearCuentaPersonaActivity.class));
            }
        });

        bt_empresa.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(SeleccionCuentaActivity.this,CrearCuentaEmpresaActivity.class));
            }
        });
    }
}
