package com.example.moviesearch.presentation.ui.main

import android.content.Context
import android.graphics.drawable.Drawable
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.GlideException
import com.bumptech.glide.request.RequestListener
import com.example.moviesearch.R
import com.example.moviesearch.databinding.FragmentFilmDetailBinding
import com.example.moviesearch.domain.entities.FilmDetailedInfo
import com.example.moviesearch.presentation.FilmApp
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import java.util.Locale
import javax.inject.Inject

class FilmDetailFragment : Fragment() {

    private lateinit var viewModel: FilmDetailViewModel

    private var _binding: FragmentFilmDetailBinding? = null
    private val binding: FragmentFilmDetailBinding
        get() = _binding ?: throw RuntimeException("FragmentFilmDetailBinding is null")

    @Inject
    lateinit var viewModelFactory: ViewModelFactory

    private var filmId = 0
    private var mode = false

    private val component by lazy {
        (requireActivity().application as FilmApp).component
    }

    override fun onAttach(context: Context) {
        component.inject(this)
        super.onAttach(context)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel = ViewModelProvider(this, viewModelFactory)[FilmDetailViewModel::class.java]

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentFilmDetailBinding.inflate(inflater, container, false)
        val root = binding.root
        parseArgs()
        getData()
        return root
    }

    private fun parseArgs() {
        filmId = arguments?.getInt(EXTRA_ID) ?: 0
        mode = arguments?.getBoolean(EXTRA_MODE) ?: false
    }

    private fun getData() {
        if (mode) {
            lifecycleScope.launch {
                repeatOnLifecycle(Lifecycle.State.RESUMED) {
                    viewModel.getDetailInfo(filmId)
                        .catch {
                            Toast.makeText(
                                requireContext(),
                                R.string.no_net_text,
                                Toast.LENGTH_LONG
                            ).show()
                            requireActivity().finish()
                        }
                        .collectLatest {
                            setupViews(it)
                        }
                }
            }
        } else {
            lifecycleScope.launch {
                repeatOnLifecycle(Lifecycle.State.RESUMED) {
                    viewModel.getDetailedInfoFromDb(filmId)
                        .catch {
                            Toast.makeText(
                                requireContext(),
                                R.string.unknown_error,
                                Toast.LENGTH_LONG
                            ).show()
                            requireActivity().finish()
                        }
                        .collectLatest {
                        setupViews(it)
                    }
                }
            }
        }
    }

    private fun setupViews(film: FilmDetailedInfo) {
        with(binding) {
            tvTitle.text = film.nameRu
            tvDescription.text = film.description
            tvGenres.text = film.genres
                .map { it.genre }
                .filter { it != "" }
                .joinToString(separator = ", ")
                .lowercase()
                .replaceFirstChar { if (it.isLowerCase()) it.titlecase(Locale.ROOT) else it.toString() }
            tvCountries.text = film.countries
                .map { it.country }
                .filter { it != "" }
                .joinToString(separator = ", ")
            Glide.with(this@FilmDetailFragment)
                .load(film.posterUrl)
                .listener(object : RequestListener<Drawable> {
                    override fun onLoadFailed(
                        e: GlideException?,
                        model: Any?,
                        target: com.bumptech.glide.request.target.Target<Drawable>?,
                        isFirstResource: Boolean
                    ): Boolean {
                        pbLoadingDetails.visibility = View.GONE
                        return false
                    }

                    override fun onResourceReady(
                        resource: Drawable?,
                        model: Any?,
                        target: com.bumptech.glide.request.target.Target<Drawable>?,
                        dataSource: com.bumptech.glide.load.DataSource?,
                        isFirstResource: Boolean
                    ): Boolean {
                        pbLoadingDetails.visibility = View.GONE
                        return false
                    }
                })
                .into(ivPoster)

        }

    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    companion object {
        private const val EXTRA_MODE = "mode"
        private const val EXTRA_ID = "id"
        fun newInstance(id: Int, modeFromWeb: Boolean) = FilmDetailFragment().apply {
            arguments = Bundle().apply {
                putInt(EXTRA_ID, id)
                putBoolean(EXTRA_MODE, modeFromWeb)
            }
        }
    }
}