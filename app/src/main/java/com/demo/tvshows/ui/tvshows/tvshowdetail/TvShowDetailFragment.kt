package com.demo.tvshows.ui.tvshows.tvshowdetail

import android.os.Bundle
import android.view.View
import androidx.core.os.bundleOf
import androidx.fragment.app.viewModels
import com.demo.tvshows.R
import com.demo.tvshows.databinding.FragmentTvShowDetailBinding
import com.demo.tvshows.ui.base.BaseFragment
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class TvShowDetailFragment : BaseFragment<FragmentTvShowDetailBinding>(R.layout.fragment_tv_show_detail) {

    private val tvShowDetailViewModel by viewModels<TvShowDetailViewModel>()
    private val tvShowId by lazy(LazyThreadSafetyMode.NONE) {
        requireArguments().getInt(ARG_TV_SHOW_ID)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setToolbarTitle(tvShowId.toString())
        observeViewModel()
        tvShowDetailViewModel.init(tvShowId)
    }

    private fun observeViewModel() = with(tvShowDetailViewModel) {
        getViewState().observe(viewLifecycleOwner, {
            binding.tvShowDetailViewState = it
            binding.executePendingBindings()
        })
    }

    companion object {

        const val ARG_TV_SHOW_ID = "tvShowId"
        const val TAG = "TvShowDetailFragment"

        fun newInstance(tvShowId: Int): TvShowDetailFragment {
            return TvShowDetailFragment().apply {
                arguments = bundleOf(ARG_TV_SHOW_ID to tvShowId)
            }
        }
    }
}
