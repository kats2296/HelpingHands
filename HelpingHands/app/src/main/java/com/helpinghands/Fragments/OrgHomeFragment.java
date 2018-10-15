package com.helpinghands.Fragments;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.helpinghands.R;
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

    private void findView() {

        recyclerView.setLayoutManager(new GridLayoutManager(getActivity() , 2));
        homeAdapter = new OrgHomeAdapter(getContext());
        recyclerView.setAdapter(homeAdapter);

    }



}
