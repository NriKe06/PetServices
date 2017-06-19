package com.doapps.petservices.Network;

import com.doapps.petservices.Network.Models.LoginBody;
import com.doapps.petservices.Network.Models.LoginResponse;
import com.doapps.petservices.Network.Models.PetResponse;
import com.doapps.petservices.Network.Models.PostResponse;
import com.doapps.petservices.Network.Models.SignUpBodyClient;
import com.doapps.petservices.Network.Models.SignUpResponse;

import java.util.ArrayList;
import java.util.Map;

import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.HeaderMap;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Part;

public interface CustomService {
    @POST(Urls.LOGIN)
    Call<LoginResponse> login(@Body LoginBody loginBody);

    @POST(Urls.LOGIN_FB)
    Call<LoginResponse> loginFb(@HeaderMap Map<String, String> headers);

    @POST(Urls.USER_SIGN_UP)
    Call<SignUpResponse> signUpClient(@Body SignUpBodyClient signUpBodyClient);

    @Multipart
    @POST(Urls.CREATE_PET)
    Call<PetResponse> createPet(@Part("name_pet") RequestBody firstname
            , @Part("race") RequestBody lastname
            , @Part("age") RequestBody secondSurname
            , @Part("weight") RequestBody documentType
            , @Part("userId") RequestBody documentNumber
            , @Part ArrayList<MultipartBody.Part> image);

    @Multipart
    @POST(Urls.CREATE_POST)
    Call<PostResponse> createPost(@Part("description") RequestBody description
            , @Part("type") RequestBody type
            , @Part("userid") RequestBody userId
            , @Part ArrayList<MultipartBody.Part> image);

    @GET(Urls.GET_USER_POST)
    Call<ArrayList<PostResponse>> getUserPosts(@HeaderMap Map<String, String> headers);

    @GET(Urls.GET_USER_POST)
    Call<ArrayList<PostResponse>> getAllPost();

    @Multipart
    @PUT(Urls.UPDATE_USER)
    Call<SignUpResponse> updateUser(@HeaderMap Map<String, String> headers,
                                    @Part("name") RequestBody firstname,
                                    @Part("last_name") RequestBody lastname,
                                    @Part("phone") RequestBody secondSurname,
                                    @Part("email") RequestBody documentType,
                                    @Part("id") RequestBody documentNumber,
                                    @Part ArrayList<MultipartBody.Part> picture);

    @FormUrlEncoded
    @POST(Urls.LIKE)
    Call<Void> like(@Field("idPublicacion") String idPublicacion, @Field("idUsuario") String idUsuario);
}
