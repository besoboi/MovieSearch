package com.example.moviesearch.data.network.paging

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.example.moviesearch.data.network.api.ApiService
import com.example.moviesearch.data.network.model.FilmDto

class FilmsDataSource(private val apiService: ApiService) : PagingSource<Int, FilmDto>() {
    override fun getRefreshKey(state: PagingState<Int, FilmDto>): Int? {
        return state.anchorPosition
    }

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, FilmDto> {
        val page = params.key ?: STARTING_PAGE_INDEX
        return try {
            val response = apiService.getPopularFilms(page = page)
            val films = response.films
            LoadResult.Page(
                data = films,
                prevKey = if (page == STARTING_PAGE_INDEX) null else page - 1,
                nextKey = if (page == MAX_PAGE_COUNT) null else page + 1
            )
        } catch (e: Exception) {
            LoadResult.Error(e)
        }
    }

    companion object {
        private const val STARTING_PAGE_INDEX = 1
        private const val MAX_PAGE_COUNT = 20
    }
}