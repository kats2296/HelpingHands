package com.helpinghands.activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.util.Patterns;
import android.widget.EditText;

import com.helpinghands.R;
import com.helpinghands.helper.Helper;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class OrgSignUpActivity extends AppCompatActivity {

    @BindView(R.id.et_orgname)
    EditText et_orgname;
    @BindView(R.id.et_contactno)
    EditText et_contact;
    @BindView(R.id.et_headname)
    EditText et_head;
    @BindView(R.id.et_address)
    EditText et_address;
    @BindView(R.id.et_password)
    EditText et_password;
    @BindView(R.id.et_confirmpass)
    EditText et_confirm_pass;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_org_sign_up);
        ButterKnife.bind(this);
    }

    @OnClick(R.id.tv_login)
    void login() {

        Intent intent = new Intent(OrgSignUpActivity.this, LoginActivity.class);
        intent.putExtra("position" , "organization");
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(intent);
    }

    @OnClick(R.id.bt_signup)
    void launchHomeScreen() {
        validateData();
    }

    private void validateData() {

        String orgname = et_orgname.getText().toString();
        String contact = et_contact.getText().toString();
        String head = et_head.getText().toString();
        String address = et_address.getText().toString();
        String password = et_password.getText().toString();
        String confirmpass = et_confirm_pass.getText().toString();

        if (TextUtils.isEmpty(orgname)) {
            showToast("Please enter organization's name");
        }

        else if(TextUtils.isEmpty(contact)) {
            showToast("Please enter your contact number");
        }

        else if (contact.length() < 10) {
            showToast("Please enter a valid contact number");
        }

        else if (TextUtils.isEmpty(head)) {
            showToast("Please enter the name of the organization's head");
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

            Intent intent = new Intent(OrgSignUpActivity.this , OrgHomeActivity.class);
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
            startActivity(intent);

      }

    }

    private void showToast(String mess) {
        Helper.showToast(this, mess);
    }
}
