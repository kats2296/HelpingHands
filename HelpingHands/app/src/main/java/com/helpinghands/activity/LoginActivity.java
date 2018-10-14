package com.helpinghands.activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

import com.helpinghands.OrganisationHomeActivity;
import com.helpinghands.R;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class LoginActivity extends AppCompatActivity {

    String pos;

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

            Intent intent = new Intent(LoginActivity.this , EmailActivity.class);
            intent.putExtra("position" , pos);
            startActivity(intent);
        }

    @OnClick(R.id.bt_login)
    void launchHomeScreen() {

        if(pos.equalsIgnoreCase("organization")) {

            Intent intent = new Intent(LoginActivity.this , OrganisationHomeActivity.class);
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP);
            startActivity(intent);
        }

    }
}
