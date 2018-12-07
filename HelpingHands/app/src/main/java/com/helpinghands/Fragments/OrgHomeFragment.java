package com.helpinghands.Fragments;


import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.helpinghands.R;
import com.helpinghands.activity.DistrictsSelectionActivity;
import com.helpinghands.adapter.OrgHomeAdapter;
import com.helpinghands.helper.Helper;
import com.helpinghands.helper.Logger;
import com.helpinghands.retrofit.ApiCall;
import com.helpinghands.retrofit.IApiCallback;
import com.helpinghands.retrofit.requests.GetSuggestedEventRequest;
import com.helpinghands.retrofit.response.SuggestedEventResponse;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import retrofit2.Response;

/**
 * A simple {@link Fragment} subclass.
 */
public class OrgHomeFragment extends Fragment implements IApiCallback {

    @BindView(R.id.recyclerView)
    RecyclerView recyclerView;
    @BindView(R.id.tv_suggested_event)
    TextView tv_event;

    private OrgHomeAdapter homeAdapter;
    private ProgressDialog progressDialog;
    String suggested_event = "";

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

    String[] names = {"poverty" , "education" , "health_care" , "donation"};
    private void findView() {

        progressDialog=Helper.initProgressDialog(getContext());


        recyclerView.setLayoutManager(new GridLayoutManager(getActivity() , 2));

        RecyclerViewClickListener listener = new RecyclerViewClickListener() {
            @Override
            public void onClick(View view, int position) {
//                startIntent(position);
                  showDistrictSuggestions(names[position]);
            }
        };

        homeAdapter = new OrgHomeAdapter(getContext(), listener);
        recyclerView.setAdapter(homeAdapter);

        makeRequest();

    }

    private void makeRequest() {

        GetSuggestedEventRequest request = new GetSuggestedEventRequest();
        request.setMonth(2);
        request.setDistrict("KULGAM");
        progressDialog.show();

        ApiCall.getInstance().get_suggested_event(request, this);
    }

//    public void startIntent(int position) {
//
//        Intent intent = new Intent(getContext(), EventDetailsActivity.class);
//        intent.putExtra("category", names[position]);
//        startActivity(intent);
//    }

    @Override
    public void onSuccess(Object type, Object data) {
        progressDialog.dismiss();
        Response<SuggestedEventResponse> response = (Response<SuggestedEventResponse>) data;

        if (response.isSuccessful()) {

            if (response.body() != null) {
                suggested_event = response.body().getEvent();

                if(suggested_event.equalsIgnoreCase("poverty")) {
                    tv_event.setText("ZERO HUNGER");
                }

                if(suggested_event.equalsIgnoreCase("education")) {
                    tv_event.setText("EDUCATION");
                }

                if(suggested_event.equalsIgnoreCase("health_care")) {
                    tv_event.setText("GOOD HEALTH AND WELL BEING");
                }

                if(suggested_event.equalsIgnoreCase("donation")) {
                    tv_event.setText("DONATIONS");
                }
            }
        }
    }

    @Override
    public void onFailure(Object data) {

        progressDialog.dismiss();
        Logger.show(data.toString());
    }


    public interface RecyclerViewClickListener {

        void onClick(View view, int position);

    }

    @OnClick(R.id.cv_event_suggestion)
    void onSuggestedEventClick() {
        showDistrictSuggestions(suggested_event);
    }

    private void showDistrictSuggestions(String event) {

        Intent intent = new Intent(getContext(), DistrictsSelectionActivity.class);
        intent.putExtra("event", event);
        startActivity(intent);

    }

}
