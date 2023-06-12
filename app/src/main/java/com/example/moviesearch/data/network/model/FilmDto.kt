package com.example.moviesearch.data.network.model

data class FilmDto (
    val filmId: Int,
    val nameRu: String?,
    val year: String?,
    val genres: List<GenresInfoDto>,
    val posterUrl: String,
    val posterUrlPreview: String
)