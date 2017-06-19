package com.doapps.petservices.Activities;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ImageView;

import com.doapps.petservices.R;
import com.doapps.petservices.Utils.Constants;
import com.squareup.picasso.Picasso;

public class PhotoActivity extends AppCompatActivity {

    private ImageView iv_photo;

    private String url;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_photo);

        url = getIntent().getStringExtra(Constants.EXTRA_PHOTO);

        iv_photo = (ImageView) findViewById(R.id.iv_photo);

        Picasso.with(this).load(url).into(iv_photo);
    }
}
