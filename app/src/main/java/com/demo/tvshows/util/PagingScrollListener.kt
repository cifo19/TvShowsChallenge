package com.demo.tvshows.util

import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

abstract class PagingScrollListener(private val layoutManager: LinearLayoutManager) :
    RecyclerView.OnScrollListener() {

    private var previousTotal = 0
    private var loading = true
    private var firstVisibleItem = 0
    private var visibleItemCount = 0
    private var totalItemCount = 0

    override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
        super.onScrolled(recyclerView, dx, dy)
        if (dy > 0) {

            visibleItemCount = recyclerView.childCount
            totalItemCount = layoutManager.itemCount
            firstVisibleItem = layoutManager.findFirstVisibleItemPosition()

            if (totalItemCount < previousTotal) {
                previousTotal = totalItemCount
                if (totalItemCount == 0) {
                    loading = true
                }
            }
            if (loading && totalItemCount > previousTotal) {
                loading = false
                previousTotal = totalItemCount
            }
            if (!isLastPage &&
                !loading &&
                totalItemCount - visibleItemCount <= firstVisibleItem + VISIBLE_THRESHOLD
            ) {
                loadMoreItems()
                loading = true
            }
        }
    }

    protected abstract fun loadMoreItems()

    abstract val isLastPage: Boolean

    companion object {
        private const val VISIBLE_THRESHOLD = 6
    }
}