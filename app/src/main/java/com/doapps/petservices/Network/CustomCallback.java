package com.doapps.petservices.Network;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by rubymobile on 05/05/2017.
 */

public class CustomCallback<T> implements Callback<T> {


    public CustomCallback() {
    }

    @Override
    public void onResponse(Call<T> call, Response<T> response) {

    }

    @Override
    public void onFailure(Call<T> call, Throwable t) {

    }
}