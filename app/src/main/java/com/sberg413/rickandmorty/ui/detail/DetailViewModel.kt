package com.sberg413.rickandmorty.ui.detail

import android.util.Log
import android.widget.ImageView
import android.widget.TextView
import androidx.databinding.BindingAdapter
import androidx.lifecycle.*
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.CircleCrop
import com.sberg413.rickandmorty.repository.CharacterRepository
import com.sberg413.rickandmorty.models.Character

class DetailViewModel(id: String): ViewModel() {

    var characterData: LiveData<Character>

    private val cdObserver = Observer<Character> {
        updateLocation(it.location.url)
    }

    private val characterRepository: CharacterRepository by lazy {
        CharacterRepository()
    }

    init {
        characterData = characterRepository.getCharacterDetailLiveData(id)
        characterData.observeForever(cdObserver)
    }

    override fun onCleared() {
        characterData.removeObserver(cdObserver)
        super.onCleared()
    }

    private fun updateLocation(locationUrl: String) {
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