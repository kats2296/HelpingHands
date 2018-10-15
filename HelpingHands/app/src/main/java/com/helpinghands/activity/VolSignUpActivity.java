package com.helpinghands.activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.widget.EditText;

import com.helpinghands.R;
import com.helpinghands.helper.Helper;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class VolSignUpActivity extends AppCompatActivity {

    @BindView(R.id.et_name)
    EditText et_name;
    @BindView(R.id.et_contactno)
    EditText et_contact;
    @BindView(R.id.et_address)
    EditText et_address;
    @BindView(R.id.et_password)
    EditText et_password;
    @BindView(R.id.et_confirmpass)
    EditText et_confirm_pass;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_vol_sign_up);
        ButterKnife.bind(this);

    }

    @OnClick(R.id.tv_login)
    void login() {

        Intent intent = new Intent(VolSignUpActivity.this, LoginActivity.class);
        intent.putExtra("position" , "volunteer");
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(intent);
    }

    @OnClick(R.id.bt_signup)
    void launchHomeScreen() {
        validateData();
    }

    private void validateData() {

        String contact = et_contact.getText().toString();
        String name = et_name.getText().toString();
        String address = et_address.getText().toString();
        String password = et_password.getText().toString();
        String confirmpass = et_confirm_pass.getText().toString();

        if (TextUtils.isEmpty(name)) {
            showToast("Please enter your name");
        }

        else if(TextUtils.isEmpty(contact)) {
            showToast("Please enter your contact number");
        }

        else if (contact.length() < 10) {
            showToast("Please enter a valid contact number");
        }

        else if (TextUtils.isEmpty(address)) {
            showToast("Please enter the organization's address");
        }

        else if (TextUtils.isEmpty(password)) {
            showToast("Please enter a password");
        }

        else if (password.length()<8) {
            showToast("Please enter a valid password");
        }

        else if (TextUtils.isEmpty(confirmpass)) {
            showToast("Please enter password again");
        }

        else if (password.length()<8) {
            showToast("Please enter a valid password");
        }

        else {

            Intent intent = new Intent(VolSignUpActivity.this , VolunteerHomeActivity.class);
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
            startActivity(intent);

        }

    }

    private void showToast(String mess) {
        Helper.showToast(this, mess);
    }
}
