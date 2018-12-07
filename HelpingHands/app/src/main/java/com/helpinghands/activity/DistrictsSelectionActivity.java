package com.helpinghands.activity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.CardView;
import android.widget.Button;
import android.widget.TextView;

import com.helpinghands.EventDetailsActivity;
import com.helpinghands.R;
import com.helpinghands.helper.Helper;
import com.helpinghands.helper.Logger;
import com.helpinghands.retrofit.ApiCall;
import com.helpinghands.retrofit.IApiCallback;
import com.helpinghands.retrofit.requests.GetDistrictsRequest;
import com.helpinghands.retrofit.response.DistrictsResponse;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import retrofit2.Response;

public class DistrictsSelectionActivity extends AppCompatActivity implements IApiCallback {

    @BindView(R.id.cv_district1)
    CardView district1;
    @BindView(R.id.cv_district2)
    CardView district2;
    @BindView(R.id.cv_district3)
    CardView district3;
    @BindView(R.id.cv_district_any)
    CardView any_district;

    @BindView(R.id.dist1)
    TextView dist1;
    @BindView(R.id.dist2)
    TextView dist2;
    @BindView(R.id.dist3)
    TextView dist3;

    private ProgressDialog progressDialog;
    String event;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_districts_selection);
        ButterKnife.bind(this);

        progressDialog=Helper.initProgressDialog(this);
        receiveIntent();
        makeRequest();
    }

    private void receiveIntent() {

        Intent intent = getIntent();
        event = intent.getStringExtra("event");
    }

    private void makeRequest() {

        GetDistrictsRequest request = new GetDistrictsRequest();
        request.setEvent(event.toLowerCase());
        progressDialog.show();

        ApiCall.getInstance().get_districts(request, this);

    }

    @OnClick(R.id.cv_district1)
    void onDistrictOneClick() {
        openEventDetailsPage(dist1.getText().toString());
    }

    @OnClick(R.id.cv_district2)
    void onDistrictTwoClick() {
        openEventDetailsPage(dist2.getText().toString());
    }

    @OnClick(R.id.cv_district3)
    void onDistrictThreeClick() {
        openEventDetailsPage(dist3.getText().toString());
    }

    @OnClick(R.id.cv_district_any)
    void onEventClick() {
        openEventDetailsPage("");
    }

    private void openEventDetailsPage(String district) {

        Intent intent  = new Intent(this, EventDetailsActivity.class);
        intent.putExtra("district", district);
        intent.putExtra("category", event.toLowerCase());
        startActivity(intent);
    }

    @Override
    public void onSuccess(Object type, Object data) {
        progressDialog.dismiss();
        Response<DistrictsResponse> response = (Response<DistrictsResponse>) data;

        if (response.isSuccessful()) {

            if (response.body() != null) {

                Logger.show(response.body().toString());

                List<String> districts = response.body().getDistricts();
                dist1.setText(districts.get(0));
                dist2.setText(districts.get(1));
                dist3.setText(districts.get(2));
                }

            }
    }

    @Override
    public void onFailure(Object data) {

        progressDialog.dismiss();
        Logger.show(data.toString());
    }
}