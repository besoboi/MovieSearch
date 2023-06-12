package com.example.moviesearch.domain.use_cases

import com.example.moviesearch.domain.entities.FilmDetailedInfo
import com.example.moviesearch.domain.repository.FilmRepository
import javax.inject.Inject

class InsertFilmDetailedInfoToDbUseCase @Inject constructor(
    private val repository: FilmRepository
) {
    suspend operator fun invoke(film: FilmDetailedInfo) = repository.insertFilmDetailedInfoToDb(film)
}