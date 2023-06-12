package com.example.moviesearch.di

import android.app.Application
import com.example.moviesearch.presentation.FilmApp
import com.example.moviesearch.presentation.FilmDetailActivity
import com.example.moviesearch.presentation.FilmListActivity
import com.example.moviesearch.presentation.ui.main.FilmDetailFragment
import com.example.moviesearch.presentation.ui.main.FilmListFragment
import dagger.BindsInstance
import dagger.Component

@ApplicationScope
@Component(
    modules = [DataModule::class, ViewModelModule::class]
)
interface ApplicationComponent {

    fun inject(fragment: FilmListFragment)

    fun inject(fragment: FilmDetailFragment)

    fun inject(application: FilmApp)

    fun inject(activity: FilmListActivity)

    fun inject(activity: FilmDetailActivity)

    @Component.Factory
    interface Factory {

        fun create(
            @BindsInstance application: Application
        ): ApplicationComponent
    }
}