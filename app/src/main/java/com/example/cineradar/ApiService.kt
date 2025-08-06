package com.example.cineradar

import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface ApiService {
    @GET("search/movie")
    fun searchMovies(
        @Query("query") query: String,
        @Query("api_key") apiKey: String = "fb9dd40243e7c4cae790e92b95a376fa"
    ): Call<MovieResponse>
}