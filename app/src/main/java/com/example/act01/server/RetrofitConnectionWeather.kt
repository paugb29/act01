package com.example.act01.server

import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.create
import java.util.concurrent.TimeUnit

object RetrofitConnectionWeather {

    /*
    private var okHttpClient: OkHttpClient = OkHttpClient.Builder()
        .connectTimeout(1, TimeUnit.MINUTES)
        .readTimeout(30, TimeUnit.SECONDS)
        .writeTimeout(15, TimeUnit.SECONDS)
        .build()

     */

    private val okHttpClient = HttpLoggingInterceptor().run {
        level = HttpLoggingInterceptor.Level.BODY
        OkHttpClient.Builder()
            .connectTimeout(10,TimeUnit.SECONDS)
            .addInterceptor(this).build()
    }

    private val builder = Retrofit.Builder()
        .baseUrl("https://api.weatherapi.com/v1/")
        .client(okHttpClient)
        .addConverterFactory(GsonConverterFactory.create())
        .build()

    // creamos un objeto retrofit.builder con la interface de endpoints declarada
    val service: RetrofiEndPointWeather = builder.create(RetrofiEndPointWeather::class.java)

}