package com.blablacar.technicaltest.common.ui

import android.widget.ImageView
import androidx.databinding.BindingAdapter
import com.bumptech.glide.Glide

object DataBindingAdapter {
    @BindingAdapter("imageUrl")
    @JvmStatic
    fun setImageUrl(image: ImageView, imageUrl: String) {
        Glide.with(image.context).load(imageUrl).into(image)
    }
}