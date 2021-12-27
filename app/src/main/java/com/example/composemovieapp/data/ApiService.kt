package com.example.composemovieapp.data

import com.example.composemovieapp.model.MovieCast
import com.example.composemovieapp.model.MovieDetail
import com.example.composemovieapp.model.MovieResponse
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface ApiService {

    @GET("movie/now_playing")
    suspend fun getNowPlayingMovies(@Query("api_key") apiKey: String): MovieResponse

    @GET("movie/upcoming")
    suspend fun getUpComingMovies(@Query("api_key") apiKey: String): MovieResponse

    @GET("movie/{movie_id}")
    suspend fun getMovieDetails(
        @Path("movie_id") movie_id: String,
        @Query("api_key") apiKey: String,
    ): MovieDetail

    @GET("movie/{movie_id}/credits")
    suspend fun getMovieCast(
        @Path("movie_id") movie_id: String,
        @Query("api_key") apiKey: String
    ): MovieCast
}