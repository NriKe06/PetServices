package com.doapps.petservices.Activities;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.InputFilter;
import android.text.InputType;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.doapps.petservices.Network.Models.SignUpBodyClient;
import com.doapps.petservices.Network.Models.SignUpResponse;
import com.doapps.petservices.PetServicesApplication;
import com.doapps.petservices.R;
import com.doapps.petservices.Utils.Constants;
import com.doapps.petservices.Utils.PreferenceManager;
import com.doapps.petservices.Utils.Utils;
import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.FacebookSdk;
import com.facebook.login.LoginResult;
import com.facebook.login.widget.LoginButton;

import okhttp3.internal.Util;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class CrearCuentaActivity extends AppCompatActivity {

    private EditText et_nombre;
    private EditText et_apellidos;
    private EditText et_password;
    private EditText et_email;
    private EditText et_phone;
    private Button bt_siguiente;
    private LoginButton bt_fb;
    private CallbackManager callbackManager;
    private LinearLayout ll_container;
    private ProgressBar pb;

    private PreferenceManager manager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        FacebookSdk.sdkInitialize(getApplicationContext());
        callbackManager = CallbackManager.Factory.create();

        setContentView(R.layout.activity_crear_cuenta_persona);

        manager = PreferenceManager.getInstance(this);

        setupViews();
        getExtras();
    }

    private void getExtras() {
        if(getIntent().getBooleanExtra(Constants.SIGN_UP_PERSONA,false)){
            et_nombre.setHint("Nombres");
            et_apellidos.setHint("Apellidos");
        }else if (getIntent().getBooleanExtra(Constants.SIGN_UP_EMPRESA,false)){
            et_nombre.setHint("Razón Social");
            et_apellidos.setHint("RUC");
            int maxLength = 11;
            InputFilter[] fArray = new InputFilter[1];
            fArray[0] = new InputFilter.LengthFilter(maxLength);
            et_apellidos.setFilters(fArray);
            et_apellidos.setInputType(InputType.TYPE_CLASS_NUMBER);
        }else{
            finish();
        }
    }

    private void setupViews() {
        et_nombre = (EditText) findViewById(R.id.et_nombre);
        et_phone = (EditText) findViewById(R.id.et_phone);
        et_apellidos = (EditText) findViewById(R.id.et_apellidos);
        et_password = (EditText) findViewById(R.id.et_password);
        et_email = (EditText) findViewById(R.id.et_email);
        bt_fb = (LoginButton) findViewById(R.id.bt_fb);
        ll_container = (LinearLayout) findViewById(R.id.ll_container);
        pb = (ProgressBar) findViewById(R.id.pb);

        bt_siguiente = (Button) findViewById(R.id.bt_siguiente);

        bt_siguiente.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                validateFields();
            }
        });

        bt_fb.registerCallback(callbackManager, new FacebookCallback<LoginResult>() {
            @Override
            public void onSuccess(LoginResult loginResult) {

            }

            @Override
            public void onCancel() {

            }

            @Override
            public void onError(FacebookException e) {

            }
        });
    }

    private void validateFields() {
        boolean cancel = false;

        et_nombre.setError(null);
        et_apellidos.setError(null);
        et_password.setError(null);
        et_email.setError(null);
        et_phone.setError(null);

        if(et_nombre.getText().toString().isEmpty()){
            cancel = true;
            et_nombre.setError("Campo Requerido.");
        }
        if(et_apellidos.getText().toString().isEmpty()){
            cancel = true;
            et_apellidos.setError("Campo Requerido.");
        }
        if(et_password.getText().toString().isEmpty()){
            cancel = true;
            et_password.setError("Campo Requerido.");
        }
        if(et_email.getText().toString().isEmpty()){
            cancel = true;
            et_email.setError("Campo Requerido.");
        }
        if(et_phone.getText().toString().length() < 9){
            cancel = true;
            et_phone.setError("Teléfono Inválido.");
        }
        if(et_phone.getText().toString().isEmpty()){
            cancel = true;
            et_phone.setError("Campo Requerido.");
        }


        if(!cancel){
            if(getIntent().getBooleanExtra(Constants.SIGN_UP_PERSONA,false)){
                signUpRequestClient();
            }else if(getIntent().getBooleanExtra(Constants.SIGN_UP_EMPRESA,false)){
                signUpRequestEmpresa();
            }
        }
    }

    private void signUpRequestClient() {
        pb.setVisibility(View.VISIBLE);
        ll_container.setVisibility(View.GONE);

        SignUpBodyClient signUpBodyClient = new SignUpBodyClient();

        signUpBodyClient.setName(et_nombre.getText().toString());
        signUpBodyClient.setLast_name(et_apellidos.getText().toString());
        signUpBodyClient.setEmail(et_email.getText().toString());
        signUpBodyClient.setPassword(et_password.getText().toString());
        signUpBodyClient.setPhone(et_phone.getText().toString());

        Call<SignUpResponse> call = PetServicesApplication.getInstance().getServices().signUpClient(signUpBodyClient);

        call.enqueue(new Callback<SignUpResponse>() {
            @Override
            public void onResponse(Call<SignUpResponse> call, Response<SignUpResponse> response) {
                if(response.isSuccessful()){
                    manager.setUserId(response.body().getId());
                    manager.setName(response.body().getName());
                    manager.setLastName(response.body().getLast_name());
                    manager.setUserName(response.body().getUsername());
                    startActivity(new Intent(CrearCuentaActivity.this,CrearMascotaActivity.class));
                    finish();
                }
                pb.setVisibility(View.GONE);
                ll_container.setVisibility(View.VISIBLE);
            }

            @Override
            public void onFailure(Call<SignUpResponse> call, Throwable t) {
                pb.setVisibility(View.GONE);
                ll_container.setVisibility(View.VISIBLE);
                Utils.showToastInternalServerError(CrearCuentaActivity.this);
            }
        });
    }

    private void signUpRequestEmpresa(){

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        callbackManager.onActivityResult(requestCode, resultCode, data);
    }
}
