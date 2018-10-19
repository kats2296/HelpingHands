package com.helpinghands;

import android.app.ProgressDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import com.helpinghands.database.StoreUserData;
import com.helpinghands.helper.Helper;
import com.helpinghands.retrofit.requests.CreateEventRequest;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class EventDetailsActivity extends AppCompatActivity {

    @BindView(R.id.et_eventname)
    EditText et_eventname;
    @BindView(R.id.et_contactno)
    EditText et_contact;
    @BindView(R.id.et_address)
    EditText et_address;
    @BindView(R.id.et_date)
    EditText et_date;
    @BindView(R.id.et_time)
    EditText et_time;
    @BindView(R.id.et_number)
    EditText et_number_of_ppl;
    @BindView(R.id.et_volunteers)
    EditText number_volunteers;
    @BindView(R.id.radioGroup2)
    RadioGroup ispickup;

    RadioButton pickup;

    private ProgressDialog progressDialog;
    StoreUserData storeUserData;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_event_details);
        ButterKnife.bind(this);

        progressDialog=Helper.initProgressDialog(this);
        storeUserData = StoreUserData.getInstance(this);

    }

    @OnClick(R.id.bt_submit)
    void submit() {

        String eventname = et_eventname.getText().toString();
        String contact = et_contact.getText().toString();
        String address = et_address.getText().toString();
        String time = et_time.getText().toString();
        int number_of_ppl = Integer.parseInt(et_number_of_ppl.getText().toString());
        String date = et_date.getText().toString();
        int volunteers = Integer.parseInt(number_volunteers.getText().toString());

        int selectedId2 = ispickup.getCheckedRadioButtonId();
        pickup = findViewById(selectedId2);

        String pickupVal = pickup.getText().toString();
        Boolean pickupBolVal;

        if(pickupVal.equalsIgnoreCase("Yes"))
            pickupBolVal = true;

        else
            pickupBolVal = false;



        if (TextUtils.isEmpty(eventname)) {
            showToast("Please enter event name");
        } else if (TextUtils.isEmpty(contact)) {
            showToast("Please enter your contact number");
        } else if (contact.length() < 10) {
            showToast("Please enter a valid contact number");
        } else if (TextUtils.isEmpty(address)) {
            showToast("Please enter the address of the event");
        } else if (TextUtils.isEmpty(time)) {
            showToast("Please enter the event timings");
        } else if (TextUtils.isEmpty(time)) {
            showToast("Please enter the event timings");
        } else if (TextUtils.isEmpty(String.valueOf(number_of_ppl))) {
            showToast("Please enter number of people"); }
        else {

            CreateEventRequest request = new CreateEventRequest(eventname, contact, storeUserData.getEmail(), address,
                    date, time, number_of_ppl,volunteers, pickupBolVal,  );

        }

    }

    private void showToast(String mess) {
        Helper.showToast(this, mess);
    }

}


