package com.doapps.petservices.Activities;

import android.Manifest;
import android.app.Activity;
import android.app.Dialog;
import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.provider.MediaStore;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.format.DateFormat;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.doapps.petservices.Network.Models.PetResponse;
import com.doapps.petservices.Network.Models.SignUpResponse;
import com.doapps.petservices.Network.Models.UserData;
import com.doapps.petservices.PetServicesApplication;
import com.doapps.petservices.R;
import com.doapps.petservices.Utils.Constants;
import com.doapps.petservices.Utils.PreferenceManager;
import com.doapps.petservices.Utils.Utils;
import com.facebook.CallbackManager;
import com.facebook.login.widget.LoginButton;
import com.squareup.picasso.Picasso;

import java.io.File;
import java.io.FileOutputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class UpdateAccount extends AppCompatActivity {

    @BindView(R.id.iv_photo)
    ImageView iv_photo;
    @BindView(R.id.et_nombre)
    EditText et_nombre;
    @BindView(R.id.et_apellidos)
    EditText et_apellidos;
    @BindView(R.id.et_phone)
    EditText et_phone;
    @BindView(R.id.ll_container)
    LinearLayout ll_container;
    @BindView(R.id.pb)
    ProgressBar pb;

    private PreferenceManager manager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_account);
        ButterKnife.bind(this);
        manager = PreferenceManager.getInstance(this);
        getUserData();
    }

    private void getUserData() {
        Call<UserData> call = PetServicesApplication.getInstance().getServices().getUserData(manager.getUserId());

        call.enqueue(new Callback<UserData>() {
            @Override
            public void onResponse(Call<UserData> call, Response<UserData> response) {
                if(response.isSuccessful()){
                    setupViews(response);
                }
            }

            @Override
            public void onFailure(Call<UserData> call, Throwable t) {

            }
        });
    }

    private void setupViews(Response<UserData> response) {
        et_nombre.setText(response.body().getName());
        et_apellidos.setText(response.body().getLast_name());
        et_phone.setText(response.body().getPhone());

        if(response.body().getPicture() != null && !response.body().getPicture().isEmpty()){
            String newUrl = response.body().getPicture().substring(0, 4) + "s" +response.body().getPicture().substring(4, response.body().getPicture().length());
            Picasso.with(this).load(newUrl).into(iv_photo);
        }else{
            iv_photo.setImageDrawable(ContextCompat.getDrawable(this,R.drawable.empty));
        }
    }

    @OnClick(R.id.bt_update)
    public void bt_update_on_click(){
        pb.setVisibility(View.VISIBLE);
        ll_container.setVisibility(View.GONE);

        Call<SignUpResponse> call = PetServicesApplication.getInstance().getServices().updateUser(manager.getUserId(),
               et_nombre.getText().toString(),
                et_apellidos.getText().toString(),
                et_phone.getText().toString());

        call.enqueue(new Callback<SignUpResponse>() {
            @Override
            public void onResponse(Call<SignUpResponse> call, Response<SignUpResponse> response) {
                if(response.isSuccessful()){
                    Utils.showToast(getApplicationContext(),"Usuario Actualizado");
                    manager.setName(response.body().getName());
                    manager.setLastName(response.body().getLast_name());
                    manager.setUserPhone(response.body().getPicture());
                    manager.setUserMail(response.body().getEmail());
                    manager.setUserPhone(response.body().getPhone());
                    finish();
                }
                pb.setVisibility(View.GONE);
                ll_container.setVisibility(View.VISIBLE);
            }

            @Override
            public void onFailure(Call<SignUpResponse> call, Throwable t) {
                pb.setVisibility(View.GONE);
                ll_container.setVisibility(View.VISIBLE);
            }
        });

        SignUpResponse user= new SignUpResponse();
        user.setName(et_nombre.getText().toString());
        user.setLast_name(et_apellidos.getText().toString());
        user.setPhone(et_phone.getText().toString());

    }
}
