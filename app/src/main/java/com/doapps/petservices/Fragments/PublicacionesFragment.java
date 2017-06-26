package com.doapps.petservices.Fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;

import com.doapps.petservices.Adapters.PostAdapter;
import com.doapps.petservices.Models.Post;
import com.doapps.petservices.Network.Models.PostResponse;
import com.doapps.petservices.PetServicesApplication;
import com.doapps.petservices.R;
import com.doapps.petservices.Utils.Utils;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by NriKe on 07/05/2017.
 */

public class PublicacionesFragment extends Fragment {

    RecyclerView rv_post;
    PostAdapter adapter;
    ProgressBar pb;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_publicaciones,container,false);

        rv_post = (RecyclerView) v.findViewById(R.id.rv_post);
        pb = (ProgressBar) v.findViewById(R.id.pb);

        getAllPost();

        return v;
    }

    private void getAllPost() {
        pb.setVisibility(View.VISIBLE);
        rv_post.setVisibility(View.GONE);

        Call<ArrayList<PostResponse>> call = PetServicesApplication.getInstance().getServices().getAllPost();

        call.enqueue(new Callback<ArrayList<PostResponse>>() {
            @Override
            public void onResponse(Call<ArrayList<PostResponse>> call, Response<ArrayList<PostResponse>> response) {
                if(response.isSuccessful()){
                    setupRv(response.body());
                }
                pb.setVisibility(View.GONE);
                rv_post.setVisibility(View.VISIBLE);
            }

            @Override
            public void onFailure(Call<ArrayList<PostResponse>> call, Throwable t) {
                pb.setVisibility(View.GONE);
                rv_post.setVisibility(View.VISIBLE);
                Utils.showToastInternalServerError(getActivity());
            }
        });
    }

    private void setupRv(ArrayList<PostResponse> body){
        adapter = new PostAdapter(getActivity(),body,null);
        rv_post.setLayoutManager(new LinearLayoutManager(getActivity()));
        rv_post.setAdapter(adapter);
    }
}