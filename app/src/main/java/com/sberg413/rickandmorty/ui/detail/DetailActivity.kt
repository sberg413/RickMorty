package com.sberg413.rickandmorty.ui.detail

import android.os.Bundle
import android.widget.ImageView
import android.widget.TextView
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.BindingAdapter
import androidx.databinding.DataBindingUtil
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.CircleCrop
import com.sberg413.rickandmorty.R
import com.sberg413.rickandmorty.databinding.DetailActivityBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class DetailActivity: AppCompatActivity() {

    private val detailViewModel : DetailViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.detail_activity)
        title = "Character Detail"

        val binding: DetailActivityBinding = DataBindingUtil.setContentView(
            this, R.layout.detail_activity)

        intent.getStringExtra("id")?.let { id ->

            detailViewModel.initCharacterId(id)

            binding.viewmodel = detailViewModel
            binding.lifecycleOwner = this
            binding.executePendingBindings()
        }

    }

    companion object {

        @JvmStatic
        @BindingAdapter("characterImage")
        fun loadImage(view: ImageView, image: String?) {
            if (image != null) {
                Glide.with(view.context)
                    .load(image)
                    .transform(CircleCrop())
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
}