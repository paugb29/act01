    package com.example.act01.server

    import com.example.act01.models.Movie
    import retrofit2.Response
    import retrofit2.http.Body
    import retrofit2.http.DELETE
    import retrofit2.http.GET
    import retrofit2.http.POST
    import retrofit2.http.PUT
    import retrofit2.http.Path

    interface RetrofitEndPoints {

        @GET("movies")
        suspend fun listMovies(): Response<List<Movie>>

        @POST("movies")
        suspend fun newMovie(@Body movie: Movie?)

        @PUT("movies/{id}")
        suspend fun updateMovie(@Path("id") id: Long, @Body movie: Movie?)

        @DELETE("movies/{id}")
        suspend fun deleteMovie(@Path("id") id: Long)

        @GET("movies?favorite=true")
        suspend fun listMoviesFav(): Response<List<Movie>>

        //ordenar por titulo ascendente
        @GET("movies?_sort=title&_order=asc")
        suspend fun listMoviesAsc(): Response<List<Movie>>
        //ordenar por titulo descendente
        @GET("movies?_sort=title&_order=desc")
        suspend fun listMoviesDesc(): Response<List<Movie>>

    }