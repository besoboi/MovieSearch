package com.example.moviesearch.data.network.paging

import androidx.paging.ExperimentalPagingApi
import androidx.paging.LoadType
import androidx.paging.PagingState
import androidx.paging.RemoteMediator
import androidx.room.withTransaction
import com.example.moviesearch.data.database.FilmDatabase
import com.example.moviesearch.data.database.RemoteKeys
import com.example.moviesearch.data.database.model.FilmDbModel
import com.example.moviesearch.data.mapper.FilmMapper
import com.example.moviesearch.data.network.api.ApiService
import retrofit2.HttpException
import java.io.IOException

@ExperimentalPagingApi
class FilmsRemoteMediator(
    private val service: ApiService,
    private val db: FilmDatabase
) : RemoteMediator<Int, FilmDbModel>() {

    private val mapper = FilmMapper()

    override suspend fun load(
        loadType: LoadType,
        state: PagingState<Int, FilmDbModel>
    ): MediatorResult {
        val key = when (loadType) {
            LoadType.REFRESH -> {
                if (db.cachedFilmListDao().count() > 0) return MediatorResult.Success(false)
                null
            }

            LoadType.PREPEND -> {
                return MediatorResult.Success(endOfPaginationReached = true)
            }

            LoadType.APPEND -> {
                getKey()
            }
        }

        try {
            if (key != null) {
                if (key.isEndReached) return MediatorResult.Success(endOfPaginationReached = true)
            }

            val page: Int = key?.nextKey ?: STARTING_PAGE_INDEX
            val apiResponse = service.getPopularFilms(page = page)

            val filmList = apiResponse.films

            val endOfPaginationReached = page >= MAX_PAGE_COUNT

            db.withTransaction {
                val nextKey = page + 1

                db.remoteKeysDao().insertKey(
                    RemoteKeys(
                        0,
                        nextKey = nextKey,
                        isEndReached = endOfPaginationReached
                    )
                )
                db.cachedFilmListDao().insertMultipleFilms(mapper.mapListFilmDtoToDbModel(filmList))
            }
            return MediatorResult.Success(endOfPaginationReached = endOfPaginationReached)
        } catch (exception: IOException) {
            return MediatorResult.Error(exception)
        } catch (exception: HttpException) {
            return MediatorResult.Error(exception)
        }
    }

    private suspend fun getKey(): RemoteKeys? {
        return db.remoteKeysDao().getKeys().firstOrNull()
    }

    companion object {
        private const val STARTING_PAGE_INDEX = 1
        private const val MAX_PAGE_COUNT = 20
    }

}

