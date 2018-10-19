package com.helpinghands.database;

import android.content.Context;
import android.content.SharedPreferences;

public class StoreUserData {


    private static SharedPreferences sharedPreferences ;
    private static SharedPreferences.Editor editor;

    public static StoreUserData getInstance(Context context){
        sharedPreferences=context.getSharedPreferences("HelpingHands",Context.MODE_PRIVATE);
        editor=sharedPreferences.edit();

        return new StoreUserData();
    }

    public void setLogin(int val) {
        editor.putInt("login_val" , val);
        editor.apply();
    }

    public int getLogin() {
        return sharedPreferences.getInt("login_val" , 0);
    }

    public void clear_data() {
        editor.clear();
        editor.commit();
    }

    public void setEmail(String email) {

        editor.putString("email" , email);
        editor.apply();
    }

    public String getEmail() {

        return sharedPreferences.getString("email", "");
    }

    public void setEmailSent(int val) {
        editor.putInt("email_sent_val" , val);
        editor.apply();
    }

    public int getEmailSent() {
        return sharedPreferences.getInt("email_sent_val" , 0);
    }


}
