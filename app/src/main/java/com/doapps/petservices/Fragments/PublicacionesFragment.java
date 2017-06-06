package com.doapps.petservices.Fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.doapps.petservices.Adapters.PostAdapter;
import com.doapps.petservices.Models.Post;
import com.doapps.petservices.R;

import java.util.ArrayList;

/**
 * Created by NriKe on 07/05/2017.
 */

public class PublicacionesFragment extends Fragment {

    RecyclerView rv_post;
    PostAdapter adapter;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_publicaciones,container,false);

        rv_post = (RecyclerView) v.findViewById(R.id.rv_post);

        adapter = new PostAdapter(getActivity(),new ArrayList<Post>());
        rv_post.setLayoutManager(new LinearLayoutManager(getActivity()));
        rv_post.setAdapter(adapter);

        return v;
    }
}