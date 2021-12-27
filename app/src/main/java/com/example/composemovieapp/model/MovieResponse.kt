package com.example.composemovieapp.model

data class MovieResponse(
    val results: List<Movie>
)

data class Movie(
    var id: String,
    var original_language: String,
    var original_title: String,
    var overview: String,
    var poster_path: String,
    var vote_average: String
)