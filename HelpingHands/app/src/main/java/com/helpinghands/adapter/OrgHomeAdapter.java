package com.helpinghands.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.helpinghands.R;
import com.helpinghands.activity.OrgHomeActivity;

import butterknife.ButterKnife;
import butterknife.OnClick;

public class OrgHomeAdapter extends RecyclerView.Adapter<OrgHomeAdapter.OrgHomeViewHolder> {

    String[] names = {"ZERO HUNGER" , "EDUCATION" , "GOOD HEALTH AND WELL BEING" , "DONATIONS"};
    int[] images =   {R.drawable.ic_zero_hunger , R.drawable.ic_education, R.drawable.ic_medical_aids ,
                        R.drawable.ic_no_poverty};
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
        holder.iv_service.setImageResource(images[position]);

    }

    @Override
    public int getItemCount() {
        return 4;
    }

    public class OrgHomeViewHolder extends RecyclerView.ViewHolder {

        private TextView tv_service;
        private ImageView iv_service;

        public OrgHomeViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this , itemView);
            tv_service = itemView.findViewById(R.id.tv_service);
            iv_service = itemView.findViewById(R.id.iv_service);
        }
    }

    @OnClick(R.id.cv_service)
    void getDetailsPage() {
        ((OrgHomeActivity)context).startIntent();
    }
}
