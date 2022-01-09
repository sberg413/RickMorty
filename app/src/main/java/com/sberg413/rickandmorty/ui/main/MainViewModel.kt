package com.sberg413.rickandmorty.ui.main

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.sberg413.rickandmorty.models.Character
import com.sberg413.rickandmorty.repository.CharacterRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(private val characterRepository: CharacterRepository): ViewModel() {

    val isLoading: LiveData<Boolean> get() = _isLoading
    private val _isLoading = MutableLiveData<Boolean>()

    val listData: LiveData<PagingData<Character>> get() = _listData
    private var _listData = MutableLiveData<PagingData<Character>>()

    init{
        updateCharacterList(null)
    }

    fun updateCharacterList(name: String?) {
        viewModelScope.launch {
            _isLoading.value = true
            withContext(Dispatchers.IO) {
                characterRepository.getCharacterList(name).cachedIn(this@launch).apply {
                    collectLatest {
                        _listData.postValue(it)
                        _isLoading.postValue(false)
                    }
                    // cachedIn(this@launch)
                    catch {
                        Log.e(TAG, "updateCharacterList: a network error occurred!")
                    }
                }
            }
        }
    }

    companion object {
        private const val TAG = "MainViewModel"
    }

}