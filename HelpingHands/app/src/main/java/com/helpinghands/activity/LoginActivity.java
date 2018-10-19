package com.helpinghands.activity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.util.Patterns;
import android.widget.EditText;
import android.widget.TextView;

import com.helpinghands.R;
import com.helpinghands.database.StoreUserData;
import com.helpinghands.helper.Helper;
import com.helpinghands.helper.Logger;
import com.helpinghands.retrofit.ApiCall;
import com.helpinghands.retrofit.IApiCallback;
import com.helpinghands.retrofit.requests.OrgLoginRequest;
import com.helpinghands.retrofit.response.OrgLoginResponse;
import com.helpinghands.retrofit.response.OrgSignupResponse;
import com.helpinghands.retrofit.response.VolLoginResponse;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import retrofit2.Response;

public class LoginActivity extends AppCompatActivity implements IApiCallback {

    String pos;
    @BindView(R.id.et_email)
    EditText etEmail;
    @BindView(R.id.et_password)
    EditText etPassword;

    private ProgressDialog progressDialog;
    StoreUserData storeUserData;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        ButterKnife.bind(this);

        progressDialog=Helper.initProgressDialog(this);
        storeUserData = StoreUserData.getInstance(this);

        receiveIntent();
    }

    private void receiveIntent() {

        Intent intent = getIntent();
        pos = intent.getStringExtra("position");
    }

    @OnClick(R.id.tv_signup)
    void register() {
        Helper.hideKeypad(this , etEmail);
        Intent intent = new Intent(LoginActivity.this , EmailActivity.class);
            intent.putExtra("position" , pos);
            startActivity(intent);
        }

    @OnClick(R.id.bt_login)
    void launchHomeScreen() {
        Helper.hideKeypad(this , etEmail);
        validateData();
    }

    private void validateData() {

        String email=etEmail.getText().toString();
        String password=etPassword.getText().toString();

        if (TextUtils.isEmpty(email)){
            showToast("Please enter your email address");
        }

        else if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            showToast("Please enter a valid email address");
        }

        else if(TextUtils.isEmpty(password)){
            showToast("Please enter your password");
        }

        else if (password.length()<8) {
            showToast("Please enter a valid password");
        }

        else {

            OrgLoginRequest request = new OrgLoginRequest();
            request.setEmail(email);
            request.setPassword(password);

            progressDialog.show();

            if(pos.equalsIgnoreCase("organization"))
                ApiCall.getInstance().orgLogin(request, this);

            else if(pos.equalsIgnoreCase("volunteer"))
                ApiCall.getInstance().vol_login(request, this);


//            if(pos.equalsIgnoreCase("organization")) {
//
//                Intent intent = new Intent(LoginActivity.this , OrgHomeActivity.class);
//                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
//                startActivity(intent);
//            }
//
//            if(pos.equalsIgnoreCase("volunteer")) {
//
//                Intent intent = new Intent(LoginActivity.this , VolunteerHomeActivity.class);
//                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
//                startActivity(intent);
//            }
        }
    }

    private void showToast(String mess){
        Helper.showToast(this,mess);
    }

    @OnClick(R.id.tv_forgotpassword)
    void forgotPass() {

        Intent intent = new Intent(LoginActivity.this, ForgotPasswordActivity.class);
        startActivity(intent);
    }

    @Override
    public void onSuccess(Object type, Object data) {

        progressDialog.dismiss();

        if(type.toString().equalsIgnoreCase("login organization")) {

            Response<OrgLoginResponse> response = (Response<OrgLoginResponse>) data;

             if(response.code() == 401) {
                showToast("Your password is incorrect");
            }

            else if(response.code() == 400) {
                showToast("User with this email id doesn't exists");
            }

            else if (response.body() != null) {

                    String message = response.body().getOrgLogin();

                    if (response.code()==200) {

                        if(pos.equalsIgnoreCase("organization")) {

                            storeUserData.setLogin(1);

                            Intent intent = new Intent(LoginActivity.this , OrgHomeActivity.class);
                            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                            startActivity(intent);
                        }
                    }


                } else {

                    showToast("Please Try Again");
                }

        }

        else if(type.toString().equalsIgnoreCase("login volunteer")) {

            Response<VolLoginResponse> response = (Response<VolLoginResponse>) data;

            if(response.code() == 401) {
                showToast("Your password is incorrect");
            }

            else if(response.code() == 400) {
                showToast("User with this email id doesn't exists");
            }

            else if (response.body() != null) {

                String message = response.body().getVolLogin();

                if (response.code()==200) {

                    if(pos.equalsIgnoreCase("volunteer")) {

                        storeUserData.setLogin(2);

                        Intent intent = new Intent(LoginActivity.this , VolunteerHomeActivity.class);
                        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
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

    }
}
