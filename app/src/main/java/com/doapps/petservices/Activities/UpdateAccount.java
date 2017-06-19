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
    @BindView(R.id.tv_photo)
    TextView tv_photo;
    @BindView(R.id.et_nombre)
    EditText et_nombre;
    @BindView(R.id.et_apellidos)
    EditText et_apellidos;
    @BindView(R.id.et_email)
    EditText et_email;
    @BindView(R.id.et_phone)
    EditText et_phone;
    @BindView(R.id.ll_container)
    LinearLayout ll_container;
    @BindView(R.id.pb)
    ProgressBar pb;

    private PreferenceManager manager;

    private ArrayList<MultipartBody.Part> fotos;

    private File image_1 = null;

    private ContentResolver contentResolver;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_account);
        ButterKnife.bind(this);
        contentResolver = getContentResolver();
        manager = PreferenceManager.getInstance(this);
        setupViews();
    }

    private void setupViews() {
        et_nombre.setText(manager.getName());
        et_apellidos.setText(manager.getLastName());
        et_email.setText(manager.getUserMail());
        et_phone.setText(manager.getUserPhone());

        if(!manager.getUserPhoto().isEmpty()){
            Picasso.with(this).load(manager.getUserPhoto()).into(iv_photo);
        }
    }

    @OnClick(R.id.bt_update)
    public void bt_update_on_click(){
        pb.setVisibility(View.VISIBLE);
        ll_container.setVisibility(View.GONE);

        fotos = new ArrayList<>();
        if(image_1 != null){
            final RequestBody requestFile = RequestBody.create(MediaType.parse("multipart/form-data"), image_1);
            MultipartBody.Part body = MultipartBody.Part.createFormData("image", image_1.getName(), requestFile);
            fotos.add(0,body);
        }

        Map<String, String> map = new HashMap<>();
        map.put("id", manager.getUserId());

        Call<SignUpResponse> call = PetServicesApplication.getInstance().getServices().updateUser(map,RequestBody.create(MediaType.parse("text/plain"),et_nombre.getText().toString()),
                RequestBody.create(MediaType.parse("text/plain"),et_apellidos.getText().toString()),
                RequestBody.create(MediaType.parse("text/plain"),et_phone.getText().toString()),
                RequestBody.create(MediaType.parse("text/plain"),et_email.getText().toString()),
                RequestBody.create(MediaType.parse("text/plain"),manager.getUserId()),
                fotos.size() != 0 ? fotos : null);

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
        user.setEmail(et_email.getText().toString());

    }

    @OnClick(R.id.tv_photo)
    public void tv_photo_click(){
        showDialogFotos();
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if(resultCode == Activity.RESULT_OK){
            //when camera was openned
            if(requestCode == Constants.CAMERA_REQUEST){
                Bitmap photo = (Bitmap) data.getExtras().get("data");
                image_1 = Utils.persistImage(photo,getApplicationContext());
                setFotoIntoIv(photo);
            }
            //when gallery was openned
            if(requestCode == Constants.REQUEST_IMAGE_GALLERY){
                Uri selectedImageUri = data.getData();
                String picturePath = Utils.getRealPathFromURI(contentResolver, selectedImageUri);
                Bitmap photo = BitmapFactory.decodeFile(picturePath);
                image_1 = Utils.persistImage(photo,getApplicationContext());
                setFotoIntoIv(photo);
            }
        }
    }

    private void setFotoIntoIv(Bitmap photo) {
        iv_photo.setImageBitmap(photo);
    }

    private void showDialogFotos() {
        final Dialog dialog = new Dialog(this);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        //add custom dialog
        dialog.setContentView(R.layout.custom_uploadpic_dialog);
        LinearLayout ll_take_photo = (LinearLayout) dialog.findViewById(R.id.ll_take_photo);
        LinearLayout ll_phone_gallery = (LinearLayout) dialog.findViewById(R.id.ll_phone_gallery);
        //open camera
        ll_take_photo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
                Intent cameraIntent = new Intent(android.provider.MediaStore.ACTION_IMAGE_CAPTURE);
                startActivityForResult(cameraIntent, Constants.CAMERA_REQUEST);
            }
        });
        //open gallery, ask permisions if needed
        ll_phone_gallery.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();

                if (ContextCompat.checkSelfPermission(UpdateAccount.this, Manifest.permission.READ_EXTERNAL_STORAGE)
                        != PackageManager.PERMISSION_GRANTED) {
                    if (ActivityCompat.shouldShowRequestPermissionRationale(UpdateAccount.this,
                            Manifest.permission.READ_EXTERNAL_STORAGE)) {

                    } else {

                        ActivityCompat.requestPermissions(UpdateAccount.this,
                                new String[]{Manifest.permission.READ_EXTERNAL_STORAGE},2);
                    }
                }else{
                    Intent photoPickerIntent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                    if (photoPickerIntent.resolveActivity(UpdateAccount.this.getPackageManager()) != null) {
                        photoPickerIntent.setType("image/*");
                        startActivityForResult(photoPickerIntent, Constants.REQUEST_IMAGE_GALLERY);
                    }
                }
            }
        });
        dialog.show();
    }
}
