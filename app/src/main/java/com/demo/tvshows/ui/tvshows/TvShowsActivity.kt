package com.demo.tvshows.ui.tvshows

import android.os.Bundle
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProviders
import com.demo.tvshows.R
import com.demo.tvshows.ui.base.BaseActivity
import javax.inject.Inject

class TvShowsActivity : BaseActivity() {

    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory

    private lateinit var tvShowsViewModel: TvShowsViewModel

    override fun getContentView(): Int = R.layout.activity_tv_shows

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        tvShowsViewModel = ViewModelProviders.of(this, viewModelFactory)[TvShowsViewModel::class.java]

        tvShowsViewModel.getTvShows()
        observeViewModel()
    }

    private fun observeViewModel() {
        tvShowsViewModel.showTvShows.observe(this, Observer {
            Toast.makeText(this, it.first().name, Toast.LENGTH_SHORT).show()
        })
    }
}
