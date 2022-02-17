package com.sberg413.rickandmorty.ui.main

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.sberg413.rickandmorty.models.*
import com.sberg413.rickandmorty.repository.CharacterRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(private val characterRepository: CharacterRepository): ViewModel() {

    val isLoading: LiveData<Boolean> get() = _isLoading
    private val _isLoading = MutableLiveData<Boolean>()

    private val statusFilterFlow =  MutableStateFlow<StatusFilter>(NoStatusFilter)

    private val searchFilterFlow =  MutableStateFlow<SearchFilter>(NoSearchFilter)

    val listData: LiveData<PagingData<Character>>
        get() = _listData
    private val _listData = MutableLiveData<PagingData<Character>>()
//        statusFilterFlow.flatMapLatest{ statusFilter ->
//            characterRepository.getCharacterList(null, statusFilter.status)
//                .cachedIn(viewModelScope)
//                .flowOn(Dispatchers.IO)
//        }.asLiveData()

    init{
        // This starts the collecting of our filter flows
        // and updates the character list accordingly.
        viewModelScope.launch {
            combineTransform<StatusFilter, SearchFilter, PagingData<Character>>(
                statusFilterFlow,
                searchFilterFlow
            ) { statusFilter, searchFilter ->
                Log.d(TAG, "in combine transfer ...")
                updateCharacterList(searchFilter.search, statusFilter.status)
            }.collect()
        }
    }

    private fun updateCharacterList(name: String?, status: String?) {
        viewModelScope.launch {
            _isLoading.value = true
            characterRepository.getCharacterList(name, status)
                .cachedIn(viewModelScope)
                .flowOn(Dispatchers.IO)
                .catch {
                    Log.e(TAG, "updateCharacterList: a network error occurred!")
                }
                .collectLatest {
                    _listData.postValue(it)
                    _isLoading.postValue(false)
                }
        }
    }

    fun setSatusFilter(status: String) {
        statusFilterFlow.value =
            if (status.endsWith("all", true))
                NoStatusFilter
            else StatusFilter(status)
    }

    fun setSearchFilter(search: String?) {
        searchFilterFlow.value =
            if (search.isNullOrBlank())
                NoSearchFilter
            else SearchFilter(search)
    }

    companion object {
        private const val TAG = "MainViewModel"
    }

}