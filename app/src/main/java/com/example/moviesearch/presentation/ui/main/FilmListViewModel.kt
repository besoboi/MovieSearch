package com.example.moviesearch.presentation.ui.main

import android.content.Context
import android.widget.Toast
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.map
import androidx.lifecycle.viewModelScope
import androidx.paging.ExperimentalPagingApi
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.example.moviesearch.R
import com.example.moviesearch.domain.entities.Film
import com.example.moviesearch.domain.use_cases.AddFilmToFavouriteUseCase
import com.example.moviesearch.domain.use_cases.CheckIfFavoriteUseCase
import com.example.moviesearch.domain.use_cases.ClearCacheUseCase
import com.example.moviesearch.domain.use_cases.DeleteFavouriteFilmUseCase
import com.example.moviesearch.domain.use_cases.GetDetailedInfoUseCase
import com.example.moviesearch.domain.use_cases.GetFavouriteFilmListUseCase
import com.example.moviesearch.domain.use_cases.GetFilmListUseCase
import com.example.moviesearch.domain.use_cases.InsertFilmDetailedInfoToDbUseCase
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import javax.inject.Inject

class FilmListViewModel @Inject constructor(
    private val getFilmListUseCase: GetFilmListUseCase,
    private val clearCacheUseCase: ClearCacheUseCase,
    private val checkIfFavoriteUseCase: CheckIfFavoriteUseCase,
    private val addFilmToFavouriteUseCase: AddFilmToFavouriteUseCase,
    private val deleteFavouriteFilmUseCase: DeleteFavouriteFilmUseCase,
    private val getFavouriteFilmListUseCase: GetFavouriteFilmListUseCase,
    private val getDetailedInfoUseCase: GetDetailedInfoUseCase,
    private val insertFilmDetailedInfoToDbUseCase: InsertFilmDetailedInfoToDbUseCase
) : ViewModel() {

    private var currentResult: Flow<PagingData<Film>>? = null
    val favouriteFilmsList = getFavouriteFilmListUseCase()

    @ExperimentalPagingApi
    fun searchFilms(): Flow<PagingData<Film>> {
        val newResult: Flow<PagingData<Film>> =
            getFilmListUseCase().cachedIn(viewModelScope)
        currentResult = newResult
        return newResult
    }

    fun clearCache() {
        viewModelScope.launch {
            clearCacheUseCase()
        }
    }

    private val _index = MutableLiveData<Int>()
    val text: LiveData<String> = _index.map {
        "Hello world from section: $it"
    }

    fun setIndex(index: Int) {
        _index.value = index
    }

    suspend fun checkIfFavourite(filmId: Int): Boolean {
        return checkIfFavoriteUseCase(filmId)
    }

    fun addFilmToFavourite(film: Film, context: Context) {
        viewModelScope.launch {
            getDetailedInfoUseCase(film.filmId)
                .catch {
                    Toast.makeText(
                        context,
                        R.string.no_net_text,
                        Toast.LENGTH_LONG
                    ).show()
                }
                .collectLatest {
                    insertFilmDetailedInfoToDbUseCase(it)
                    addFilmToFavouriteUseCase(film)
                }
        }
    }

    fun deleteFavouriteFilm(filmId: Int) {
        viewModelScope.launch {
            deleteFavouriteFilmUseCase(filmId)
        }
    }
}