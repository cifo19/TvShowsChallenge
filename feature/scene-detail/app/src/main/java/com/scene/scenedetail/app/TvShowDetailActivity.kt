package com.scene.scenedetail.app

import android.os.Bundle
import androidx.core.os.bundleOf
import com.scene.base.BaseActivity
import com.scene.scenedetailpresentation.TvShowDetailFragment
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class TvShowDetailActivity: BaseActivity() {

    override val fragmentContainerId: Int
        get() = R.id.fragmentContainerView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_tv_show_detail)

        if (savedInstanceState == null) {
            showTvShowDetail()
        }
    }

    private fun showTvShowDetail() {
        val tvShowDetailFragment = TvShowDetailFragment().apply {
            arguments = bundleOf(TvShowDetailFragment.ARG_TV_SHOW_ID to DEFAULT_TV_SHOW_ID)
        }
        addFragment(tvShowDetailFragment, TvShowDetailFragment.TAG, addToBackStack = false)
    }

    companion object {
        private const val DEFAULT_TV_SHOW_ID = 92749
    }
}
