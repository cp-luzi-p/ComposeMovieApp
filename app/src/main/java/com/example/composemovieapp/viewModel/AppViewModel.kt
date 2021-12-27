package com.example.composemovieapp.viewModel

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.composemovieapp.data.Source
import com.example.composemovieapp.data.Status
import com.example.composemovieapp.model.MovieCast
import com.example.composemovieapp.model.MovieDetail
import com.example.composemovieapp.model.MovieResponse
import com.example.composemovieapp.repository.AppRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import timber.log.Timber
import javax.inject.Inject

@HiltViewModel
class AppViewModel @Inject constructor(
    private val appRepository: AppRepository
) : ViewModel() {

    private val upComingMovies =
        MutableStateFlow<Source<MovieResponse>>(Source.loading(null))

    val upComeMovie get() = upComingMovies

    fun getUpcomingMovies(apiKey: String) {
        viewModelScope.launch {
            appRepository.getUpComingMovies(apiKey = apiKey).collect {
                when (it.status) {
                    Status.EMPTY -> {
                        upComingMovies.emit(Source.empty())
                    }
                    Status.SUCCESS -> {
                        upComingMovies.emit(Source.success(it.data))
                    }
                    else -> {
                        upComingMovies.emit(Source.error(it.message.toString(), null))
                    }
                }
            }
        }
    }

    private val nowPlayingMovies = MutableStateFlow<Source<MovieResponse>>(Source.loading(null))
    val playNowMovies get() = nowPlayingMovies

    fun getPlayNowMovies(apiKey: String) {
        viewModelScope.launch {
            appRepository.getPlayingNowMovies(apiKey).collect {
                when (it.status) {
                    Status.SUCCESS -> {
                        nowPlayingMovies.emit(Source.success(it.data))
                    }
                    else -> {
                        nowPlayingMovies.emit(Source.error(it.message.toString(), null))
                    }
                }
            }
        }
    }

    private val _moviesDetails = MutableStateFlow<Source<MovieDetail>>(Source.loading(null))
    val moviesDetails get() = _moviesDetails
    fun getMoviesDetails(apiKey: String, movieId: String) {
        viewModelScope.launch {
            appRepository.getMoviesDetails(apiKey, movieId).collect {
                when(it.status) {
                    Status.SUCCESS -> {
                        _moviesDetails.emit(Source.success(it.data))
                    }
                    else -> {
                        _moviesDetails.emit(Source.error(it.message.toString(), null))
                    }
                }
            }
        }
    }


    private val movieCast = MutableStateFlow<Source<MovieCast>>(Source.loading(null))
    val castingMovie get() = movieCast
    fun getMoviesCast(apiKey: String, movieId: String) {
        viewModelScope.launch {
            appRepository.getMoviesCast(apiKey, movieId).collect {
                when (it.status) {
                    Status.SUCCESS -> {
                        movieCast.emit(Source.success(it.data))
                    }
                    else -> {
                        movieCast.emit(Source.error(it.message.toString(), null))
                    }
                }
            }
        }
    }
}