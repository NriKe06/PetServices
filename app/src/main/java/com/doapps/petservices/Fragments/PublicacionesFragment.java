package com.doapps.petservices.Fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.RadioButton;
import android.widget.TextView;

import com.doapps.petservices.Adapters.PostAdapter;
import com.doapps.petservices.Models.Post;
import com.doapps.petservices.Network.Models.PostResponse;
import com.doapps.petservices.PetServicesApplication;
import com.doapps.petservices.R;
import com.doapps.petservices.Utils.Utils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

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
    Button bt_fitlrar;
    RadioButton rb_adopcion;
    RadioButton rb_perdida;
    RadioButton rb_otro;
    RadioButton rb_todas;
    TextView tv_expand;
    LinearLayout ll_container;

    boolean filterVisible = false;

    boolean allTypes = true;

    private String[] types;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_publicaciones,container,false);

        types = getResources().getStringArray(R.array.sp_tipo_post);

        setupViews(v);

        getAllPost();

        return v;
    }

    private void setupViews(View v){
        rv_post = (RecyclerView) v.findViewById(R.id.rv_post);
        pb = (ProgressBar) v.findViewById(R.id.pb);
        bt_fitlrar = (Button) v.findViewById(R.id.bt_fitlrar);
        rb_adopcion = (RadioButton) v.findViewById(R.id.rb_adopcion);
        rb_perdida = (RadioButton) v.findViewById(R.id.rb_perdida);
        rb_otro = (RadioButton) v.findViewById(R.id.rb_otro);
        rb_todas = (RadioButton) v.findViewById(R.id.rb_todas);
        tv_expand = (TextView) v.findViewById(R.id.tv_expand);
        ll_container = (LinearLayout) v.findViewById(R.id.ll_container);

        tv_expand.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ll_container.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT,
                        filterVisible ? 0 : LinearLayout.LayoutParams.WRAP_CONTENT));
                tv_expand.setCompoundDrawablesWithIntrinsicBounds(null,null,
                        filterVisible ? ContextCompat.getDrawable(getActivity(),android.R.drawable.arrow_down_float) :  ContextCompat.getDrawable(getActivity(),android.R.drawable.arrow_up_float),null);
                filterVisible = !filterVisible;
            }
        });

        bt_fitlrar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Map<String, String> map = new HashMap<>();

                if(rb_adopcion.isChecked()){
                    map.put("Type",types[1]);
                    allTypes = false;
                }else if (rb_perdida.isChecked()){
                    map.put("Type",types[2]);
                    allTypes = false;
                }else if (rb_otro.isChecked()){
                    map.put("Type",types[3]);
                    allTypes = false;
                }else if (rb_todas.isChecked()){
                    allTypes = true;
                }

                if(!allTypes){
                    pb.setVisibility(View.VISIBLE);
                    rv_post.setVisibility(View.GONE);

                    Call<ArrayList<PostResponse>> call = PetServicesApplication.getInstance().getServices().filterPost(map);

                    call.enqueue(new Callback<ArrayList<PostResponse>>() {
                        @Override
                        public void onResponse(Call<ArrayList<PostResponse>> call, Response<ArrayList<PostResponse>> response) {
                            if(response.isSuccessful()){
                                setupRv(response.body());
                                ll_container.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT,0));
                                tv_expand.setCompoundDrawablesWithIntrinsicBounds(null,null,ContextCompat.getDrawable(getActivity(),android.R.drawable.arrow_down_float),null);
                                filterVisible = !filterVisible;
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
                }else{
                    getAllPost();
                }

            }
        });
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