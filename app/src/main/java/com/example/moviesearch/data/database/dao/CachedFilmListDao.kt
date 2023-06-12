package com.example.moviesearch.data.database.dao

import androidx.paging.PagingSource
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.moviesearch.data.database.model.FilmDbModel

@Dao
interface CachedFilmListDao {

    @Query("SELECT * FROM film_list")
    fun getMoviesList(): PagingSource<Int, FilmDbModel>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertMultipleFilms(list: List<FilmDbModel>)

    @Query("DELETE FROM film_list")
    suspend fun clearRepos()

    @Query("SELECT COUNT(filmId) from film_list")
    suspend fun count(): Int

    @Query("SELECT EXISTS(SELECT * FROM film_list WHERE filmId == :id)")
    suspend fun isRowExist(id : Int) : Boolean
}