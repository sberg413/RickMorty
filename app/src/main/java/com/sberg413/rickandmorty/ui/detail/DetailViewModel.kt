package com.sberg413.rickandmorty.ui.detail

import android.widget.ImageView
import androidx.databinding.BindingAdapter
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.CircleCrop
import com.sberg413.rickandmorty.repository.CharacterRepository
import com.sberg413.rickandmorty.ui.models.Character

class DetailViewModel(id: String): ViewModel() {

    var characterData: LiveData<Character>

    init {
        val characterRepository: CharacterRepository by lazy {
            CharacterRepository()
        }

        characterData = characterRepository.getCharacterDetailLiveData(id)
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
    }
}