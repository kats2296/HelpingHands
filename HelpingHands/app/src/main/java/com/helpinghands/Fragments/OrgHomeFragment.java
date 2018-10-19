package com.helpinghands.Fragments;


import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.helpinghands.EventDetailsActivity;
import com.helpinghands.R;
import com.helpinghands.activity.OrgHomeActivity;
import com.helpinghands.adapter.OrgHomeAdapter;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * A simple {@link Fragment} subclass.
 */
public class OrgHomeFragment extends Fragment {

    @BindView(R.id.recyclerView)
    RecyclerView recyclerView;
    private OrgHomeAdapter homeAdapter;
    public OrgHomeFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_org_home, container, false);
        ButterKnife.bind(this, view);

        findView();
        return view;
    }

    String[] names = {"ZERO HUNGER" , "EDUCATION" , "GOOD HEALTH AND WELL BEING" , "DONATIONS"};

    private void findView() {

        recyclerView.setLayoutManager(new GridLayoutManager(getActivity() , 2));

        RecyclerViewClickListener listener = new RecyclerViewClickListener() {
            @Override
            public void onClick(View view, int position) {
                startIntent(position);
            }
        };

        homeAdapter = new OrgHomeAdapter(getContext(), listener);
        recyclerView.setAdapter(homeAdapter);

    }

    public void startIntent(int position) {

        Intent intent = new Intent(getContext(), EventDetailsActivity.class);
        intent.putExtra("category", names[position]);
        startActivity(intent);
    }


    public interface RecyclerViewClickListener {

        void onClick(View view, int position);

    }


}
