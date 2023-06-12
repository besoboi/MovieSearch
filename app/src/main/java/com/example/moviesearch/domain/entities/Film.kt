package com.example.moviesearch.domain.entities

data class Film (
    val filmId: Int,
    val nameRu: String?,
    val year: String?,
    val genres: List<GenresInfo>,
    val posterUrl: String,
    val posterUrlPreview: String,
    var isFavourite: Boolean = false
)