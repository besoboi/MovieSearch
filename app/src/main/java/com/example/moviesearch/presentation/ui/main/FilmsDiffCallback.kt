package com.example.moviesearch.presentation.ui.main

import androidx.recyclerview.widget.DiffUtil
import com.example.moviesearch.domain.entities.Film

class FilmsDiffCallback : DiffUtil.ItemCallback<Film>() {
    override fun areItemsTheSame(oldItem: Film, newItem: Film): Boolean {
        return oldItem.filmId == newItem.filmId
    }

    override fun areContentsTheSame(oldItem: Film, newItem: Film): Boolean {
        return oldItem == newItem
    }
}