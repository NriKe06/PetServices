package com.doapps.petservices.Activities;

import android.Manifest;
import android.app.Activity;
import android.app.Dialog;
import android.content.ContentResolver;
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
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.doapps.petservices.Network.Models.PetResponse;
import com.doapps.petservices.PetServicesApplication;
import com.doapps.petservices.R;
import com.doapps.petservices.Utils.Constants;
import com.doapps.petservices.Utils.PreferenceManager;
import com.doapps.petservices.Utils.Utils;

import java.io.File;
import java.io.FileOutputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.Date;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import okhttp3.internal.Util;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class CrearMascotaActivity extends AppCompatActivity {

    private EditText et_nombre;
    private EditText et_edad;
    private EditText et_raza;
    private EditText et_peso;
    private TextView tv_foto;
    private RelativeLayout rl_photo;
    private ImageView iv_photo;
    private ImageView iv_remove;
    private LinearLayout ll_container;
    private ProgressBar pb;

    private Button bt_finalizar;

    private PreferenceManager manager;

    private ArrayList<MultipartBody.Part> fotos;

    private File image_1 = null;

    private ContentResolver contentResolver;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_crear_mascota);

        contentResolver = getContentResolver();

        manager = PreferenceManager.getInstance(this);

        setupViews();
    }

    private void setupViews(){
        et_nombre = (EditText) findViewById(R.id.et_nombre);
        et_edad = (EditText) findViewById(R.id.et_edad);
        et_raza = (EditText) findViewById(R.id.et_raza);
        et_peso = (EditText) findViewById(R.id.et_peso);
        tv_foto = (TextView) findViewById(R.id.tv_foto);
        rl_photo = (RelativeLayout) findViewById(R.id.rl_photo);
        iv_photo = (ImageView) findViewById(R.id.iv_photo);
        iv_remove = (ImageView) findViewById(R.id.iv_remove);
        ll_container = (LinearLayout) findViewById(R.id.ll_container);
        pb = (ProgressBar) findViewById(R.id.pb);

        bt_finalizar = (Button) findViewById(R.id.bt_finalizar);

        bt_finalizar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                validateFields();
            }
        });

        tv_foto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDialogFotos();
            }
        });

        iv_remove.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                image_1 = null;
                rl_photo.setVisibility(View.GONE);
            }
        });
    }

    private void validateFields() {
        boolean cancel = false;

        et_nombre.setError(null);
        et_edad.setError(null);
        et_raza.setError(null);
        et_peso.setError(null);

        if(et_nombre.getText().toString().isEmpty()){
            cancel = true;
            et_nombre.setText("Campo Requerido.");
        }
        if(et_peso.getText().toString().isEmpty()){
            cancel = true;
            et_peso.setText("Campo Requerido.");
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
            createPetRequest();
        }
    }

    private void createPetRequest(){
        pb.setVisibility(View.VISIBLE);
        ll_container.setVisibility(View.GONE);

        fotos = new ArrayList<>();
        if(image_1 != null){
            final RequestBody requestFile = RequestBody.create(MediaType.parse("multipart/form-data"), image_1);
            MultipartBody.Part body = MultipartBody.Part.createFormData("image", image_1.getName(), requestFile);
            fotos.add(0,body);
        }
        Call<PetResponse> call = PetServicesApplication.getInstance().getServices().createPet(RequestBody.create(MediaType.parse("text/plain"),et_nombre.getText().toString()),
                RequestBody.create(MediaType.parse("text/plain"),et_raza.getText().toString()),
                RequestBody.create(MediaType.parse("text/plain"),et_edad.getText().toString()),
                RequestBody.create(MediaType.parse("text/plain"),et_peso.getText().toString()),
                RequestBody.create(MediaType.parse("text/plain"),manager.getUserId()),
                fotos.size() != 0 ? fotos : null);

        call.enqueue(new Callback<PetResponse>() {
            @Override
            public void onResponse(Call<PetResponse> call, Response<PetResponse> response) {
                if(response.isSuccessful()){
                    Intent i = new Intent(CrearMascotaActivity.this,HomeActivity.class);
                    i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                    startActivity(i);
                }
                pb.setVisibility(View.GONE);
                ll_container.setVisibility(View.VISIBLE);
            }

            @Override
            public void onFailure(Call<PetResponse> call, Throwable t) {
                pb.setVisibility(View.GONE);
                ll_container.setVisibility(View.VISIBLE);
                Utils.showToastInternalServerError(CrearMascotaActivity.this);
            }
        });
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if(resultCode == Activity.RESULT_OK){
            //when camera was openned
            if(requestCode == Constants.CAMERA_REQUEST){
                Bitmap photo = (Bitmap) data.getExtras().get("data");
                image_1 = persistImage(photo);
                setFotoIntoIv(photo);
            }
            //when gallery was openned
            if(requestCode == Constants.REQUEST_IMAGE_GALLERY){
                Uri selectedImageUri = data.getData();
                String picturePath = getRealPathFromURI(contentResolver, selectedImageUri);
                Bitmap photo = BitmapFactory.decodeFile(picturePath);
                image_1 = persistImage(photo);
                setFotoIntoIv(photo);
            }
        }
    }

    private void setFotoIntoIv(Bitmap photo) {
        iv_photo.setImageBitmap(photo);
        rl_photo.setVisibility(View.VISIBLE);
    }

    public static String getRealPathFromURI(ContentResolver contentResolver, Uri uri) {
        Cursor cursor = contentResolver.query(uri, null, null, null, null);
        if (cursor != null) {
            cursor.moveToFirst();
            int indexColumn = cursor.getColumnIndex(MediaStore.Images.ImageColumns.DATA);
            return cursor.getString(indexColumn);
        }
        return null;
    }

    //save imagen into file
    private File persistImage(Bitmap bitmap) {
        File filesDir = getApplicationContext().getFilesDir();
        final String name = DateFormat.format("yyyy-MM-dd_hhmmss", new Date()).toString();
        File imageFile = new File(filesDir, name + ".jpg");

        OutputStream os;
        try {
            os = new FileOutputStream(imageFile);
            bitmap.compress(Bitmap.CompressFormat.JPEG, 100, os);
            os.flush();
            os.close();
            return imageFile;
        } catch (Exception e) {
            Log.e(getClass().getSimpleName(), "Error writing bitmap", e);
            return null;
        }
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

                if (ContextCompat.checkSelfPermission(CrearMascotaActivity.this, Manifest.permission.READ_EXTERNAL_STORAGE)
                        != PackageManager.PERMISSION_GRANTED) {
                    if (ActivityCompat.shouldShowRequestPermissionRationale(CrearMascotaActivity.this,
                            Manifest.permission.READ_EXTERNAL_STORAGE)) {

                        // Show an expanation to the user *asynchronously* -- don't block
                        // this thread waiting for the user's response! After the user
                        // sees the explanation, try again to request the permission.

                    } else {

                        // No explanation needed, we can request the permission.

                        ActivityCompat.requestPermissions(CrearMascotaActivity.this,
                                new String[]{Manifest.permission.READ_EXTERNAL_STORAGE},2);

                        // MY_PERMISSIONS_REQUEST_READ_CONTACTS is an
                        // app-defined int constant. The callback method gets the
                        // result of the request.
                    }
                }else{
                    Intent photoPickerIntent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                    if (photoPickerIntent.resolveActivity(CrearMascotaActivity.this.getPackageManager()) != null) {
                        photoPickerIntent.setType("image/*");
                        startActivityForResult(photoPickerIntent, Constants.REQUEST_IMAGE_GALLERY);
                    }
                }
            }
        });
        dialog.show();
    }

}
