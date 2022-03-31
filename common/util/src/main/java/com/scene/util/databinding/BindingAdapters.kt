package com.scene.util.databinding

import android.widget.ImageView
import androidx.databinding.BindingAdapter
import com.scene.util.R
import com.squareup.picasso.Picasso

const val PREFIX_IMAGE_URL = "https://image.tmdb.org/t/p/w500/"

@BindingAdapter("imageUrl")
fun loadImage(imageView: ImageView, imageUrl: String?) {
    Picasso.get().load("${PREFIX_IMAGE_URL}$imageUrl")
        .placeholder(R.drawable.ic_tv_show_place_holder)
        .into(imageView)
}
