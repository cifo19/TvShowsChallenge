package com.demo.tvshows.ui.tvshows

import android.os.Bundle
import com.demo.tvshows.R
import com.demo.tvshows.ui.base.BaseActivity

class TvShowsActivity : BaseActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_tv_shows)

        if (savedInstanceState == null) {
            showTvShowsFragment()
        }
    }

    private fun showTvShowsFragment() {
        supportFragmentManager.beginTransaction()
            .add(R.id.fragmentContainerView, TvShowsFragment())
            .commit()
    }
}
