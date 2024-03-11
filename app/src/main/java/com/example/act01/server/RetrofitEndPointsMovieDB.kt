package com.example.act01.server

import com.example.act01.models.ResultResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface RetrofitEndPointsMovieDB {
    @GET("movie/popular")
    suspend fun getPopularMovies(): Response<ResultResponse>

    @GET("search/movie")
    suspend fun searchMovies(@Query("query") query: String): Response<ResultResponse>
}