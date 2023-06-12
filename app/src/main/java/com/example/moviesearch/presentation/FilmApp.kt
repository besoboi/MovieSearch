package com.example.moviesearch.presentation

import android.app.Application
import com.example.moviesearch.di.DaggerApplicationComponent

class FilmApp : Application() {
    val component by lazy {
        DaggerApplicationComponent.factory().create(this)
    }
}