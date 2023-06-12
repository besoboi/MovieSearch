package com.example.moviesearch.presentation.ui.main

import androidx.lifecycle.ViewModel
import com.example.moviesearch.domain.use_cases.GetDetailedInfoFromDbUseCase
import com.example.moviesearch.domain.use_cases.GetDetailedInfoUseCase
import javax.inject.Inject

class FilmDetailViewModel @Inject constructor(
    private val getDetailedInfoFromDbUseCase: GetDetailedInfoFromDbUseCase,
    private val getDetailedInfoUseCase: GetDetailedInfoUseCase
) : ViewModel() {
    suspend fun getDetailInfo(filmId: Int) = getDetailedInfoUseCase(filmId)
    suspend fun getDetailedInfoFromDb(filmId: Int) = getDetailedInfoFromDbUseCase(filmId)
}