package com.example.composemovieapp.repository

import com.example.composemovieapp.data.Source
import com.example.composemovieapp.model.MovieCast
import com.example.composemovieapp.model.MovieDetail
import com.example.composemovieapp.model.MovieResponse
import kotlinx.coroutines.flow.Flow

interface AppRepository {

    suspend fun getUpComingMovies(apiKey: String): Flow<Source<MovieResponse>>
    suspend fun getPlayingNowMovies(apiKey: String): Flow<Source<MovieResponse>>
    suspend fun getMoviesDetails(apiKey: String, movieId: String): Flow<Source<MovieDetail>>
    suspend fun getMoviesCast(apiKey: String, movieId: String): Flow<Source<MovieCast>>
}