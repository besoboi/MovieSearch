package com.example.moviesearch.data.database.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.moviesearch.data.database.model.FavouriteFilmDbModel
import kotlinx.coroutines.flow.Flow

@Dao
interface FavouriteFilmListDao {
    @Query("SELECT * FROM favourite_film_list")
    fun getFavouriteMoviesList(): Flow<List<FavouriteFilmDbModel>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertFavouriteMovie(film: FavouriteFilmDbModel)

    @Query("DELETE FROM favourite_film_list WHERE filmId == :id")
    suspend fun deleteFavouriteMovie(id : Int)

    @Query("SELECT EXISTS(SELECT * FROM favourite_film_list WHERE filmId == :id)")
    suspend fun isRowExist(id : Int) : Boolean
}