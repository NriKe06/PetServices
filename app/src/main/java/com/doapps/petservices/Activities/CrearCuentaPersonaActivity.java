package com.doapps.petservices.Activities;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.doapps.petservices.R;

public class CrearCuentaPersonaActivity extends AppCompatActivity {

    EditText et_nombre;
    EditText et_apellidos;
    EditText et_correo;
    EditText et_dni;
    EditText et_nacimiento;
    EditText et_telefono;
    EditText et_usuario;
    EditText et_password;

    Button bt_siguiente;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_crear_cuenta_persona);

        et_nombre = (EditText) findViewById(R.id.et_nombre);
        et_apellidos = (EditText) findViewById(R.id.et_apellidos);
        et_correo = (EditText) findViewById(R.id.et_correo);
        et_dni = (EditText) findViewById(R.id.et_dni);
        et_nacimiento = (EditText) findViewById(R.id.et_nacimiento);
        et_telefono = (EditText) findViewById(R.id.et_telefono);
        et_usuario = (EditText) findViewById(R.id.et_usuario);
        et_password = (EditText) findViewById(R.id.et_password);


        bt_siguiente = (Button) findViewById(R.id.bt_siguiente);

        bt_siguiente.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                validateFields();
            }
        });
    }

    private void validateFields() {
        boolean cancel = false;

        et_nombre.setError(null);
        et_apellidos.setError(null);
        et_correo.setError(null);
        et_dni.setError(null);
        et_nacimiento.setError(null);
        et_telefono.setError(null);
        et_usuario.setError(null);
        et_password.setError(null);

        if(et_nombre.getText().toString().isEmpty()){
            cancel = true;
            et_nombre.setError("Campo Requerido.");
        }
        if(et_apellidos.getText().toString().isEmpty()){
            cancel = true;
            et_apellidos.setError("Campo Requerido.");
        }
        if(et_correo.getText().toString().isEmpty()){
            cancel = true;
            et_correo.setError("Campo Requerido.");
        }
        if(et_dni.getText().toString().isEmpty()){
            cancel = true;
            et_dni.setError("Campo Requerido.");
        }
        if(et_nacimiento.getText().toString().isEmpty()){
            cancel = true;
            et_nacimiento.setError("Campo Requerido.");
        }
        if(et_telefono.getText().toString().isEmpty()){
            cancel = true;
            et_telefono.setError("Campo Requerido.");
        }
        if(et_usuario.getText().toString().isEmpty()){
            cancel = true;
            et_usuario.setError("Campo Requerido.");
        }
        if(et_password.getText().toString().isEmpty()){
            cancel = true;
            et_password.setError("Campo Requerido.");
        }

        if(!cancel){
            startActivity(new Intent(CrearCuentaPersonaActivity.this,CrearMascotaActivity.class));
        }
    }
}
