package com.demo.tvshows.ui.tvshows

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.paging.LoadState
import androidx.paging.LoadStateAdapter
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.demo.tvshows.R
import kotlinx.android.extensions.LayoutContainer

class TvShowsLoadStateAdapter : LoadStateAdapter<LoadingViewHolder>() {

    override fun onBindViewHolder(holder: LoadingViewHolder, loadState: LoadState) {

    }

    override fun onCreateViewHolder(parent: ViewGroup, loadState: LoadState): LoadingViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_loading, parent, false)
        return LoadingViewHolder(view)
    }
}

class LoadingViewHolder(override val containerView: View) : ViewHolder(containerView), LayoutContainer
