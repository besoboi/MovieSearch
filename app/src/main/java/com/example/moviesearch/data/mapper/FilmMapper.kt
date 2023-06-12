package com.example.moviesearch.data.mapper

import com.example.moviesearch.data.database.model.FavouriteFilmDbModel
import com.example.moviesearch.data.database.model.FilmDbModel
import com.example.moviesearch.data.database.model.FilmDetailedInfoDbModel
import com.example.moviesearch.data.network.model.CountryDto
import com.example.moviesearch.data.network.model.FilmDetailedInfoDto
import com.example.moviesearch.data.network.model.FilmDto
import com.example.moviesearch.data.network.model.GenresInfoDto
import com.example.moviesearch.domain.entities.Country
import com.example.moviesearch.domain.entities.Film
import com.example.moviesearch.domain.entities.FilmDetailedInfo
import com.example.moviesearch.domain.entities.GenresInfo
import javax.inject.Inject

class FilmMapper @Inject constructor() {
    fun mapFilmDtoToDbModel(dto: FilmDto): FilmDbModel {
        return FilmDbModel(
            filmId = dto.filmId,
            nameRu = dto.nameRu,
            year = dto.year,
            genres = mapGenresListDtoToEntity(dto.genres),
            posterUrl = dto.posterUrl,
            posterUrlPreview = dto.posterUrlPreview
        )
    }

    fun mapFilmDbModelToEntity(dbModel: FilmDbModel): Film {
        return Film(
            filmId = dbModel.filmId,
            nameRu = dbModel.nameRu,
            year = dbModel.year,
            genres = dbModel.genres,
            posterUrl = dbModel.posterUrl,
            posterUrlPreview = dbModel.posterUrlPreview
        )
    }

    fun mapEntityToFavouriteFilmDbModel(film: Film): FavouriteFilmDbModel {
        return FavouriteFilmDbModel(
            filmId = film.filmId,
            nameRu = film.nameRu,
            year = film.year,
            genres = film.genres,
            posterUrl = film.posterUrl,
            posterUrlPreview = film.posterUrlPreview
        )
    }

    fun mapListFilmDtoToDbModel(dto: List<FilmDto>): List<FilmDbModel> {
        return dto.map { mapFilmDtoToDbModel(it) }
    }

    fun mapCountryDtoToEntity(dto: CountryDto): Country {
        return Country(dto.country)
    }

    fun mapCountryListDtoToEntity(dto: List<CountryDto>): List<Country> {
        return dto.map { mapCountryDtoToEntity(it) }
    }

    fun mapGenreDtoToEntity(dto: GenresInfoDto): GenresInfo {
        return GenresInfo(dto.genre)
    }

    fun mapGenresListDtoToEntity(dto: List<GenresInfoDto>): List<GenresInfo> {
        return dto.map { mapGenreDtoToEntity(it) }
    }

    fun mapFilmDetailedInfoDtoToEntity(dto: FilmDetailedInfoDto): FilmDetailedInfo {
        return FilmDetailedInfo(
            kinopoiskId = dto.kinopoiskId,
            nameRu = dto.nameRu,
            posterUrl = dto.posterUrl,
            year = dto.year,
            description = dto.description,
            countries = mapCountryListDtoToEntity(dto.countries),
            genres = mapGenresListDtoToEntity(dto.genres),
            lastSync = dto.lastSync
        )
    }

    fun mapFavouriteFilmDbModelToEntity(dbModel: FavouriteFilmDbModel): Film {
        return Film(
            filmId = dbModel.filmId,
            nameRu = dbModel.nameRu,
            year = dbModel.year,
            genres = dbModel.genres,
            posterUrl = dbModel.posterUrl,
            posterUrlPreview = dbModel.posterUrlPreview
        )
    }

    fun mapListFavouriteFilmDbModelToEntity(dbModel: List<FavouriteFilmDbModel>): List<Film> {
        return dbModel.map { mapFavouriteFilmDbModelToEntity(it) }
    }

    fun mapFilmDetailedInfoDbModelToEntity(dbModel: FilmDetailedInfoDbModel): FilmDetailedInfo {
        return FilmDetailedInfo(
            kinopoiskId = dbModel.kinopoiskId,
            nameRu = dbModel.nameRu,
            posterUrl = dbModel.posterUrl,
            year = dbModel.year,
            description = dbModel.description,
            countries = dbModel.countries,
            genres = dbModel.genres,
            lastSync = dbModel.lastSync
        )
    }

    fun mapEntityToilmDetailedInfoDbModel(entity: FilmDetailedInfo): FilmDetailedInfoDbModel {
        return FilmDetailedInfoDbModel(
            kinopoiskId = entity.kinopoiskId,
            nameRu = entity.nameRu,
            posterUrl = entity.posterUrl,
            year = entity.year,
            description = entity.description,
            countries = entity.countries,
            genres = entity.genres,
            lastSync = entity.lastSync
        )
    }

}