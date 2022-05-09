package com.scene.scenedetailpresentation

import android.os.Bundle
import android.view.View
import androidx.fragment.app.viewModels
import com.scene.base.BaseFragment
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.fragment_tv_show_detail.textViewTvShowName

@AndroidEntryPoint
class TvShowDetailFragment : BaseFragment(R.layout.fragment_tv_show_detail) {

    /*synthetic*/ internal val tvShowDetailViewModel by viewModels<TvShowDetailViewModel>()

    private val tvShowId: Int by lazy { requireArguments().getInt(ARG_TV_SHOW_ID) }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setToolbarTitle(tvShowId.toString())

        tvShowDetailViewModel.getTvShowDetail(tvShowId)

        tvShowDetailViewModel.tvShowName.observe(viewLifecycleOwner) {
            textViewTvShowName.text = it
        }
    }

    companion object {
        const val ARG_TV_SHOW_ID = "tvShowId"
        const val TAG = "TvShowDetailFragment"
    }
}
