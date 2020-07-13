package com.demo.tvshows.ui.tvshows

import android.os.Bundle
import android.view.View
import androidx.core.os.bundleOf
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.demo.tvshows.R
import com.demo.tvshows.ui.base.BaseFragment
import com.demo.tvshows.ui.tvshows.tvshowdetail.TvShowDetailFragment
import com.demo.tvshows.ui.tvshows.tvshowdetail.TvShowDetailFragment.Companion.ARG_TV_SHOW_ID
import com.demo.tvshows.util.PagingScrollListener
import com.squareup.picasso.Picasso
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.fragment_tv_shows.tvShowsRecyclerView
import javax.inject.Inject

@AndroidEntryPoint
class TvShowsFragment : BaseFragment(R.layout.fragment_tv_shows) {

    @Inject
    lateinit var picasso: Picasso

    private lateinit var tvShowsListAdapter: TvShowsListAdapter

    private val tvShowsViewModel by viewModels<TvShowsViewModel>()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setToolbarTitle(getString(R.string.title_show_shows_activity))
        initAdapter()
        tvShowsRecyclerView.init()
        tvShowsViewModel.getTvShows()
        observeViewModel()
    }

    private fun observeViewModel() {
        with(tvShowsViewModel) {
            showTvShows.observe(viewLifecycleOwner, Observer {
                tvShowsListAdapter.showTvShows(it)
            })
            onError.observe(viewLifecycleOwner, Observer {
                onError(it) { tvShowsViewModel.getTvShows() }
            })
        }
    }

    private fun showTvShowDetailFragment(tvShowId: Int) {
        val tvShowDetailFragment = TvShowDetailFragment().apply {
            arguments = bundleOf(ARG_TV_SHOW_ID to tvShowId)
        }
        addFragment(tvShowDetailFragment, TvShowDetailFragment.TAG, addToBackStack = true)
    }

    private fun initAdapter() {
        tvShowsListAdapter = TvShowsListAdapter(picasso, ::showTvShowDetailFragment)
    }

    private fun RecyclerView.init() {
        layoutManager = LinearLayoutManager(requireContext(), RecyclerView.VERTICAL, false)
        setPagingScrollListener()
        adapter = tvShowsListAdapter
    }

    private fun RecyclerView.setPagingScrollListener() {
        val layoutManager = layoutManager as LinearLayoutManager
        val tvShowsPagingScrollListener = object : PagingScrollListener(layoutManager) {
            override fun loadMoreItems() {
                tvShowsViewModel.getTvShows(loadMore = true)
            }

            override val canLoadMore: Boolean
                get() = tvShowsViewModel.canLoadMore
        }
        addOnScrollListener(tvShowsPagingScrollListener)
    }

    companion object {
        const val TAG_TV_SHOWS_FRAGMENT = "tvShowsFragment"
    }
}
