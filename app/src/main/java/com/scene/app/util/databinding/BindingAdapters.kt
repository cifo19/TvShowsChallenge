package com.scene.app.util.databinding

import android.widget.ImageView
import androidx.databinding.BindingAdapter
import com.scene.data.Constants
import com.scene.app.R
import com.squareup.picasso.Picasso

@BindingAdapter("imageUrl")
fun loadImage(imageView: ImageView, imageUrl: String?) {
    Picasso.get().load("${Constants.PREFIX_IMAGE_URL}$imageUrl")
        .placeholder(R.drawable.ic_tv_show_place_holder)
        .into(imageView)
}
