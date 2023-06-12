package com.example.moviesearch.presentation

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.moviesearch.databinding.MovieItemBinding
import com.example.moviesearch.domain.entities.Film
import com.example.moviesearch.presentation.ui.main.FilmsDiffCallback
import java.util.Locale

class FilmsAdapter: PagingDataAdapter<Film, FilmsAdapter.FilmsViewHolder>(FilmsDiffCallback()) {

    var onClickListener : OnFilmClickListener? = null
    var onFilmLongClickListener : OnFilmLongClickListener? = null

    inner class FilmsViewHolder(
        val binding: MovieItemBinding
    ) : RecyclerView.ViewHolder(binding.root)

    override fun onBindViewHolder(holder: FilmsViewHolder, position: Int) {
        val film = getItem(position)
        val binding = holder.binding

        if (film != null) {
            binding.tvTitle.text = film.nameRu
            var genreString = film.genres
                .map { it.genre }
                .filter { it != "" }
                .joinToString(separator = ", ")
                .replaceFirstChar { if (it.isLowerCase()) it.titlecase(Locale.ROOT) else it.toString() }
            if (!film.year.isNullOrBlank()) genreString += " (${film.year})"
            binding.tvGenre.text = genreString
            Glide.with(holder.itemView)
                .load(film.posterUrlPreview)
                .into(binding.ivMiniPoster)

            if (film.isFavourite) {
                binding.vStar.visibility = View.VISIBLE
            } else {
                binding.vStar.visibility = View.GONE
            }
            holder.itemView.setOnClickListener {
                onClickListener?.onFilmClick(film)
            }
            holder.itemView.setOnLongClickListener {
                onFilmLongClickListener?.onFilmLongClick(film)
                film.isFavourite = !film.isFavourite
                if (film.isFavourite) {
                    binding.vStar.visibility = View.VISIBLE
                } else {
                    binding.vStar.visibility = View.GONE
                }
                true
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FilmsViewHolder {
        val binding = MovieItemBinding.inflate(
            LayoutInflater.from(parent.context), parent, false
        )

        return FilmsViewHolder(binding)
    }

    interface OnFilmLongClickListener {
        fun onFilmLongClick(film: Film)
    }

    interface OnFilmClickListener {
        fun onFilmClick(film: Film)
    }


}