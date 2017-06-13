package com.doapps.petservices.Activities;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.doapps.petservices.R;
import com.doapps.petservices.Utils.Constants;

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
                Intent i = new Intent(SeleccionCuentaActivity.this,CrearCuentaActivity.class);
                i.putExtra(Constants.SIGN_UP_PERSONA,true);
                startActivity(i);
            }
        });

        bt_empresa.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(SeleccionCuentaActivity.this,CrearCuentaActivity.class);
                i.putExtra(Constants.SIGN_UP_EMPRESA,true);
                startActivity(i);
            }
        });
    }
}
