package com.helpinghands.activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.helpinghands.R;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class ChooseActivity extends AppCompatActivity {



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_choose);
        ButterKnife.bind(this);
    }

    @OnClick(R.id.tv_org)
    void startOrg() {

        Intent intent = new Intent(ChooseActivity.this, LoginActivity.class);
        intent.putExtra("position" , "organization");
        startActivity(intent);
    }

    @OnClick(R.id.tv_vol)
    void startVol() {

        Intent intent = new Intent(ChooseActivity.this, LoginActivity.class);
        intent.putExtra("position" , "volunteer");
        startActivity(intent);
    }
}
