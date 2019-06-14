package com.demo.tvshows.ui.tvshows

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView.Adapter
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.demo.tvshows.R
import com.demo.tvshows.data.remote.response.model.TvShow
import com.demo.tvshows.ui.tvshows.TvShowsListAdapter.AdapterItem.LoadingAdapterItem
import com.demo.tvshows.ui.tvshows.TvShowsListAdapter.AdapterItem.TvShowAdapterItem
import com.squareup.picasso.Picasso
import kotlinx.android.extensions.LayoutContainer
import kotlinx.android.synthetic.main.item_tv_show.logoImageView
import kotlinx.android.synthetic.main.item_tv_show.overviewTextView
import kotlinx.android.synthetic.main.item_tv_show.ratingTextView
import kotlinx.android.synthetic.main.item_tv_show.titleTextView
import javax.inject.Inject

class TvShowsListAdapter @Inject constructor(private val picasso: Picasso) : Adapter<ViewHolder>() {

    private var items = mutableListOf<AdapterItem>()

    fun showTvShows(tvShows: List<TvShowAdapterItem>) {
        hideLoading()
        items.addAll(tvShows)
        notifyItemRangeInserted(items.size, tvShows.size)
    }

    fun toggleLoading(isShowing: Boolean) {
        if (isShowing) {
            showLoading()
        } else {
            hideLoading()
        }
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
                    picasso
                )
            }
            else -> throw IllegalArgumentException("No viewType found at $viewType")
        }
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        if (holder is TvShowViewHolder) {
            val adapterItem = items[position] as TvShowAdapterItem
            holder.bind(adapterItem.tvShow)
        }
    }

    private fun showLoading() {
        items.add(LoadingAdapterItem)
        notifyItemInserted(items.size)
    }

    private fun hideLoading() {
        items.remove(items.find { it is LoadingAdapterItem })
        notifyItemRemoved(items.size)
    }

    class TvShowViewHolder(override val containerView: View, private val picasso: Picasso) : ViewHolder(containerView),
        LayoutContainer {
        fun bind(tvShow: TvShow) {
            picasso.load("https://image.tmdb.org/t/p/w500/${tvShow.posterPath}")
                .placeholder(R.drawable.ic_tv_show_place_holder)
                .into(logoImageView)
            titleTextView.text = tvShow.name
            overviewTextView.text = tvShow.overview
            ratingTextView.text = tvShow.voteAverage.toString()
        }
    }

    class LoadingViewHolder(override val containerView: View) : ViewHolder(containerView), LayoutContainer

    sealed class AdapterItem {
        object LoadingAdapterItem : AdapterItem()
        data class TvShowAdapterItem(val tvShow: TvShow) : AdapterItem()
    }

    companion object {
        private const val VIEW_TYPE_LOADING = 0
        private const val VIEW_TYPE_TV_SHOW = 1
    }
}
