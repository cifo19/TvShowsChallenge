package com.demo.tvshows.ui.tvshows

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView.Adapter
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.demo.tvshows.R
import com.demo.tvshows.databinding.ItemTvShowBinding
import com.demo.tvshows.ui.tvshows.adapteritem.LoadingAdapterItem
import com.demo.tvshows.ui.tvshows.adapteritem.TvShowAdapterItem
import com.demo.tvshows.util.AdapterItem
import kotlinx.android.extensions.LayoutContainer

class TvShowsListAdapter(private val onTvShowClicked: (Int) -> Unit) : Adapter<ViewHolder>() {

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
            else -> throw IllegalArgumentException("No adapterItem found for position: $position")
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        return when (viewType) {
            VIEW_TYPE_LOADING -> {
                LoadingViewHolder(inflater.inflate(R.layout.item_loading, parent, false))
            }
            VIEW_TYPE_TV_SHOW -> {
                val binding = DataBindingUtil.inflate<ItemTvShowBinding>(
                    inflater, R.layout.item_tv_show, parent, false
                )
                TvShowViewHolder(binding, onTvShowClicked)
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
        private val itemTvShowBinding: ItemTvShowBinding,
        private val onTvShowClicked: (Int) -> Unit
    ) : ViewHolder(itemTvShowBinding.root), LayoutContainer {

        fun bind(tvShowAdapterItem: TvShowAdapterItem) {
            itemTvShowBinding.tvShowAdapterItem = tvShowAdapterItem
            itemTvShowBinding.executePendingBindings()
            containerView.setOnClickListener { onTvShowClicked(tvShowAdapterItem.id) }
        }

        override val containerView: View = itemTvShowBinding.root
    }

    class LoadingViewHolder(override val containerView: View) : ViewHolder(containerView), LayoutContainer

    companion object {
        private const val VIEW_TYPE_LOADING = 0
        private const val VIEW_TYPE_TV_SHOW = 1
    }
}
