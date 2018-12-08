package com.helpinghands.weather_forecasting;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;

public interface Api {


    @GET("{latlng}?app_id=dbaf1d6c&app_key=14d88196cfe4a6442c5258102ed305e2")
    Call<Data> getData(@Path("latlng") String latlng);

}
