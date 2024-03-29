package com.scene.homepresentation

import android.os.Bundle
import com.scene.base.BaseActivity
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class TvShowsActivity : BaseActivity() {

    override val fragmentContainerId: Int
        get() = R.id.fragmentContainerView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_tv_shows)

        if (savedInstanceState == null) {
            showTvShowsFragment()
        }
    }

    private fun showTvShowsFragment() {
        addFragment(TvShowsFragment(), TvShowsFragment.TAG_TV_SHOWS_FRAGMENT, addToBackStack = false)
    }
}
