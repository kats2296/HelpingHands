package com.helpinghands.retrofit

import io.reactivex.Observable
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.Body
import retrofit2.http.Headers
import retrofit2.http.POST
import java.util.concurrent.TimeUnit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.http.GET


interface HHApiService {


    @Headers("Content-Type: application/json;charset=utf-8")
    @POST("organisation/events/ongoing/")
    fun getOngoignEventList(@Body email: EmailBody): Observable<OngoinglistEvents>

    @Headers("Content-Type: application/json;charset=utf-8")
    @POST("organisation/events/previous/")
    fun getPreviousEventList(@Body email: EmailBody): Observable<PreviouslistEvents>


    @Headers("Content-Type: application/json;charset=utf-8")
    @GET("volunteer/event/ongoing/")
    fun getAllOngoingEventList(): Observable<AllOngoingEvents>


    companion object {

        var retrofit: Retrofit? = null
        fun create(): HHApiService {

            val interceptor = HttpLoggingInterceptor()
            interceptor.level = HttpLoggingInterceptor.Level.BODY

            val client = OkHttpClient.Builder()
                    .addInterceptor(interceptor)
                    .connectTimeout(30, TimeUnit.SECONDS)
                    .readTimeout(30, TimeUnit.SECONDS)
                    .writeTimeout(30, TimeUnit.SECONDS)
                    .build()

            retrofit = Retrofit.Builder()
                    .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                    .addConverterFactory(GsonConverterFactory.create())
                    .baseUrl(RestClient.BASE_URL)
                    .client(client)
                    .build()
            return retrofit!!.create(HHApiService::class.java)
        }

        fun retrofit(): Retrofit {
            return retrofit!!
        }
    }

}