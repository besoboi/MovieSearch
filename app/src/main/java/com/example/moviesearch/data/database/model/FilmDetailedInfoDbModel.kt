package com.example.moviesearch.data.database.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.moviesearch.domain.entities.Country
import com.example.moviesearch.domain.entities.GenresInfo

@Entity("film_detailed_info")
data class FilmDetailedInfoDbModel(
    @PrimaryKey
    val kinopoiskId: Int,
    val nameRu: String,
    val posterUrl: String,
    val year: Int,
    val description: String,
    val countries: List<Country>,
    val genres: List<GenresInfo>,
    val lastSync: String
)