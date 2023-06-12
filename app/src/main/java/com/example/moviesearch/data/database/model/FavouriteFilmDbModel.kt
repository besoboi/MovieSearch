package com.example.moviesearch.data.database.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.moviesearch.domain.entities.GenresInfo

@Entity(tableName = "favourite_film_list")
data class FavouriteFilmDbModel (
    @PrimaryKey
    val filmId: Int,
    val nameRu: String?,
    val year: String?,
    val genres: List<GenresInfo>,
    val posterUrl: String,
    val posterUrlPreview: String
)