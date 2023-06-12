package com.example.moviesearch.di

import androidx.lifecycle.ViewModel
import com.example.moviesearch.presentation.ui.main.FilmDetailViewModel
import com.example.moviesearch.presentation.ui.main.FilmListViewModel
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap

@Module
interface ViewModelModule {
    @Binds
    @IntoMap
    @ViewModelKey(FilmListViewModel::class)
    fun bindFilmListViewModel(viewModel: FilmListViewModel): ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(FilmDetailViewModel::class)
    fun bindFilmDetailViewModel(viewModel: FilmDetailViewModel): ViewModel
}