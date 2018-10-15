package com.helpinghands.activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.CardView;
import android.view.View;
import android.view.animation.AnimationUtils;
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

        CardView cardViewOrg = findViewById(R.id.cardViewOrg);
        CardView cardViewVol = findViewById(R.id.cardViewVol);

        cardViewOrg.startAnimation(AnimationUtils.loadAnimation(
                this,R.anim.slide_from_left
        ));

        cardViewVol.startAnimation(AnimationUtils.loadAnimation(
                this,R.anim.slide_from_right
        ));
    }

    @OnClick(R.id.tv_org)
    void startOrgTitle() {
        startOrg();
    }

    @OnClick(R.id.textViewOrgDetail)
    void startOrgDetail() {
        startOrg();
    }


    private void startOrg(){
        Intent intent = new Intent(ChooseActivity.this, LoginActivity.class);
        intent.putExtra("position" , "organization");
        startActivity(intent);
    }

    @OnClick(R.id.tv_vol)
    void startVolTitle() {
        startVol();
    }

    @OnClick(R.id.textViewVolDetail)
    void startVolDetail() {
        startVol();
    }

    private void startVol() {
        Intent intent = new Intent(ChooseActivity.this, LoginActivity.class);
        intent.putExtra("position" , "volunteer");
        startActivity(intent);
    }
}
