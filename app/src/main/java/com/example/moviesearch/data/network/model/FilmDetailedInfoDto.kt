package com.example.moviesearch.data.network.model

data class FilmDetailedInfoDto (
    val kinopoiskId: Int,
    val nameRu: String,
    val posterUrl: String,
    val year: Int,
    val description: String,
    val countries: List<CountryDto>,
    val genres: List<GenresInfoDto>,
    val lastSync: String
)

