package com.sberg413.rickandmorty.ui.detail

import android.util.Log
import android.widget.ImageView
import android.widget.TextView
import androidx.databinding.BindingAdapter
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.CircleCrop
import com.sberg413.rickandmorty.repository.CharacterRepository
import com.sberg413.rickandmorty.models.Character
import com.sberg413.rickandmorty.models.Location

class DetailViewModel(id: String): ViewModel() {

    var characterData: LiveData<Character>

    private val characterRepository: CharacterRepository by lazy {
        CharacterRepository()
    }

    init {
        characterData = characterRepository.getCharacterDetailLiveData(id)
    }

    fun updateLocation(locationUrl: String, owner: LifecycleOwner) {
        val locationId = locationUrl.replace(Regex(".*/"), "")
        Log.d(TAG, "location id = $locationId")
        characterData.value?.locationDetails = characterRepository.getLocationLiveData(locationId)
    }

    companion object {
        private const val TAG = "DetailViewModel"

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