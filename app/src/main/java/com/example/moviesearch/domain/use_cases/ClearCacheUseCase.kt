package com.example.moviesearch.domain.use_cases

import com.example.moviesearch.domain.repository.FilmRepository
import javax.inject.Inject

class ClearCacheUseCase @Inject constructor(
    private val repository: FilmRepository
) {
    suspend operator fun invoke() = repository.clearCache()
}