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
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.doapps.petservices.Network.Models.PetResponse;
import com.doapps.petservices.Network.Models.PostResponse;
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
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class CrearPublicacionCliente extends AppCompatActivity {

    private Spinner sp_tipo;
    private EditText et_descripcion;
    private RelativeLayout rl_photo;
    private ImageView iv_photo;
    private ImageView iv_remove;
    private Button bt_foto;
    private Button bt_publicar;
    private TextView tv_tipo_error;
    private LinearLayout ll_container;
    private ProgressBar pb;

    private PreferenceManager manager;

    private ArrayList<MultipartBody.Part> fotos;

    private File image_1 = null;

    private ContentResolver contentResolver;

    private String[] types;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_crear_publicacion_cliente);

        contentResolver = getContentResolver();

        manager = PreferenceManager.getInstance(this);

        types = getResources().getStringArray(R.array.sp_tipo_post);

        setupViews();
    }

    private void setupViews() {
        sp_tipo = (Spinner) findViewById(R.id.sp_tipo);
        et_descripcion = (EditText) findViewById(R.id.et_descripcion);
        rl_photo = (RelativeLayout) findViewById(R.id.rl_photo);
        iv_photo = (ImageView) findViewById(R.id.iv_photo);
        iv_remove = (ImageView) findViewById(R.id.iv_remove);
        bt_foto = (Button) findViewById(R.id.bt_foto);
        bt_publicar = (Button) findViewById(R.id.bt_publicar);
        tv_tipo_error = (TextView) findViewById(R.id.tv_tipo_error);
        ll_container = (LinearLayout) findViewById(R.id.ll_container);
        pb = (ProgressBar) findViewById(R.id.pb);

        setupSpinner();

        bt_foto.setOnClickListener(new View.OnClickListener() {
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
                bt_foto.setVisibility(View.VISIBLE);
            }
        });

        bt_publicar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                validateFields();
            }
        });
    }

    private void setupSpinner() {
        ArrayAdapter<String> adapter;
        adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, types);
        sp_tipo.setAdapter(adapter);
    }

    private void validateFields() {
        boolean cancel = false;
        et_descripcion.setError(null);
        tv_tipo_error.setVisibility(View.GONE);

        if (et_descripcion.getText().toString().isEmpty()) {
            cancel = true;
            et_descripcion.setError("Campo Requerido.");
        }
        if (sp_tipo.getSelectedItem().toString().equals(types[0])) {
            cancel = true;
            tv_tipo_error.setVisibility(View.VISIBLE);
        }

        if (!cancel) {
            createPost();
        }
    }

    private void createPost() {
        pb.setVisibility(View.VISIBLE);
        ll_container.setVisibility(View.GONE);

        fotos = new ArrayList<>();

        if (image_1 != null) {
            final RequestBody requestFile = RequestBody.create(MediaType.parse("multipart/form-data"), image_1);
            MultipartBody.Part body = MultipartBody.Part.createFormData("image", image_1.getName(), requestFile);
            fotos.add(0, body);
        }

        Call<PostResponse> call = PetServicesApplication.getInstance().getServices().createPost(RequestBody.create(MediaType.parse("text/plain"), et_descripcion.getText().toString()),
                RequestBody.create(MediaType.parse("text/plain"), sp_tipo.getSelectedItem().toString()),
                RequestBody.create(MediaType.parse("text/plain"), manager.getUserId()),
                fotos.size() != 0 ? fotos : null);

        call.enqueue(new Callback<PostResponse>() {
            @Override
            public void onResponse(Call<PostResponse> call, Response<PostResponse> response) {
                if(response.isSuccessful()){
                    Toast.makeText(CrearPublicacionCliente.this,"Publicación creada con éxito.",Toast.LENGTH_SHORT).show();

                    PostResponse postResponse = response.body();

                    Intent result = new Intent();
                    result.putExtra(Constants.EXTRA_POST_DESCRIPTION,postResponse.getDescription());
                    result.putExtra(Constants.EXTRA_POST_TYPE,postResponse.getType());
                    result.putExtra(Constants.EXTRA_POST_URL,postResponse.getImage().getUrl());
                    result.putExtra(Constants.EXTRA_POST_USER_ID,postResponse.getUserId());
                    result.putExtra(Constants.EXTRA_POST_DATE,postResponse.getDate());
                    setResult(RESULT_OK,result);
                    finish();
                }
                pb.setVisibility(View.GONE);
                ll_container.setVisibility(View.VISIBLE);
            }

            @Override
            public void onFailure(Call<PostResponse> call, Throwable t) {
                pb.setVisibility(View.GONE);
                ll_container.setVisibility(View.VISIBLE);
                Utils.showToastInternalServerError(CrearPublicacionCliente.this);
            }
        });
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode == Activity.RESULT_OK) {
            bt_foto.setVisibility(View.GONE);
            //when camera was openned
            if (requestCode == Constants.CAMERA_REQUEST) {
                Bitmap photo = (Bitmap) data.getExtras().get("data");
                image_1 = Utils.persistImage(photo,getApplicationContext());
                setFotoIntoIv(photo);
            }
            //when gallery was openned
            if (requestCode == Constants.REQUEST_IMAGE_GALLERY) {
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
        rl_photo.setVisibility(View.VISIBLE);
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

                if (ContextCompat.checkSelfPermission(CrearPublicacionCliente.this, Manifest.permission.READ_EXTERNAL_STORAGE)
                        != PackageManager.PERMISSION_GRANTED) {
                    if (ActivityCompat.shouldShowRequestPermissionRationale(CrearPublicacionCliente.this,
                            Manifest.permission.READ_EXTERNAL_STORAGE)) {

                        // Show an expanation to the user *asynchronously* -- don't block
                        // this thread waiting for the user's response! After the user
                        // sees the explanation, try again to request the permission.

                    } else {

                        // No explanation needed, we can request the permission.

                        ActivityCompat.requestPermissions(CrearPublicacionCliente.this,
                                new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, 2);

                        // MY_PERMISSIONS_REQUEST_READ_CONTACTS is an
                        // app-defined int constant. The callback method gets the
                        // result of the request.
                    }
                } else {
                    Intent photoPickerIntent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                    if (photoPickerIntent.resolveActivity(CrearPublicacionCliente.this.getPackageManager()) != null) {
                        photoPickerIntent.setType("image/*");
                        startActivityForResult(photoPickerIntent, Constants.REQUEST_IMAGE_GALLERY);
                    }
                }
            }
        });
        dialog.show();
    }

}
