package com.demo.tvshows.ui.tvshows.tvshowdetail

import android.os.Bundle
import android.view.View
import com.demo.tvshows.R
import com.demo.tvshows.ui.base.BaseFragment

class TvShowDetailFragment : BaseFragment(R.layout.fragment_tv_show_detail) {

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setToolbarTitle(requireArguments().getInt(ARG_TV_SHOW_ID).toString())
    }

    companion object {
        const val ARG_TV_SHOW_ID = "tvShowId"
        const val TAG = "TvShowDetailFragment"
    }
}
