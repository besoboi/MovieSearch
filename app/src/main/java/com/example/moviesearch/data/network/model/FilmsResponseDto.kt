package com.example.moviesearch.data.network.model

data class FilmsResponseDto (
    val pagesCount: Int,
    val films: List<FilmDto>
)

