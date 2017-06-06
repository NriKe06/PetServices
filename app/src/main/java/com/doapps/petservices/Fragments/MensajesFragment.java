package com.doapps.petservices.Fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.doapps.petservices.Adapters.MessagesAdapter;
import com.doapps.petservices.Models.Message;
import com.doapps.petservices.R;

import java.util.ArrayList;

/**
 * Created by NriKe on 07/05/2017.
 */

public class MensajesFragment extends Fragment {

    RecyclerView rv_messages;
    MessagesAdapter adapter;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_mensajes,container,false);

        rv_messages = (RecyclerView) v.findViewById(R.id.rv_messages);

        adapter = new MessagesAdapter(getActivity(),new ArrayList<Message>());
        rv_messages.setLayoutManager(new LinearLayoutManager(getActivity()));
        rv_messages.setAdapter(adapter);

        return v;
    }
}
