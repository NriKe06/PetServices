package com.doapps.petservices.Activities;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.Signature;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.doapps.petservices.Network.Models.LoginBody;
import com.doapps.petservices.Network.Models.LoginResponse;
import com.doapps.petservices.PetServicesApplication;
import com.doapps.petservices.R;
import com.doapps.petservices.Utils.PreferenceManager;
import com.doapps.petservices.Utils.Utils;
import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.FacebookSdk;
import com.facebook.login.LoginManager;
import com.facebook.login.LoginResult;
import com.facebook.login.widget.LoginButton;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.HashMap;
import java.util.Map;

import butterknife.ButterKnife;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LoginActivity extends AppCompatActivity {

    Button bt_login;
    TextView bt_sign_up;
    EditText et_user;
    EditText et_password;
    RelativeLayout rl_login;
    ProgressBar pb_login;
    TextInputLayout til_user;
    TextInputLayout til_password;

    private LoginButton bt_fb;

    private CallbackManager callbackManager;

    private PreferenceManager manager;

    private final String[] permissionsNeeded = new String[]{Manifest.permission.READ_EXTERNAL_STORAGE};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        FacebookSdk.sdkInitialize(getApplicationContext());
        callbackManager = CallbackManager.Factory.create();

        setContentView(R.layout.activity_login);
        ButterKnife.bind(this);

        Utils.askForPermission(this,permissionsNeeded,getString(R.string.ask_for_permission_sign_in));

        manager = PreferenceManager.getInstance(this);

        setupViews();

    }

    public void setupViews(){
        PackageInfo info;
        try {
            info = getPackageManager().getPackageInfo("com.doapps.petservices", PackageManager.GET_SIGNATURES);
            for (Signature signature : info.signatures) {
                MessageDigest md;
                md = MessageDigest.getInstance("SHA");
                md.update(signature.toByteArray());
                String something = new String(Base64.encode(md.digest(), 0));
                Log.e("hash key", something);
            }
        } catch (PackageManager.NameNotFoundException e1) {
            Log.e("name not found", e1.toString());
        } catch (NoSuchAlgorithmException e) {
            Log.e("no such an algorithm", e.toString());
        } catch (Exception e) {
            Log.e("exception", e.toString());
        }

        bt_login = (Button) findViewById(R.id.bt_login);
        bt_sign_up = (TextView) findViewById(R.id.bt_sign_up);
        et_user = (EditText) findViewById(R.id.et_user);
        et_password = (EditText) findViewById(R.id.et_password);
        rl_login = (RelativeLayout) findViewById(R.id.rl_login);
        pb_login = (ProgressBar) findViewById(R.id.pb_login);
        bt_fb = (LoginButton) findViewById(R.id.bt_fb);
        til_user = (TextInputLayout) findViewById(R.id.til_user);
        til_password = (TextInputLayout) findViewById(R.id.til_password);

        bt_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                boolean cancel = false;
                til_password.setErrorEnabled(false);
                til_user.setErrorEnabled(false);
                til_password.setError(null);
                til_user.setError(null);

                if(et_user.getText().toString().isEmpty()){
                    cancel = true;
                    til_user.setError("Campo requerido.");
                    til_user.setErrorEnabled(true);
                }

                if(et_password.getText().toString().isEmpty()){
                    cancel = true;
                    til_password.setError("Campo requerido.");
                    til_password.setErrorEnabled(true);
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

        bt_fb.registerCallback(callbackManager, new FacebookCallback<LoginResult>() {
            @Override
            public void onSuccess(LoginResult loginResult) {
                Log.e("token",loginResult.getAccessToken().getToken());

                Map<String, String> map = new HashMap<>();
                map.put("accessToken",loginResult.getAccessToken().getToken());

                Call<LoginResponse> call = PetServicesApplication.getInstance().getServices().loginFb(map);

                call.enqueue(new Callback<LoginResponse>() {
                    @Override
                    public void onResponse(Call<LoginResponse> call, Response<LoginResponse> response) {
                        if(response.isSuccessful()){
                            manager.setUserId(response.body().getId());
                            startActivity(new Intent(LoginActivity.this,HomeActivity.class));
                            finish();
                        }
                    }

                    @Override
                    public void onFailure(Call<LoginResponse> call, Throwable t) {

                    }
                });
            }

            @Override
            public void onCancel() {

            }

            @Override
            public void onError(FacebookException e) {

            }
        });
    }

    public void loginRequest(){
        pb_login.setVisibility(View.VISIBLE);
        rl_login.setVisibility(View.INVISIBLE);

        LoginBody loginBody = new LoginBody();
        loginBody.setEmail(et_user.getText().toString().trim());
        loginBody.setPassword(et_password.getText().toString());

        Call<LoginResponse> call = PetServicesApplication.getInstance().getServices().login(loginBody);

        call.enqueue(new Callback<LoginResponse>() {
            @Override
            public void onResponse(Call<LoginResponse> call, Response<LoginResponse> response) {
                if(response.isSuccessful()){
                    manager.setUserId(response.body().getUserId());
                    manager.setUserName(et_user.getText().toString());
                    startActivity(new Intent(LoginActivity.this,HomeActivity.class));
                    finish();
                }else{
                    LoginManager.getInstance().logOut();
                }
                pb_login.setVisibility(View.GONE);
                rl_login.setVisibility(View.VISIBLE);
            }

            @Override
            public void onFailure(Call<LoginResponse> call, Throwable t) {
                LoginManager.getInstance().logOut();
                Utils.showToastInternalServerError(LoginActivity.this);
                pb_login.setVisibility(View.GONE);
                rl_login.setVisibility(View.VISIBLE);
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        callbackManager.onActivityResult(requestCode, resultCode, data);
    }
}
