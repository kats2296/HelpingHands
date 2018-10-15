package com.helpinghands.activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Patterns;
import android.widget.EditText;
import android.widget.TextView;

import com.helpinghands.R;
import com.helpinghands.helper.Helper;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class LoginActivity extends AppCompatActivity {

    String pos;
    @BindView(R.id.et_email)
    EditText etEmail;
    @BindView(R.id.et_password)
    EditText etPassword;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        ButterKnife.bind(this);

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

            if(pos.equalsIgnoreCase("organization")) {

                Intent intent = new Intent(LoginActivity.this , OrgHomeActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                startActivity(intent);
            }

            if(pos.equalsIgnoreCase("volunteer")) {

                Intent intent = new Intent(LoginActivity.this , OrgHomeActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                startActivity(intent);
            }
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
}
