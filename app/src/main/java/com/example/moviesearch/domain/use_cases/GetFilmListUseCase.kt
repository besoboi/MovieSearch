package com.example.moviesearch.domain.use_cases

import com.example.moviesearch.domain.repository.FilmRepository
import javax.inject.Inject

class GetFilmListUseCase @Inject constructor(
    private val repository: FilmRepository
) {
    operator fun invoke() = repository.getFilmList()
}