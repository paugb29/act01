package com.example.act01.server

import com.example.act01.models.Current
import okhttp3.ResponseBody
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface RetrofiEndPointWeather {

    @GET("current.json")
    suspend fun getWeather(@Query("key") key: String,
                           @Query("q") city: String): Response<Current>
}