package com.example.moviesearch.data.network.api

import com.example.moviesearch.data.network.model.FilmDetailedInfoDto
import com.example.moviesearch.data.network.model.FilmsResponseDto
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.Path
import retrofit2.http.Query

interface ApiService {
    @Headers(
        "$QUERY_HEADER_API_KEY: $API_KEY_VALUE",
        "Content-Type: application/json"
    )
    @GET("api/v2.2/films/top")
    suspend fun getPopularFilms(
        @Query(QUERY_PARAM_TYPE) type: String = TYPE_VALUE,
        @Query(QUERY_PARAM_PAGE) page: Int = 1
    ) : FilmsResponseDto

    @Headers(
        "$QUERY_HEADER_API_KEY: $API_KEY_VALUE",
        "Content-Type: application/json"
    )
    @GET("api/v2.2/films/{id}")
    suspend fun getFilmDetailedInfo(
        @Path("id") filmId: Int
    ): FilmDetailedInfoDto

    companion object {
        private const val QUERY_PARAM_TYPE = "type"
        private const val TYPE_VALUE = "TOP_100_POPULAR_FILMS"
        private const val QUERY_PARAM_PAGE = "page"
        private const val QUERY_HEADER_API_KEY = "X-API-KEY"
        private const val API_KEY_VALUE = "e30ffed0-76ab-4dd6-b41f-4c9da2b2735b"
    }
}