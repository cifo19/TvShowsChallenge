package com.demo.tvshows.ui.tvshows

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.demo.tvshows.R
import com.demo.tvshows.data.Constants.PREFIX_IMAGE_URL
import com.demo.tvshows.data.remote.response.model.TvShow
import com.squareup.picasso.Picasso
import kotlinx.android.extensions.LayoutContainer
import kotlinx.android.synthetic.main.item_tv_show.logoImageView
import kotlinx.android.synthetic.main.item_tv_show.overviewTextView
import kotlinx.android.synthetic.main.item_tv_show.ratingTextView
import kotlinx.android.synthetic.main.item_tv_show.titleTextView
import javax.inject.Inject

class TvShowsListAdapter @Inject constructor(
    private val picasso: Picasso
) : PagingDataAdapter<TvShow, ViewHolder>(TV_SHOW_COMPARATOR) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_tv_show, parent, false)
        return TvShowViewHolder(view, picasso)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        if (holder is TvShowViewHolder) {
            val tvShow = getItem(position)
            tvShow?.let(holder::bind)
        }
    }

    class TvShowViewHolder(override val containerView: View, private val picasso: Picasso) : ViewHolder(containerView),
        LayoutContainer {
        fun bind(tvShow: TvShow) {
            picasso.load("$PREFIX_IMAGE_URL${tvShow.posterPath}")
                .placeholder(R.drawable.ic_tv_show_place_holder)
                .into(logoImageView)
            titleTextView.text = tvShow.name
            overviewTextView.text = tvShow.overview
            ratingTextView.text = tvShow.voteAverage.toString()
        }
    }

    companion object {
        private val TV_SHOW_COMPARATOR = object : DiffUtil.ItemCallback<TvShow>() {
            override fun areItemsTheSame(oldItem: TvShow, newItem: TvShow): Boolean =
                oldItem.id == newItem.id

            override fun areContentsTheSame(oldItem: TvShow, newItem: TvShow): Boolean =
                oldItem == newItem
        }
    }
}
