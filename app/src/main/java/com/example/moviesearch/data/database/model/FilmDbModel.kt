package com.example.moviesearch.data.database.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.Index
import androidx.room.PrimaryKey
import com.example.moviesearch.domain.entities.GenresInfo

@Entity(tableName = "film_list",
    indices = [Index(value = ["filmId"], unique = true)])
data class FilmDbModel (
    @PrimaryKey(autoGenerate = true)
    val db_id: Int = 0,
    @ColumnInfo(name = "filmId")
    val filmId: Int,
    val nameRu: String?,
    val year: String?,
    val genres: List<GenresInfo>,
    val posterUrl: String,
    val posterUrlPreview: String
)