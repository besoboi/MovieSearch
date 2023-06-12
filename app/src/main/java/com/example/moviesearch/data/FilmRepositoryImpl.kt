package com.example.moviesearch.data

import androidx.paging.ExperimentalPagingApi
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.map
import com.example.moviesearch.data.database.FilmDatabase
import com.example.moviesearch.data.mapper.FilmMapper
import com.example.moviesearch.data.network.api.ApiService
import com.example.moviesearch.data.network.paging.FilmsRemoteMediator
import com.example.moviesearch.domain.entities.Film
import com.example.moviesearch.domain.entities.FilmDetailedInfo
import com.example.moviesearch.domain.repository.FilmRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class FilmRepositoryImpl @Inject constructor(
    private val db: FilmDatabase,
    private val apiService: ApiService,
    private val mapper: FilmMapper
) : FilmRepository {

    private val pagingSourceFactory = { db.cachedFilmListDao().getMoviesList() }

    @OptIn(ExperimentalPagingApi::class)
    override fun getFilmList(): Flow<PagingData<Film>> {
        return Pager(
            config = PagingConfig(
                pageSize = NETWORK_PAGE_SIZE,
                prefetchDistance = 5
            ),
            remoteMediator = FilmsRemoteMediator(
                apiService,
                db
            ),
            pagingSourceFactory = pagingSourceFactory
        ).flow.map { filmDbModelPagingData ->
            filmDbModelPagingData.map {
                mapper.mapFilmDbModelToEntity(it)
            }
        }
    }

    override suspend fun getFilmDetailedInfo(filmId: Int) = flow {
        emit(mapper.mapFilmDetailedInfoDtoToEntity(apiService.getFilmDetailedInfo(filmId)))
    }

    override suspend fun getFilmDetailedInfoFromDb(filmId: Int) = flow {
        emit(mapper.mapFilmDetailedInfoDbModelToEntity(db.detailedFilmInfoDao().getDetailedInfoAboutFilm(filmId)))
    }

    override fun getFavouriteFilmList(): Flow<List<Film>> {
        return db.favouriteFilmListDao().getFavouriteMoviesList().map { mapper.mapListFavouriteFilmDbModelToEntity(it) }
    }

    override suspend fun insertFilmDetailedInfoToDb(film: FilmDetailedInfo) {
        db.detailedFilmInfoDao().insertFilmDetailedInfo(mapper.mapEntityToilmDetailedInfoDbModel(film))
    }

    override suspend fun deleteFilmDetailedInfoFromDb(id: Int) {
        db.detailedFilmInfoDao().deleteFilmDetailedInfo(id)
    }

    override suspend fun clearCache() {
        db.cachedFilmListDao().clearRepos()
    }

    override suspend fun checkIfFavorite(filmId: Int): Boolean {
        return db.favouriteFilmListDao().isRowExist(filmId)
    }

    override suspend fun addFilmToFavourite(film: Film) {
        db.favouriteFilmListDao().insertFavouriteMovie(mapper.mapEntityToFavouriteFilmDbModel(film))
    }

    override suspend fun deleteFilmFromFavourite(filmId: Int) {
        db.favouriteFilmListDao().deleteFavouriteMovie(filmId)
    }

    companion object {
        private const val NETWORK_PAGE_SIZE = 20
    }
}