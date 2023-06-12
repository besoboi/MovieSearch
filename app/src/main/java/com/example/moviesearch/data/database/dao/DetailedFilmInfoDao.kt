package com.example.moviesearch.data.database.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.moviesearch.data.database.model.FilmDetailedInfoDbModel

@Dao
interface DetailedFilmInfoDao {
    @Query("SELECT * FROM film_detailed_info WHERE kinopoiskId == :id LIMIT 1")
    suspend fun getDetailedInfoAboutFilm(id : Int): FilmDetailedInfoDbModel

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertFilmDetailedInfo(filmDetailedInfo: FilmDetailedInfoDbModel)

    @Query("DELETE FROM film_detailed_info WHERE kinopoiskId == :id")
    suspend fun deleteFilmDetailedInfo(id : Int)
}