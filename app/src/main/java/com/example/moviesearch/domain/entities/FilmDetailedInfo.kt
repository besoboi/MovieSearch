package com.example.moviesearch.domain.entities

data class FilmDetailedInfo(
    val kinopoiskId: Int,
    val nameRu: String,
    val posterUrl: String,
    val year: Int,
    val description: String,
    val countries: List<Country>,
    val genres: List<GenresInfo>,
    val lastSync: String
)