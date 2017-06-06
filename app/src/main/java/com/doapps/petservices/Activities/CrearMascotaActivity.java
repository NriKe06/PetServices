package com.doapps.petservices.Activities;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.doapps.petservices.R;

public class CrearMascotaActivity extends AppCompatActivity {

    EditText et_nombre;
    EditText et_edad;
    EditText et_raza;
    TextView tv_foto;

    Button bt_finalizar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_crear_mascota);

        et_nombre = (EditText) findViewById(R.id.et_nombre);
        et_edad = (EditText) findViewById(R.id.et_edad);
        et_raza = (EditText) findViewById(R.id.et_raza);
        tv_foto = (TextView) findViewById(R.id.tv_foto);

        bt_finalizar = (Button) findViewById(R.id.bt_finalizar);

        bt_finalizar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                validateFields();
            }
        });
    }

    private void validateFields() {
        boolean cancel = false;

        et_nombre.setError(null);
        et_edad.setError(null);
        et_raza.setError(null);

        if(et_nombre.getText().toString().isEmpty()){
            cancel = true;
            et_nombre.setText("Campo Requerido.");
        }
        if(et_edad.getText().toString().isEmpty()){
            cancel = true;
            et_edad.setText("Campo Requerido.");
        }
        if(et_raza.getText().toString().isEmpty()){
            cancel = true;
            et_raza.setText("Campo Requerido.");
        }

        if(!cancel){
            Intent i = new Intent(CrearMascotaActivity.this,HomeActivity.class);
            i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(i);
        }
    }
}
