package com.doapps.petservices.Activities;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.doapps.petservices.Network.Models.LoginBody;
import com.doapps.petservices.Network.Models.LoginResponse;
import com.doapps.petservices.PetServicesApplication;
import com.doapps.petservices.R;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LoginActivity extends AppCompatActivity {

    Button bt_login;
    Button bt_sign_up;
    EditText et_email;
    EditText et_password;
    RelativeLayout rl_login;
    ProgressBar pb_login;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        bt_login = (Button) findViewById(R.id.bt_login);
        bt_sign_up = (Button) findViewById(R.id.bt_sign_up);
        et_email = (EditText) findViewById(R.id.et_email);
        et_password = (EditText) findViewById(R.id.et_password);
        rl_login = (RelativeLayout) findViewById(R.id.rl_login);
        pb_login = (ProgressBar) findViewById(R.id.pb_login);

        bt_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                boolean cancel = false;

                if(et_email.getText().toString().isEmpty()){
                    cancel = true;
                    et_email.setError("Campo requerido.");
                }

                if(et_password.getText().toString().isEmpty()){
                    cancel = true;
                    et_password.setError("Campo requerido.");
                }

                if(!cancel){
                    loginRequest();
                }



            }
        });

        bt_sign_up.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(LoginActivity.this,SeleccionCuentaActivity.class));
            }
        });
    }

    public void loginRequest(){
        pb_login.setVisibility(View.VISIBLE);
        rl_login.setVisibility(View.INVISIBLE);

        LoginBody loginBody = new LoginBody();
        loginBody.setEmail(et_email.getText().toString().trim());
        loginBody.setPassword(et_password.getText().toString());

        Call<LoginResponse> call = PetServicesApplication.getInstance().getServices().login(loginBody);

        call.enqueue(new Callback<LoginResponse>() {
            @Override
            public void onResponse(Call<LoginResponse> call, Response<LoginResponse> response) {
                if(response.isSuccessful()){
                    startActivity(new Intent(LoginActivity.this,HomeActivity.class));
                    finish();
                }
                pb_login.setVisibility(View.GONE);
                rl_login.setVisibility(View.VISIBLE);
            }

            @Override
            public void onFailure(Call<LoginResponse> call, Throwable t) {
                Toast.makeText(LoginActivity.this,"Internal server error.",Toast.LENGTH_SHORT).show();
                pb_login.setVisibility(View.GONE);
                rl_login.setVisibility(View.VISIBLE);
            }
        });
    }
}
