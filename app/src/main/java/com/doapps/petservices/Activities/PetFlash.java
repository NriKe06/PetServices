package com.doapps.petservices.Activities;

import android.content.Intent;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.doapps.petservices.R;
import com.doapps.petservices.Utils.PreferenceManager;

public class PetFlash extends AppCompatActivity {

    PreferenceManager manager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pet_flash);

        manager = PreferenceManager.getInstance(this);

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                if(manager.getUserId().isEmpty()){
                    Intent intent = new Intent(PetFlash.this,LoginActivity.class);
                    startActivity(intent);
                    finish();
                }else{
                    Intent intent = new Intent(PetFlash.this,HomeActivity.class);
                    startActivity(intent);
                    finish();
                }
            }
        },2000);
    }
}
