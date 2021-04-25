package com.demo.tvshows.ui.tvshows

import android.os.Bundle
import android.view.View
import androidx.core.os.bundleOf
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.demo.tvshows.R
import com.demo.tvshows.ui.base.BaseFragment
import com.demo.tvshows.ui.tvshows.event.TvShowsEvent
import com.demo.tvshows.ui.tvshows.search.TvShowsSearchFragment
import com.demo.tvshows.ui.tvshows.tvshowdetail.TvShowDetailFragment
import com.demo.tvshows.ui.tvshows.tvshowdetail.TvShowDetailFragment.Companion.ARG_TV_SHOW_ID
import com.demo.tvshows.util.PagingScrollListener
import com.demo.tvshows.util.ext.observeOnLifecycle
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.fragment_tv_shows.*

@AndroidEntryPoint
class TvShowsFragment : BaseFragment(R.layout.fragment_tv_shows) {

    private lateinit var tvShowsListAdapter: TvShowsListAdapter

    /*synthetic*/ internal val tvShowsViewModel by viewModels<TvShowsViewModel>()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setToolbarTitle(getString(R.string.title_show_shows_activity))
        initAdapter()
        initView()
        tvShowsViewModel.getTvShows()
        observeViewModel()
    }

    private fun observeViewModel() {
        tvShowsViewModel.states.observeOnLifecycle(viewLifecycleOwner, ::render)
        tvShowsViewModel.events.observeOnLifecycle(viewLifecycleOwner, ::onEvent)
    }

    private fun render(tvShowsState: TvShowsState) {
        tvShowsListAdapter.showTvShows(tvShowsState.adapterItems)
    }

    private fun onEvent(tvShowsEvent: TvShowsEvent) {
        when (tvShowsEvent) {
            is TvShowsEvent.FailureEvent -> onError(
                throwable = tvShowsEvent.throwable,
                onPositiveButtonClick = tvShowsViewModel::getTvShows
            )
        }
    }

    private fun showTvShowDetailFragment(tvShowId: Int) {
        val tvShowDetailFragment = TvShowDetailFragment().apply {
            arguments = bundleOf(ARG_TV_SHOW_ID to tvShowId)
        }
        addFragment(tvShowDetailFragment, TvShowDetailFragment.TAG)
    }

    private fun initAdapter() {
        tvShowsListAdapter = TvShowsListAdapter(::showTvShowDetailFragment)
    }

    private fun initView() {
        tvShowsRecyclerView.init()
        searchFloatingActionButton.setOnClickListener {
            addFragment(TvShowsSearchFragment(), TvShowsSearchFragment.TAG)
        }
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
