package com.demo.tvshows.ui.tvshows

import android.os.Bundle
import android.view.View
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.demo.tvshows.R
import com.demo.tvshows.di.viewmodel.ViewModelFactory
import com.demo.tvshows.ui.base.BaseFragment
import kotlinx.android.synthetic.main.fragment_tv_shows.tvShowsRecyclerView
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import javax.inject.Inject

class TvShowsFragment : BaseFragment(R.layout.fragment_tv_shows) {

    @Inject
    lateinit var viewModelFactory: ViewModelFactory

    @Inject
    lateinit var tvShowsListAdapter: TvShowsListAdapter

    private val tvShowsViewModel by viewModels<TvShowsViewModel> { viewModelFactory }

    private var job: Job? = null

    @ExperimentalCoroutinesApi
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setToolbarTitle(getString(R.string.title_show_shows_activity))
        tvShowsRecyclerView.init()
        getTvShows()
        observeViewModel()
    }

    @ExperimentalCoroutinesApi
    private fun observeViewModel() {
        with(tvShowsViewModel) {
            onError.observe(viewLifecycleOwner, Observer {
                onError(it) {
                    job?.cancel()
                    this@TvShowsFragment.getTvShows()
                }
            })
        }
    }

    @ExperimentalCoroutinesApi
    private fun getTvShows() {
        job = lifecycleScope.launch {
            tvShowsViewModel.fetchTvShows().collectLatest { tvShows ->
                tvShowsListAdapter.submitData(tvShows)
            }
        }
    }

    private fun RecyclerView.init() {
        layoutManager = LinearLayoutManager(requireContext(), RecyclerView.VERTICAL, false)
        adapter = tvShowsListAdapter.withLoadStateFooter(TvShowsLoadStateAdapter())
    }
}
