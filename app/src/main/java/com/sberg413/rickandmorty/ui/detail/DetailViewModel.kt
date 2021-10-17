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
import com.sberg413.rickandmorty.models.Location

class DetailViewModel: ViewModel() {

    private val _charId = MutableLiveData<String>()

    private val _characterData = _charId.switchMap { id ->
        characterRepository.getCharacterDetailLiveData(id)
    }

    var characterData: LiveData<Character> = _characterData

    private val _locationData = characterData.switchMap { character ->
        Log.d(TAG, "character = $character")
        val locationId = character.location.url.replace(Regex(".*/"), "")
        characterRepository.getLocationLiveData(locationId)
    }

    var locationData: LiveData<Location> = _locationData

    private val characterRepository: CharacterRepository by lazy {
        CharacterRepository()
    }

    fun initCharacterId(id: String) {
        _charId.value = id
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