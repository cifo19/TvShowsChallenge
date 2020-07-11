package com.demo.tvshows.ui.tvshows

import android.os.Bundle
import android.view.View
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.demo.tvshows.R
import com.demo.tvshows.di.viewmodel.ViewModelFactory
import com.demo.tvshows.ui.base.BaseFragment
import com.demo.tvshows.util.PagingScrollListener
import kotlinx.android.synthetic.main.fragment_tv_shows.tvShowsRecyclerView
import javax.inject.Inject

class TvShowsFragment : BaseFragment(R.layout.fragment_tv_shows) {

    @Inject
    lateinit var viewModelFactory: ViewModelFactory

    @Inject
    lateinit var tvShowsListAdapter: TvShowsListAdapter

    private val tvShowsViewModel by viewModels<TvShowsViewModel> { viewModelFactory }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setToolbarTitle(getString(R.string.title_show_shows_activity))
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
}
