package com.example.act01.models

import com.google.gson.annotations.SerializedName
data class Paises (
    val location: Location,
    val current: Current
)

data class Current (
    val lastUpdatedEpoch: Long,
    val lastUpdated: String,
    val tempC: Long,
    val tempF: Double,
    val isDay: Long,
    val condition: Condition,
    val windMph: Double,
    val windKph: Double,
    val windDegree: Long,
    val windDir: String,
    val pressureMB: Long,
    val pressureIn: Double,
    val precipMm: Long,
    val precipIn: Long,
    val humidity: Long,
    val cloud: Long,
    val feelslikeC: Long,
    val feelslikeF: Double,
    val visKM: Long,
    val visMiles: Long,
    val uv: Long,
    val gustMph: Long,
    val gustKph: Double
)

data class Condition (
    val text: String,
    val icon: String,
    val code: Long
)

data class Location (
    val name: String,
    val region: String,
    val country: String,
    val lat: Double,
    val lon: Double,
    val tzID: String,
    val localtimeEpoch: Long,
    val localtime: String
)


data class ResultResponse(
    @SerializedName("results")
    val results: List<Movie>
)

data class Movie(
    var title: String,
    var adult: Boolean = false,

    @SerializedName("backdrop_path")
    var backdropPath: String = "",

    @SerializedName("genre_ids")
    var genreIDS: List<Long> = emptyList(),

    var id: Long = 0,

    @SerializedName("original_language")
    var originalLanguage: String = "",

    @SerializedName("original_title")
    var originalTitle: String = "",

    var overview: String = "",
    var popularity: Double = 0.0,

    @SerializedName("poster_path")
    var posterPath: String = "",

    @SerializedName("release_date")
    var releaseDate: String = "",

    var video: Boolean = false,

    @SerializedName("vote_average")
    var voteAverage: Double = 0.0,

    @SerializedName("vote_count")
    var voteCount: Long = 0,

    var favorite: Boolean = false,
)


