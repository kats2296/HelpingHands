package com.helpinghands.activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.util.Patterns;
import android.widget.Button;
import android.widget.EditText;

import com.helpinghands.R;
import com.helpinghands.helper.Helper;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class EmailActivity extends AppCompatActivity {

    String pos;
    @BindView(R.id.et_email)
    EditText et_email;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_email);
        ButterKnife.bind(this);

        receiveIntent();
    }

    private void receiveIntent() {

        Intent intent = getIntent();
        pos = intent.getStringExtra("position");

    }

    @OnClick(R.id.bt_send)
    void validate() {

        String email = et_email.getText().toString();

        if (TextUtils.isEmpty(email)){
            showToast("Please enter your email address");
        }

        else if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            showToast("Please enter a valid email address");
        }

        else {

            if(pos.equalsIgnoreCase("organization")) {

                Intent intent = new Intent(EmailActivity.this , OrgSignUpActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent);

            }

            if(pos.equalsIgnoreCase("volunteer")) {

                Intent intent = new Intent(EmailActivity.this , VolSignUpActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent);

            }

        }

    }

    private void showToast(String mess){
        Helper.showToast(this,mess);
    }



}
