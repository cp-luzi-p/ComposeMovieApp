package com.example.composemovieapp.repository

import android.util.Log
import com.example.composemovieapp.data.ApiService
import com.example.composemovieapp.data.Source
import com.example.composemovieapp.model.MovieCast
import com.example.composemovieapp.model.MovieDetail
import com.example.composemovieapp.model.MovieResponse
import kotlinx.coroutines.DelicateCoroutinesApi
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import okio.IOException
import javax.inject.Inject


@DelicateCoroutinesApi
class AppRepositoryImpl @Inject constructor(private val apiService: ApiService) : AppRepository {

    override suspend fun getUpComingMovies(apiKey: String): Flow<Source<MovieResponse>> {
        return flow {
            try {
                val result = apiService.getUpComingMovies(apiKey = apiKey)
                if (result.results.isEmpty()) {
                    emit(Source.empty<MovieResponse>())
                } else {
                    emit(Source.success(result))
                }
            } catch (e: Exception) {
                if (e is IOException) {
                    emit(Source.error("No Internet connection", null))
                } else {
                    emit(Source.error("Something went wrong....", null))
                }
            }
        }
    }

    override suspend fun getPlayingNowMovies(apiKey: String): Flow<Source<MovieResponse>> {
        return flow {
            try {
                val result = apiService.getNowPlayingMovies(apiKey = apiKey)
                if (result.results.isEmpty()) {
                    emit(Source.empty<MovieResponse>())
                } else {
                    emit(Source.success(result))
                }
            } catch (e: Exception) {
                if (e is IOException) {
                    emit(Source.error("No Internet Connection", null))
                } else {
                    Log.d("API ERROR", e.localizedMessage)
                    emit(Source.error("Something Went Wrong...", null))
                }
            }
        }
    }

    override suspend fun getMoviesDetails(
        apiKey: String,
        movieId: String
    ): Flow<Source<MovieDetail>> {
        return flow {
            try {
                val result = apiService.getMovieDetails(movie_id = movieId, apiKey = apiKey)
                emit(Source.success(result))
            } catch (e: Exception) {
                if (e is IOException) {
                    emit(Source.error("No Internet Connection", null))
                } else {
                    Log.d("Movies-Details-Error", e.localizedMessage.toString())
                    emit(Source.error("Something went wrong...", null))
                }
            }
        }
    }

    override suspend fun getMoviesCast(apiKey: String, movieId: String): Flow<Source<MovieCast>> {
        return flow {
            try {
                val result = apiService.getMovieCast(movie_id = movieId, apiKey = apiKey)
                emit(Source.success(result))
            } catch (e: Exception) {
                if(e is IOException) {
                    emit(Source.error("No Internet Connection", null))
                } else {
                    Log.d("Movies-Cast-Error", e.localizedMessage.toString())
                    emit(Source.error("Something went wrong...", null))
                }
            }
        }
    }
}