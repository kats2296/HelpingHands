package com.helpinghands.adapter;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.helpinghands.EventDetailsActivity;
import com.helpinghands.R;
import com.helpinghands.activity.OrgHomeActivity;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class OrgHomeAdapter extends RecyclerView.Adapter<OrgHomeAdapter.OrgHomeViewHolder> {

    String[] names = {"ZERO HUNGER" , "NO POVERTY" , "GOOD HEALTH AND WELL BEING" , "DONATIONS"};
    Context context;

    public OrgHomeAdapter(Context context) {
        this.context = context;
    }

    @NonNull
    @Override
    public OrgHomeViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.home_fragment_items , parent , false);

        ButterKnife.bind(this , v);

        return new OrgHomeViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull OrgHomeViewHolder holder, int position) {

        holder.tv_service.setText(String.valueOf(names[position]));

    }

    @Override
    public int getItemCount() {
        return 4;
    }

    public class OrgHomeViewHolder extends RecyclerView.ViewHolder {

        private TextView tv_service;

        public OrgHomeViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this , itemView);
            tv_service = itemView.findViewById(R.id.tv_service);
        }
    }

    @OnClick(R.id.cv_service)
    void getDetailsPage() {
        ((OrgHomeActivity)context).startIntent();
    }
}
