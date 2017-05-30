package com.doapps.petservices.Network;

import com.doapps.petservices.Network.Models.LoginBody;
import com.doapps.petservices.Network.Models.LoginResponse;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;

public interface CustomService {
    @POST(Urls.LOGIN)
    Call<LoginResponse> login(@Body LoginBody loginBody);
}
