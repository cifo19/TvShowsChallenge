package com.demo.tvshows.ui.tvshows

import android.os.Bundle
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.demo.tvshows.R
import com.demo.tvshows.ui.base.BaseActivity
import com.demo.tvshows.util.PagingScrollListener
import kotlinx.android.synthetic.main.activity_base.activityToolbar
import kotlinx.android.synthetic.main.activity_tv_shows.tvShowsRecyclerView
import javax.inject.Inject

class TvShowsActivity : BaseActivity() {

    @Inject
    lateinit var tvShowsViewModel: TvShowsViewModel
    @Inject
    lateinit var tvShowsListAdapter: TvShowsListAdapter

    override fun getContentView(): Int = R.layout.activity_tv_shows

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        tvShowsRecyclerView.init()
        tvShowsViewModel.getTvShows()
        observeViewModel()

        activityToolbar.setTitle(R.string.app_name)
    }

    private fun observeViewModel() {
        val lifeCycleOwner = this
        with(tvShowsViewModel) {
            showTvShows.observe(lifeCycleOwner, Observer {
                tvShowsListAdapter.showTvShows(it)
            })
            toggleListLoading.observe(lifeCycleOwner, Observer {
                tvShowsListAdapter.toggleLoading(it)
            })
            onError.observe(lifeCycleOwner, Observer {
                onError(it) { tvShowsViewModel.getTvShows() }
            })
        }
    }

    private fun RecyclerView.init() {
        layoutManager = LinearLayoutManager(this@TvShowsActivity, RecyclerView.VERTICAL, false)
        setPagingScrollListener()
        adapter = tvShowsListAdapter
    }

    private fun RecyclerView.setPagingScrollListener() {
        val layoutManager = layoutManager as LinearLayoutManager
        val tvShowsPagingScrollListener = object : PagingScrollListener(layoutManager) {
            override fun loadMoreItems() {
                tvShowsViewModel.getTvShows(loadMore = true)
            }

            override val isLastPage: Boolean
                get() = tvShowsViewModel.hasNextPage.not()
        }
        addOnScrollListener(tvShowsPagingScrollListener)
    }
}
