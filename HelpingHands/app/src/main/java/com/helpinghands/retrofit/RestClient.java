package com.helpinghands.retrofit;


import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;


public class RestClient {

    public static final String BASE_URL="https://317a40f9.ngrok.io/";
    private static APIService apiRestInterfaces ;


    public static APIService getClient() {
        HttpLoggingInterceptor interceptor = new HttpLoggingInterceptor();
        interceptor.setLevel(HttpLoggingInterceptor.Level.BODY);


        final OkHttpClient okHttpClient = new OkHttpClient.Builder()
                .readTimeout(180, TimeUnit.SECONDS)
                .addInterceptor(interceptor)
                .connectTimeout(180, TimeUnit.SECONDS)
                .build();


        Gson gson = new GsonBuilder()
                .setLenient()
                .create();


        if (apiRestInterfaces == null) {
            Retrofit client = new Retrofit.Builder()
                    .baseUrl(BASE_URL)
                    .addConverterFactory(GsonConverterFactory.create(gson))
                    .client(okHttpClient)
                    .build();
            apiRestInterfaces = client.create(APIService.class);
        }
        return apiRestInterfaces ;
    }


}
