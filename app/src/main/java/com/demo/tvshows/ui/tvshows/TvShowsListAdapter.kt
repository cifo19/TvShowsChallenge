package com.demo.tvshows.ui.tvshows

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView.Adapter
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.demo.tvshows.Constants.PREFIX_IMAGE_URL
import com.demo.tvshows.R
import com.demo.tvshows.ui.tvshows.TvShowsListAdapter.AdapterItem.LoadingAdapterItem
import com.demo.tvshows.ui.tvshows.TvShowsListAdapter.AdapterItem.TvShowAdapterItem
import com.squareup.picasso.Picasso
import kotlinx.android.extensions.LayoutContainer
import kotlinx.android.synthetic.main.item_tv_show.logoImageView
import kotlinx.android.synthetic.main.item_tv_show.overviewTextView
import kotlinx.android.synthetic.main.item_tv_show.ratingTextView
import kotlinx.android.synthetic.main.item_tv_show.titleTextView

class TvShowsListAdapter constructor(
    private val picasso: Picasso,
    private val onTvShowClicked: (Int) -> Unit
) : Adapter<ViewHolder>() {

    private var items = mutableListOf<AdapterItem>()

    fun showTvShows(tvShows: List<AdapterItem>) {
        val diffUtilCallback = AdapterItemDiffUtilCallback(items, tvShows)
        val diff = DiffUtil.calculateDiff(diffUtilCallback)
        items.clear()
        items.addAll(tvShows)
        diff.dispatchUpdatesTo(this)
    }

    override fun getItemCount() = items.size

    override fun getItemViewType(position: Int): Int {
        return when (items[position]) {
            LoadingAdapterItem -> VIEW_TYPE_LOADING
            is TvShowAdapterItem -> VIEW_TYPE_TV_SHOW
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return when (viewType) {
            VIEW_TYPE_LOADING -> {
                LoadingViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.item_loading, parent, false))
            }
            VIEW_TYPE_TV_SHOW -> {
                TvShowViewHolder(
                    LayoutInflater.from(parent.context).inflate(R.layout.item_tv_show, parent, false),
                    picasso,
                    onTvShowClicked
                )
            }
            else -> throw IllegalArgumentException("No viewType found for $viewType")
        }
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        if (holder is TvShowViewHolder) {
            val adapterItem = items[position] as TvShowAdapterItem
            holder.bind(adapterItem)
        }
    }

    class TvShowViewHolder(
        override val containerView: View,
        private val picasso: Picasso,
        private val onTvShowClicked: (Int) -> Unit
    ) : ViewHolder(containerView), LayoutContainer {
        fun bind(tvShowAdapterItem: TvShowAdapterItem) {
            picasso.load("$PREFIX_IMAGE_URL${tvShowAdapterItem.posterPath}")
                .placeholder(R.drawable.ic_tv_show_place_holder)
                .into(logoImageView)
            titleTextView.text = tvShowAdapterItem.name
            overviewTextView.text = tvShowAdapterItem.overview
            ratingTextView.text = tvShowAdapterItem.voteAverage.toString()
            containerView.setOnClickListener { onTvShowClicked(tvShowAdapterItem.id) }
        }
    }

    class LoadingViewHolder(override val containerView: View) : ViewHolder(containerView), LayoutContainer

    sealed class AdapterItem {
        object LoadingAdapterItem : AdapterItem()
        data class TvShowAdapterItem(
            val id: Int,
            val name: String,
            val overview: String,
            val posterPath: String,
            val voteAverage: Double
        ) : AdapterItem()
    }

    companion object {
        private const val VIEW_TYPE_LOADING = 0
        private const val VIEW_TYPE_TV_SHOW = 1
    }
}
