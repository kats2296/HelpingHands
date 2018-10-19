package com.helpinghands.retrofit;

import com.helpinghands.retrofit.requests.CreateEventRequest;
import com.helpinghands.retrofit.requests.CreateRequest;
import com.helpinghands.retrofit.requests.OrgLoginRequest;
import com.helpinghands.retrofit.requests.OrgSignupRequest;
import com.helpinghands.retrofit.requests.VolSignupRequest;
import com.helpinghands.retrofit.response.CreatEventResponse;
import com.helpinghands.retrofit.response.OrgLoginResponse;
import com.helpinghands.retrofit.response.OrgSignupResponse;
import com.helpinghands.retrofit.response.VolLoginResponse;
import com.helpinghands.retrofit.response.VolSignResponse;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;

public interface APIService {

    // creation of org as well as vol
    @POST("organisation/create/")
    Call<String> createOrg(@Body CreateRequest request);

    @POST("organisation/signup/")
    Call<OrgSignupResponse> org_signup(@Body OrgSignupRequest request);

    @POST("organisation/login/")
    Call<OrgLoginResponse> org_login(@Body OrgLoginRequest request);

    @POST("volunteer/signup/")
    Call<VolSignResponse> vol_signup(@Body VolSignupRequest request);

    @POST("volunteer/login/")
    Call<VolLoginResponse> vol_login(@Body OrgLoginRequest request);

    @POST("organisation/event/create")
    Call<CreatEventResponse> create_event(@Body CreateEventRequest request);

}