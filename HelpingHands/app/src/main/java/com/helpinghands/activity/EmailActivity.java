package com.helpinghands.activity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.util.Patterns;
import android.widget.EditText;

import com.helpinghands.R;
import com.helpinghands.database.StoreUserData;
import com.helpinghands.helper.Helper;
import com.helpinghands.helper.Logger;
import com.helpinghands.retrofit.ApiCall;
import com.helpinghands.retrofit.IApiCallback;
import com.helpinghands.retrofit.requests.CreateRequest;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import retrofit2.Response;

public class EmailActivity extends AppCompatActivity implements IApiCallback {

    String pos;
    @BindView(R.id.et_email)
    EditText et_email;

    private ProgressDialog progressDialog;
    StoreUserData storeUserData;
    String email;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_email);
        ButterKnife.bind(this);

        progressDialog=Helper.initProgressDialog(this);
        storeUserData = StoreUserData.getInstance(this);
        receiveIntent();
    }

    private void receiveIntent() {

        Intent intent = getIntent();
        pos = intent.getStringExtra("position");

    }

    @OnClick(R.id.bt_send)
    void validate() {

        email = et_email.getText().toString();

        if (TextUtils.isEmpty(email)){
            showToast("Please enter your email address");
        }

        else if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            showToast("Please enter a valid email address");
        }

        else {

            CreateRequest request = new CreateRequest();
            request.setEmail(email);
            Log.d("@@@@@@@@@@@@@ " , pos);
            request.setPosition(pos);

            progressDialog.show();
            ApiCall.getInstance().create(request, this);
        }

    }

    private void showToast(String mess){
        Helper.showToast(this,mess);
    }


    @Override
    public void onSuccess(Object type, Object data) {

        progressDialog.dismiss();
        Response<String> response = (Response<String>) data;

        Logger.show(""
                + response.isSuccessful() + "          " + response.body());

        if (response.isSuccessful()) {

            if (response.body() != null) {

                Logger.show(response.body().toString());
                String message = response.body();
                storeUserData.setEmail(email);

                if (response.code()==200) {

                    if (pos.equalsIgnoreCase("organization")) {

                        storeUserData.setEmailSent(1);

                        Intent intent = new Intent(EmailActivity.this, OrgSignUpActivity.class);
                        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                        startActivity(intent);

                    }

                    if (pos.equalsIgnoreCase("volunteer")) {

                        storeUserData.setEmailSent(2);

                        Intent intent = new Intent(EmailActivity.this, VolSignUpActivity.class);
                        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                        startActivity(intent);

                    }

                }

            } else {

                showToast("Please Try Again");
            }

        }
    }

    @Override
    public void onFailure(Object data) {

        progressDialog.dismiss();
        Logger.show(data.toString());

    }
}
