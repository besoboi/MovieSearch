package com.example.moviesearch.di

import android.app.Application
import com.example.moviesearch.data.FilmRepositoryImpl
import com.example.moviesearch.data.database.FilmDatabase
import com.example.moviesearch.data.database.dao.CachedFilmListDao
import com.example.moviesearch.data.database.dao.DetailedFilmInfoDao
import com.example.moviesearch.data.database.dao.FavouriteFilmListDao
import com.example.moviesearch.data.network.api.ApiFactory
import com.example.moviesearch.data.network.api.ApiService
import com.example.moviesearch.domain.repository.FilmRepository
import dagger.Binds
import dagger.Module
import dagger.Provides

@Module
interface DataModule {

    @Binds
    @ApplicationScope
    fun bindFilmRepository(impl: FilmRepositoryImpl): FilmRepository

    companion object {
        @Provides
        @ApplicationScope
        fun provideApiService(): ApiService {
            return ApiFactory()
        }

        @Provides
        @ApplicationScope
        fun provideCachedFilmListDao(
            application: Application
        ): CachedFilmListDao {
            return FilmDatabase.getInstance(application).cachedFilmListDao()
        }

        @Provides
        @ApplicationScope
        fun provideDetailedFilmInfoDao(
            application: Application
        ): DetailedFilmInfoDao {
            return FilmDatabase.getInstance(application).detailedFilmInfoDao()
        }

        @Provides
        @ApplicationScope
        fun provideFavouriteFilmListDao(
            application: Application
        ): FavouriteFilmListDao {
            return FilmDatabase.getInstance(application).favouriteFilmListDao()
        }

        @Provides
        @ApplicationScope
        fun provideDatabase(application: Application): FilmDatabase {
            return FilmDatabase.getInstance(application)
        }
    }
}