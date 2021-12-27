package com.example.composemovieapp.model

data class MovieCast(
    var id: String,
    var cast: List<Cast>
)

data class Cast(
    var name: String,
    var profile_path: String?,
    var original_name: String
)