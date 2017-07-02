package com.doapps.petservices.Fragments;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.doapps.petservices.Activities.CrearPublicacionCliente;
import com.doapps.petservices.Activities.ListaMascotasActivity;
import com.doapps.petservices.Activities.UpdateAccount;
import com.doapps.petservices.Adapters.PostAdapter;
import com.doapps.petservices.Models.Post;
import com.doapps.petservices.Network.Models.Photo;
import com.doapps.petservices.Network.Models.PostResponse;
import com.doapps.petservices.Network.Models.UserData;
import com.doapps.petservices.PetServicesApplication;
import com.doapps.petservices.R;
import com.doapps.petservices.Utils.Constants;
import com.doapps.petservices.Utils.PreferenceManager;
import com.doapps.petservices.Utils.Utils;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.doapps.petservices.Network.Models.PostResponse.*;

/**
 * Created by NriKe on 07/05/2017.
 */

public class PerfilFragment extends Fragment {

    private RecyclerView rv_my_post;
    private PostAdapter adapter;
    private TextView tv_pets;
    private ImageView iv_photo;
    private TextView tv_username;
    private com.github.clans.fab.FloatingActionButton menu_edit;
    private com.github.clans.fab.FloatingActionButton menu_add;

    private PreferenceManager manager;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_perfil, container, false);

        manager = PreferenceManager.getInstance(getActivity());

        getUserData(v);

        return v;
    }

    private void getUserData(final View v) {
        Call<UserData> call = PetServicesApplication.getInstance().getServices().getUserData(manager.getUserId());

        call.enqueue(new Callback<UserData>() {
            @Override
            public void onResponse(Call<UserData> call, Response<UserData> response) {
                if(response.isSuccessful()){
                    setupViews(v,response);
                    getUserPost();
                }
            }

            @Override
            public void onFailure(Call<UserData> call, Throwable t) {
                Utils.showToastInternalServerError(getActivity());
            }
        });
    }


    private void getUserPost() {
        Call<ArrayList<PostResponse>> call = PetServicesApplication.getInstance().getServices().getUserPosts(manager.getUserId());

        call.enqueue(new Callback<ArrayList<PostResponse>>() {
            @Override
            public void onResponse(Call<ArrayList<PostResponse>> call, Response<ArrayList<PostResponse>> response) {
                if (response.isSuccessful()) {
                    setupRv(response.body());
                }
            }

            @Override
            public void onFailure(Call<ArrayList<PostResponse>> call, Throwable t) {
                Utils.showToastInternalServerError(getActivity());
            }
        });
    }

    private void setupViews(View v, Response<UserData> response) {
        rv_my_post = (RecyclerView) v.findViewById(R.id.rv_my_post);
        tv_pets = (TextView) v.findViewById(R.id.tv_pets);
        iv_photo = (ImageView) v.findViewById(R.id.iv_photo);
        tv_username = (TextView) v.findViewById(R.id.tv_username);
        menu_edit = (com.github.clans.fab.FloatingActionButton) v.findViewById(R.id.menu_edit);
        menu_add = (com.github.clans.fab.FloatingActionButton) v.findViewById(R.id.menu_add);

        tv_username.setText(response.body().getName());

        if(manager.getUserPhoto() != null && !manager.getUserPhoto().isEmpty()){
            String newUrl = manager.getUserPhoto().substring(0, 4) + "s" + manager.getUserPhoto().substring(4,manager.getUserPhoto().length());
            Picasso.with(getActivity()).load(newUrl).into(iv_photo);
        }else{
            iv_photo.setImageDrawable(ContextCompat.getDrawable(getActivity(),R.drawable.empty));
        }

        menu_edit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivityForResult(new Intent(getActivity(), UpdateAccount.class), Constants.UPDATE_USER_REQUEST);
            }
        });

        tv_pets.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getActivity(), ListaMascotasActivity.class));
            }
        });

        menu_add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivityForResult(new Intent(getActivity(), CrearPublicacionCliente.class), Constants.CREATE_POST_REQUEST);
            }
        });
    }

    private void setupRv(ArrayList<PostResponse> body) {
        adapter = new PostAdapter(getActivity(), body, rv_my_post);
        rv_my_post.setLayoutManager(new LinearLayoutManager(getActivity()));
        rv_my_post.setAdapter(adapter);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode == Activity.RESULT_OK) {
            if (requestCode == Constants.CREATE_POST_REQUEST) {
                getUserPost();
            }
            if(requestCode == Constants.UPDATE_USER_REQUEST){
                if(!manager.getUserPhoto().isEmpty()){
                    Picasso.with(getActivity()).load(manager.getUserPhoto()).into(iv_photo);
                }
                tv_username.setText(manager.getName());
            }
        }
    }
}