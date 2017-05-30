package com.doapps.petservices.Network;

import android.content.Context;

import java.io.IOException;

import okhttp3.Authenticator;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.Route;


public class RetrofitAuthenticator implements Authenticator {

    Context context;

    public RetrofitAuthenticator(Context context) {
        this.context = context;
    }

    @Override
    public Request authenticate(Route route, Response response) throws IOException {
        return null;
    }
}
