package com.helpinghands.activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Patterns;
import android.widget.EditText;

import com.helpinghands.R;
import com.helpinghands.helper.Helper;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class ForgotPasswordActivity extends AppCompatActivity {

    @BindView(R.id.et_email)
    EditText etEmail;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forgot_password);
        ButterKnife.bind(this);
    }

    @OnClick(R.id.iv_back)
    void back(){
        onBackPressed();
    }

    @OnClick(R.id.bt_send)
    void forgotPass() {

        String email = etEmail.getText().toString();

        if (TextUtils.isEmpty(email)) {
            showToast("Please enter your email address");
        } else if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            showToast("Please enter a valid email address");
        } else {

            //

        }

    }

    private void showToast(String mess){
        Helper.showToast(this,mess);
    }

}
