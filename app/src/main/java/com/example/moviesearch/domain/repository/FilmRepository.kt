package com.example.moviesearch.domain.repository

import androidx.paging.PagingData
import com.example.moviesearch.domain.entities.Film
import com.example.moviesearch.domain.entities.FilmDetailedInfo
import kotlinx.coroutines.flow.Flow

interface FilmRepository {
    fun getFilmList(): Flow<PagingData<Film>>
    suspend fun getFilmDetailedInfo(filmId: Int): Flow<FilmDetailedInfo>
    suspend fun getFilmDetailedInfoFromDb(filmId: Int): Flow<FilmDetailedInfo>
    suspend fun insertFilmDetailedInfoToDb(film: FilmDetailedInfo)
    suspend fun deleteFilmDetailedInfoFromDb(id: Int)
    fun getFavouriteFilmList(): Flow<List<Film>>
    suspend fun clearCache()
    suspend fun checkIfFavorite(filmId: Int): Boolean
    suspend fun addFilmToFavourite(film: Film)
    suspend fun deleteFilmFromFavourite(filmId: Int)
}