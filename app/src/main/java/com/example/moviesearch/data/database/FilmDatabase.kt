package com.example.moviesearch.data.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.example.moviesearch.data.database.dao.CachedFilmListDao
import com.example.moviesearch.data.database.dao.DetailedFilmInfoDao
import com.example.moviesearch.data.database.dao.FavouriteFilmListDao
import com.example.moviesearch.data.database.dao.RemoteKeysDao
import com.example.moviesearch.data.database.model.FavouriteFilmDbModel
import com.example.moviesearch.data.database.model.FilmDbModel
import com.example.moviesearch.data.database.model.FilmDetailedInfoDbModel

@Database(entities = [
    FilmDbModel::class,
    FilmDetailedInfoDbModel::class,
    RemoteKeys::class,
    FavouriteFilmDbModel::class], version = 7, exportSchema = false)
@TypeConverters(Converters::class)
abstract class FilmDatabase : RoomDatabase() {
    companion object {
        private var db: FilmDatabase? = null
        private const val DB_NAME = "movie_search.db"
        private val monitor = Any()

        fun getInstance(context: Context): FilmDatabase {
            synchronized(monitor) {
                db?.let { return it }
                val instance = Room.databaseBuilder(
                    context,
                    FilmDatabase::class.java,
                    DB_NAME
                )
                    .fallbackToDestructiveMigration()
                    .build()
                db = instance
                return instance
            }
        }
    }

    abstract fun cachedFilmListDao() : CachedFilmListDao
    abstract fun favouriteFilmListDao() : FavouriteFilmListDao
    abstract fun detailedFilmInfoDao() : DetailedFilmInfoDao
    abstract fun remoteKeysDao() : RemoteKeysDao
}
