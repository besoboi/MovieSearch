package com.example.moviesearch.domain.use_cases

import com.example.moviesearch.domain.repository.FilmRepository
import javax.inject.Inject

class CheckIfFavoriteUseCase @Inject constructor(
    private val repository: FilmRepository
) {
    operator suspend fun invoke(filmId: Int) = repository.checkIfFavorite(filmId)
}