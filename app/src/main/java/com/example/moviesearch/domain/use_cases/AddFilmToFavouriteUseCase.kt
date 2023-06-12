package com.example.moviesearch.domain.use_cases

import com.example.moviesearch.domain.entities.Film
import com.example.moviesearch.domain.repository.FilmRepository
import javax.inject.Inject

class AddFilmToFavouriteUseCase @Inject constructor(
    private val repository: FilmRepository
) {
    suspend operator fun invoke(film: Film) = repository.addFilmToFavourite(film)
}