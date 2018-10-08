package com.helpinghands.activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.helpinghands.R;

import butterknife.ButterKnife;
import butterknife.OnClick;

public class VolSignUpActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_vol_sign_up);
        ButterKnife.bind(this);

    }

    @OnClick(R.id.tv_login)
    void login() {

        Intent intent = new Intent(VolSignUpActivity.this, LoginActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(intent);
    }
}
