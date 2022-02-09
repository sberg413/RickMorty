package com.sberg413.rickandmorty.adapters

import android.widget.ImageView
import android.widget.TextView
import androidx.databinding.BindingAdapter
import com.bumptech.glide.Glide

object BindingAdapters {

    @JvmStatic
    @BindingAdapter("characterImage")
    fun loadImage(view: ImageView, image: String?) {
        if (image != null) {
            Glide.with(view.context)
                .load(image)
                // .transform(CircleCrop())
                .into(view)
        }
    }

    @JvmStatic
    @BindingAdapter("countResidents")
    fun countResidents(view: TextView, residence: List<String>?) {
        if (residence != null) {
            view.text = residence.size.toString()
        }
    }
}