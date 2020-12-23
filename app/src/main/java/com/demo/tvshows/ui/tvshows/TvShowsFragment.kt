package com.demo.tvshows.ui.tvshows

import android.os.Bundle
import android.view.View
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.demo.tvshows.R
import com.demo.tvshows.databinding.FragmentTvShowsBinding
import com.demo.tvshows.ui.base.BaseFragment
import com.demo.tvshows.ui.tvshows.search.TvShowsSearchFragment
import com.demo.tvshows.ui.tvshows.tvshowdetail.TvShowDetailFragment
import com.demo.tvshows.util.PagingScrollListener
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.fragment_tv_shows.searchFloatingActionButton
import kotlinx.android.synthetic.main.fragment_tv_shows.tvShowsRecyclerView

@AndroidEntryPoint
class TvShowsFragment : BaseFragment<FragmentTvShowsBinding>(R.layout.fragment_tv_shows) {

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
        with(tvShowsViewModel) {
            showTvShows.observe(viewLifecycleOwner, {
                tvShowsListAdapter.showTvShows(it)
            })
            onError.observe(viewLifecycleOwner, {
                onError(it) { tvShowsViewModel.getTvShows() }
            })
        }
    }

    private fun showTvShowDetailFragment(tvShowId: Int) {
        addFragment(TvShowDetailFragment.newInstance(tvShowId), TvShowDetailFragment.TAG)
    }

    private fun initAdapter() {
        tvShowsListAdapter = TvShowsListAdapter(::showTvShowDetailFragment)
    }

    private fun initView() = with(binding) {
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
