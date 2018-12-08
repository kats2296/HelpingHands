package com.helpinghands.Fragments;


import android.annotation.TargetApi;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;
import com.helpinghands.LocationTrack;
import com.helpinghands.R;
import com.helpinghands.activity.DistrictsSelectionActivity;
import com.helpinghands.adapter.OrgHomeAdapter;
import com.helpinghands.helper.Helper;
import com.helpinghands.helper.Logger;
import com.helpinghands.retrofit.ApiCall;
import com.helpinghands.retrofit.IApiCallback;
import com.helpinghands.retrofit.requests.GetDistrictByLatLngRequest;
import com.helpinghands.retrofit.requests.GetSuggestedEventRequest;
import com.helpinghands.retrofit.response.GetDistrictByLatLngResponse;
import com.helpinghands.retrofit.response.SuggestedEventResponse;
import com.helpinghands.weather_forecasting.Api;

import java.util.ArrayList;
import java.util.Calendar;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import retrofit2.Response;

import static android.Manifest.permission.ACCESS_COARSE_LOCATION;
import static android.Manifest.permission.ACCESS_FINE_LOCATION;

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

    private ArrayList permissionsToRequest;
    private ArrayList permissionsRejected = new ArrayList();
    private ArrayList permissions = new ArrayList();

    private final static int ALL_PERMISSIONS_RESULT = 101;
    LocationTrack locationTrack;

    public OrgHomeFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_org_home, container, false);
        ButterKnife.bind(this, view);

        getLatLng();
        findView();
        return view;
    }

    private void getLatLng() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            locationTrack = new LocationTrack(getContext());
            if (locationTrack.canGetLocation()) {


                double longitude = locationTrack.getLongitude();
                double latitude = locationTrack.getLatitude();

                if((longitude != 0.0) && (latitude != 0.0)) {
                    makeRequestForDistrict(latitude, longitude);
                }

//                Logger.show("@@@@@  " + String.valueOf(longitude) + "@@@@@ " + String.valueOf(latitude));

//                    Toast.makeText(getContext(), "Longitude:" + Double.toString(longitude) + "\nLatitude:" + Double.toString(latitude), Toast.LENGTH_SHORT).show();
            } else {

                locationTrack.showSettingsAlert();
            }

        }

    }

    private void makeRequestForDistrict(double latitude, double longitude) {

        GetDistrictByLatLngRequest request = new GetDistrictByLatLngRequest();
        request.setLat(String.valueOf(latitude));
        request.setLng(String.valueOf(longitude));

        progressDialog = Helper.initProgressDialog(getContext());
        progressDialog.show();

        ApiCall.getInstance().get_district_by_lat_lng(request, this);
    }


    @Override
        public void onDestroy () {
            super.onDestroy();
            locationTrack.stopListener();
        }


        String[] names = {"poverty", "education", "health_care", "donation"};
        private void findView () {

            recyclerView.setLayoutManager(new GridLayoutManager(getActivity(), 2));

            RecyclerViewClickListener listener = new RecyclerViewClickListener() {
                @Override
                public void onClick(View view, int position) {
//                startIntent(position);
                    showDistrictSuggestions(names[position]);
                }
            };

            homeAdapter = new OrgHomeAdapter(getContext(), listener);
            recyclerView.setAdapter(homeAdapter);
        }

        private void makeRequest (String district) {

            GetSuggestedEventRequest request = new GetSuggestedEventRequest();

            Calendar c = Calendar.getInstance();
            int month = c.get(Calendar.MONTH);

            request.setMonth(month + 1);
            request.setDistrict(district);

            progressDialog = Helper.initProgressDialog(getContext());
            progressDialog.show();

            ApiCall.getInstance().get_suggested_event(request, this);
        }

        @Override
        public void onSuccess (Object type, Object data){
//            progressDialog.dismiss();

            if(type.equals("get event suggestion")) {

                progressDialog.dismiss();

                Response<SuggestedEventResponse> response = (Response<SuggestedEventResponse>) data;

                if (response.isSuccessful()) {

                    if (response.body() != null) {
                        suggested_event = response.body().getEvent();

                        if (suggested_event.equalsIgnoreCase("poverty")) {
                            tv_event.setText("ZERO HUNGER");
                        }

                        if (suggested_event.equalsIgnoreCase("education")) {
                            tv_event.setText("EDUCATION");
                        }

                        if (suggested_event.equalsIgnoreCase("health_care")) {
                            tv_event.setText("GOOD HEALTH AND WELL BEING");
                        }

                        if (suggested_event.equalsIgnoreCase("donation")) {
                            tv_event.setText("DONATIONS");
                        }
                    }
                }
            }

            if(type.equals("get district by latlng")) {

                progressDialog.dismiss();

                Response<GetDistrictByLatLngResponse> response = (Response<GetDistrictByLatLngResponse>) data;

                if (response.isSuccessful()) {

                    if (response.body() != null) {

//                        Logger.show("@@@@@ " + response.body().getDistrict());
                        String district = response.body().getDistrict();
                        makeRequest(district);

                    }

                }
            }
        }

        @Override
        public void onFailure (Object data){

            progressDialog.dismiss();
            Logger.show(data.toString());
        }


        public interface RecyclerViewClickListener {

            void onClick(View view, int position);

        }

        @OnClick(R.id.cv_event_suggestion)
        void onSuggestedEventClick () {
            showDistrictSuggestions(suggested_event);
        }

        private void showDistrictSuggestions (String event){

            Intent intent = new Intent(getContext(), DistrictsSelectionActivity.class);
            intent.putExtra("event", event);
            startActivity(intent);

        }
    }
