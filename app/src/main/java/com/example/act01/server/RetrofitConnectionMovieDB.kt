package com.example.act01.server

import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.create

object RetrofitConnectionMovieDB {

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
            .addInterceptor { chain ->
                val original = chain.request()
                val request = original.newBuilder()
                    .header("Authorization","Bearer eyJhbGciOiJIUzI1NiJ9.eyJhdWQiOiI4MWZiOWI1ODI2ZTc4ZDI4ODA5YTRlY2E2ZWE2MDMyYSIsInN1YiI6IjY1ZDRjMThmZWI3OWMyMDE0YjQ0MDgzYyIsInNjb3BlcyI6WyJhcGlfcmVhZCJdLCJ2ZXJzaW9uIjoxfQ.ytF6IHYUxhYmq1zeUWbsj6Zyywv3a0Pbm9pJEey5d8g")
                    .build()
                chain.proceed(request)

            }
            .build()
    }

    private val builder = Retrofit.Builder()
        .baseUrl("https://api.themoviedb.org/3/")
        .client(okHttpClient)
        .addConverterFactory(GsonConverterFactory.create())
        .build()

    // creamos un objeto retrofit.builder con la interface de endpoints declarada
    val service: RetrofitEndPointsMovieDB = builder.create()

}