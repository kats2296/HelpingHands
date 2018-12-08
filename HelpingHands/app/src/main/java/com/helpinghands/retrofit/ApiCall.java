package com.helpinghands.retrofit;


import com.helpinghands.retrofit.requests.CreateEventRequest;
import com.helpinghands.retrofit.requests.CreateRequest;
import com.helpinghands.retrofit.requests.GetDistrictsRequest;
import com.helpinghands.retrofit.requests.GetSuggestedEventRequest;
import com.helpinghands.retrofit.requests.OrgLoginRequest;
import com.helpinghands.retrofit.requests.OrgSignupRequest;
import com.helpinghands.retrofit.requests.VolSignupRequest;
import com.helpinghands.retrofit.response.CreatEventResponse;
import com.helpinghands.retrofit.response.DistrictsResponse;
import com.helpinghands.retrofit.response.LatLngResponse;
import com.helpinghands.retrofit.response.OrgLoginResponse;
import com.helpinghands.retrofit.response.OrgSignupResponse;
import com.helpinghands.retrofit.response.SuggestedEventResponse;
import com.helpinghands.retrofit.response.VolLoginResponse;
import com.helpinghands.retrofit.response.VolSignResponse;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ApiCall {
    private static APIService service;

    public static ApiCall getInstance() {
        if (service == null) {
            service = RestClient.getClient();
        }
        return new ApiCall();
    }

//    public void login(LoginRequest request, final IApiCallback iApiCallback) {
//        Call<LoginResponse> call = service.login(request);
//        call.enqueue(new Callback<LoginResponse>() {
//            @Override
//            public void onResponse(Call<LoginResponse> call, Response<LoginResponse> response) {
//                iApiCallback.onSuccess("login", response);
//            }
//
//            @Override
//            public void onFailure(Call<LoginResponse> call, Throwable t) {
//                iApiCallback.onFailure("" + t.getMessage());
//            }
//        });
//    }

    public void create(CreateRequest request, final IApiCallback iApiCallback) {

        Call<String> call = service.createOrg(request);
        call.enqueue(new Callback<String>() {
            @Override
            public void onResponse(Call<String> call, Response<String> response) {
                iApiCallback.onSuccess("create" , response);
            }

            @Override
            public void onFailure(Call<String> call, Throwable t) {
                iApiCallback.onFailure("" + t.getMessage());

            }
        });
    }

    public void org_register(OrgSignupRequest request, final IApiCallback iApiCallback) {

        Call<OrgSignupResponse> call = service.org_signup(request);
        call.enqueue(new Callback<OrgSignupResponse>() {
            @Override
            public void onResponse(Call<OrgSignupResponse> call, Response<OrgSignupResponse> response) {
                iApiCallback.onSuccess("signup organization" , response);
            }

            @Override
            public void onFailure(Call<OrgSignupResponse> call, Throwable t) {
                iApiCallback.onFailure("" + t.getMessage());

            }
        });
    }

    public void orgLogin(OrgLoginRequest request, final IApiCallback iApiCallback) {

        Call<OrgLoginResponse> call = service.org_login(request);
        call.enqueue(new Callback<OrgLoginResponse>() {
            @Override
            public void onResponse(Call<OrgLoginResponse> call, Response<OrgLoginResponse> response) {
                iApiCallback.onSuccess("login organization" , response);
            }

            @Override
            public void onFailure(Call<OrgLoginResponse> call, Throwable t) {
                iApiCallback.onFailure("" + t.getMessage());

            }
        });
    }

    public void vol_register(VolSignupRequest request, final IApiCallback iApiCallback) {

        Call<VolSignResponse> call = service.vol_signup(request);
        call.enqueue(new Callback<VolSignResponse>() {
            @Override
            public void onResponse(Call<VolSignResponse> call, Response<VolSignResponse> response) {
                iApiCallback.onSuccess("signup organization" , response);
            }

            @Override
            public void onFailure(Call<VolSignResponse> call, Throwable t) {
                iApiCallback.onFailure("" + t.getMessage());

            }
        });
    }

    public void vol_login(OrgLoginRequest request, final IApiCallback iApiCallback) {

        Call<VolLoginResponse> call = service.vol_login(request);
        call.enqueue(new Callback<VolLoginResponse>() {
            @Override
            public void onResponse(Call<VolLoginResponse> call, Response<VolLoginResponse> response) {
                iApiCallback.onSuccess("login volunteer" , response);
            }

            @Override
            public void onFailure(Call<VolLoginResponse> call, Throwable t) {
                iApiCallback.onFailure("" + t.getMessage());

            }
        });
    }

    public void create_event(CreateEventRequest request, final IApiCallback iApiCallback) {

        Call<CreatEventResponse> call = service.create_event(request);
        call.enqueue(new Callback<CreatEventResponse>() {
            @Override
            public void onResponse(Call<CreatEventResponse> call, Response<CreatEventResponse> response) {
                iApiCallback.onSuccess("login volunteer" , response);
            }

            @Override
            public void onFailure(Call<CreatEventResponse> call, Throwable t) {
                iApiCallback.onFailure("" + t.getMessage());

            }
        });
    }

    public void get_districts(GetDistrictsRequest request, final IApiCallback iApiCallback) {

        Call<DistrictsResponse> call = service.get_districts(request);
        call.enqueue(new Callback<DistrictsResponse>() {
            @Override
            public void onResponse(Call<DistrictsResponse> call, Response<DistrictsResponse> response) {
                iApiCallback.onSuccess("get district suggestion" , response);
            }

            @Override
            public void onFailure(Call<DistrictsResponse> call, Throwable t) {
                iApiCallback.onFailure("" + t.getMessage());

            }
        });
    }

    public void get_suggested_event(GetSuggestedEventRequest request, final IApiCallback iApiCallback) {

        Call<SuggestedEventResponse> call = service.get_suggested_event(request);
        call.enqueue(new Callback<SuggestedEventResponse>() {
            @Override
            public void onResponse(Call<SuggestedEventResponse> call, Response<SuggestedEventResponse> response) {
                iApiCallback.onSuccess("get event suggestion" , response);
            }

            @Override
            public void onFailure(Call<SuggestedEventResponse> call, Throwable t) {
                iApiCallback.onFailure("" + t.getMessage());

            }
        });
    }

    public void get_lat_lng(GetSuggestedEventRequest request, final IApiCallback iApiCallback) {

        Call<LatLngResponse> call = service.get_lat_lng(request);
        call.enqueue(new Callback<LatLngResponse>() {
            @Override
            public void onResponse(Call<LatLngResponse> call, Response<LatLngResponse> response) {
                iApiCallback.onSuccess("get lat lng for districts" , response);
            }

            @Override
            public void onFailure(Call<LatLngResponse> call, Throwable t) {
                iApiCallback.onFailure("" + t.getMessage());

            }
        });
    }

}