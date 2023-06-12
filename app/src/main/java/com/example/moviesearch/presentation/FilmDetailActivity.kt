package com.example.moviesearch.presentation

import android.content.Context
import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.view.WindowInsetsController
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.app.AppCompatDelegate
import androidx.core.view.WindowCompat
import androidx.lifecycle.ViewModelProvider
import com.example.moviesearch.R
import com.example.moviesearch.databinding.ActivityFilmDetailBinding
import com.example.moviesearch.presentation.ui.main.FilmDetailFragment
import com.example.moviesearch.presentation.ui.main.FilmDetailViewModel
import com.example.moviesearch.presentation.ui.main.ViewModelFactory
import javax.inject.Inject

class FilmDetailActivity : AppCompatActivity() {

    private lateinit var binding: ActivityFilmDetailBinding
    private lateinit var viewModel: FilmDetailViewModel

    @Inject
    lateinit var viewModelFactory: ViewModelFactory

    private var id = 0
    private var mode = false

    private val component by lazy {
        (application as FilmApp).component
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        component.inject(this)
        super.onCreate(savedInstanceState)
        viewModel = ViewModelProvider(
            this, viewModelFactory
        )[FilmDetailViewModel::class.java]
        binding = ActivityFilmDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)
        parseIntent()
        setupActionBar()
        if (savedInstanceState == null) {
            supportFragmentManager.beginTransaction()
                .replace(R.id.container, FilmDetailFragment.newInstance(id, mode))
                .commitNow()
        }
    }

    private fun parseIntent() {
        if (!intent.hasExtra(EXTRA_ID) || !intent.hasExtra(EXTRA_MODE)) {
            finish()
            return
        }

        id = intent.getIntExtra(EXTRA_ID, 1)
        if (id < 0) {
            finish()
            return
        }
        mode = intent.getBooleanExtra(EXTRA_MODE, false)

    }

    private fun setupActionBar() {
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
        WindowCompat.setDecorFitsSystemWindows(window, false)
        val decorView = this.window.decorView
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
            decorView.windowInsetsController?.setSystemBarsAppearance(
                0,
                WindowInsetsController.APPEARANCE_LIGHT_STATUS_BARS
            )
        }
        supportActionBar?.hide()
    }

    companion object {
        private const val EXTRA_MODE = "mode"
        private const val EXTRA_ID = "id"

        fun newIntent(context: Context, id: Int, modeFromWeb: Boolean): Intent {
            val intent = Intent(context, FilmDetailActivity::class.java)
            intent.putExtra(EXTRA_ID, id)
            intent.putExtra(EXTRA_MODE, modeFromWeb)
            return intent
        }

    }
}