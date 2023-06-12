package com.example.moviesearch.presentation.ui.main

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.paging.ExperimentalPagingApi
import androidx.paging.map
import com.example.moviesearch.databinding.FragmentFilmListBinding
import com.example.moviesearch.domain.entities.Film
import com.example.moviesearch.presentation.FavouriteFilmsAdapter
import com.example.moviesearch.presentation.FilmApp
import com.example.moviesearch.presentation.FilmDetailActivity
import com.example.moviesearch.presentation.FilmsAdapter
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import javax.inject.Inject

class FilmListFragment : Fragment() {

    private lateinit var viewModel: FilmListViewModel

    private var _binding: FragmentFilmListBinding? = null
    private val binding: FragmentFilmListBinding
        get() = _binding ?: throw RuntimeException("FragmentFilmListBinding is null")

    private val popularFilmsAdapter = FilmsAdapter()
    private val favouriteFilmsAdapter = FavouriteFilmsAdapter()

    private var tabNum = 1

    private var searchJob: Job? = null

    @Inject
    lateinit var viewModelFactory: ViewModelFactory

    private val component by lazy {
        (requireActivity().application as FilmApp).component
    }

    override fun onAttach(context: Context) {
        component.inject(this)
        super.onAttach(context)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel = ViewModelProvider(
            this, viewModelFactory
        )[FilmListViewModel::class.java]
        tabNum = arguments?.getInt(ARG_SECTION_NUMBER) ?: 1
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentFilmListBinding.inflate(inflater, container, false)
        val root = binding.root
        initPopularTab()
        initFavouriteTab()
        return root
    }

    @ExperimentalPagingApi
    private fun startSearchJob() {
        searchJob?.cancel()
        searchJob = lifecycleScope.launch {
            viewModel.searchFilms()
                .collectLatest {
                    popularFilmsAdapter.submitData(
                        it.map {
                            it.copy(isFavourite = viewModel.checkIfFavourite(it.filmId))
                        }
                    )

                }
        }
    }

    @OptIn(ExperimentalPagingApi::class)
    private fun initPopularTab() {
        if (tabNum == 1) {
            binding.rvFilms.adapter = popularFilmsAdapter
            binding.rvFilms.itemAnimator = null
            startSearchJob()
            setupPopularListeners()
        }
    }

    private fun initFavouriteTab() {
        if (tabNum == 2) {
            binding.rvFilms.adapter = favouriteFilmsAdapter
            lifecycleScope.launch {
                repeatOnLifecycle(Lifecycle.State.RESUMED) {
                    viewModel.favouriteFilmsList.collectLatest {
                        favouriteFilmsAdapter.submitList(it)
                    }
                }
            }
            setupFavouriteListeners()
        }
    }

    private fun setupPopularListeners() {
        popularFilmsAdapter.onFilmLongClickListener =
            object : FilmsAdapter.OnFilmLongClickListener {
                override fun onFilmLongClick(film: Film) {
                    if (!film.isFavourite) {
                        viewModel.addFilmToFavourite(film, requireContext())
                    } else {
                        viewModel.deleteFavouriteFilm(film.filmId)
                    }
                }
            }
        popularFilmsAdapter.onClickListener = object : FilmsAdapter.OnFilmClickListener {
            override fun onFilmClick(film: Film) {
                val intent = film.filmId.let {
                    FilmDetailActivity.newIntent(
                        requireContext(),
                        it,
                        modeFromWeb = true
                    )
                }
                startActivity(intent)
            }
        }
    }

    private fun setupFavouriteListeners() {
        favouriteFilmsAdapter.onFilmLongClickListener =
            object : FavouriteFilmsAdapter.OnFilmLongClickListener {
                override fun onFilmLongClick(film: Film) {
                    viewModel.deleteFavouriteFilm(film.filmId)
                }
            }

        favouriteFilmsAdapter.onClickListener = object : FavouriteFilmsAdapter.OnFilmClickListener {
            override fun onFilmClick(film: Film) {
                val intent = film.filmId.let {
                    FilmDetailActivity.newIntent(
                        requireContext(),
                        it,
                        modeFromWeb = false
                    )
                }
                startActivity(intent)
            }
        }
    }

    companion object {
        private const val ARG_SECTION_NUMBER = "section_number"
        @JvmStatic
        fun newInstance(sectionNumber: Int): FilmListFragment {
            return FilmListFragment().apply {
                arguments = Bundle().apply {
                    putInt(ARG_SECTION_NUMBER, sectionNumber)
                }
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}