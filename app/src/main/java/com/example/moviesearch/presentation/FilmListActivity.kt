package com.example.moviesearch.presentation

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import com.example.moviesearch.databinding.ActivityFilmListBinding
import com.example.moviesearch.presentation.ui.main.FilmListFragment
import com.example.moviesearch.presentation.ui.main.FilmListViewModel
import com.example.moviesearch.presentation.ui.main.SectionsPagerAdapter
import com.example.moviesearch.presentation.ui.main.ViewModelFactory
import com.google.android.material.tabs.TabLayoutMediator
import javax.inject.Inject

class FilmListActivity : AppCompatActivity() {

    private lateinit var binding: ActivityFilmListBinding
    private lateinit var viewModel: FilmListViewModel

    @Inject
    lateinit var viewModelFactory: ViewModelFactory

    private val component by lazy {
        (application as FilmApp).component
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        component.inject(this)
        super.onCreate(savedInstanceState)

        viewModel = ViewModelProvider(
            this, viewModelFactory)[FilmListViewModel::class.java]
        viewModel.clearCache()

        binding = ActivityFilmListBinding.inflate(layoutInflater)
        setContentView(binding.root)

        initViewPager()

    }

    private fun initViewPager(){
        val adapter = SectionsPagerAdapter(this)
        adapter.addFragment(FilmListFragment.newInstance(1), POPULAR_TITLE)
        adapter.addFragment(FilmListFragment.newInstance(2), FAVOURITE_TITLE)

        binding.viewPager.adapter = adapter
        binding.viewPager.currentItem = 0
        TabLayoutMediator(binding.tabs, binding.viewPager) { tab, position ->
            tab.text = adapter.getTabTitle(position)
        }.attach()
    }

    companion object {
        private const val POPULAR_TITLE = "Популярные"
        private const val FAVOURITE_TITLE = "Избранное"
    }
}