package com.doapps.petservices.Network;

import com.doapps.petservices.Network.Models.LoginBody;
import com.doapps.petservices.Network.Models.LoginResponse;
import com.doapps.petservices.Network.Models.Mascota;
import com.doapps.petservices.Network.Models.PetResponse;
import com.doapps.petservices.Network.Models.PostResponse;
import com.doapps.petservices.Network.Models.SignUpBodyClient;
import com.doapps.petservices.Network.Models.SignUpResponse;
import com.doapps.petservices.Network.Models.UserData;

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
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface CustomService {
    @GET(Urls.USER_DATA)
    Call<UserData> getUserData(@Path("id") String id);

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
            , @Part("weigth") RequestBody documentType
            , @Part("userid") RequestBody documentNumber
            , @Part ArrayList<MultipartBody.Part> image);

    @Multipart
    @POST(Urls.CREATE_POST)
    Call<PostResponse> createPost(@Part("description") RequestBody description
            , @Part("type") RequestBody type
            , @Part("userid") RequestBody userId
            , @Part ArrayList<MultipartBody.Part> image);

    @GET(Urls.GET_USER_POST)
    Call<ArrayList<PostResponse>> getUserPosts(@Query("idUser") String id);

    @GET(Urls.GET_ALL_POST)
    Call<ArrayList<PostResponse>> getAllPost();

    @FormUrlEncoded
    @POST(Urls.UPDATE_USER)
    Call<SignUpResponse> updateUser(@Query("id") String id,
                                    @Field("name") String firstname,
                                    @Field("last_name") String lastname,
                                    @Field("phone") String secondSurname);

    @POST(Urls.LIKE)
    Call<Void> like(@HeaderMap Map<String, String> headers);

    @POST(Urls.DISLIKE)
    Call<Void> dislike(@HeaderMap Map<String, String> headers);

    @GET(Urls.FILTER_POST)
    Call<ArrayList<PostResponse>> filterPost(@HeaderMap Map<String, String> headers);

    @GET(Urls.MASCOTAS)
    Call<ArrayList<Mascota>> getMascotas(@Path("id") String id);
}
