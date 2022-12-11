package com.sberg413.rickandmorty.ui.main

import android.util.Log
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

    val isLoading: StateFlow<Boolean> get() = _isLoading
    private val _isLoading = MutableStateFlow(true)

    val characterClicked: StateFlow<Character?> get() = _characterClicked
    private val _characterClicked = MutableStateFlow<Character?>(null)

    val statusFilterFlow: StateFlow<StatusFilter> get() = _statusFilterFlow
    private val _statusFilterFlow =  MutableStateFlow(NoStatusFilter)

    private val _searchFilterFlow =  MutableStateFlow(NoSearchFilter)

    val listData: Flow<PagingData<Character>>
        get() = _listData
    private val _listData: MutableStateFlow<PagingData<Character>> = MutableStateFlow(PagingData.empty())

    init{
        // This starts the collecting of our filter flows
        // and updates the character list accordingly.
        viewModelScope.launch {
            combine(_statusFilterFlow,_searchFilterFlow) { statusFilter, searchFilter ->
                Log.d(TAG, "in combine transfer ...")
                _isLoading.value = true
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
                .collect {
                    _isLoading.value = false
                    _listData.value = it
                }
        }
    }

    fun setSatusFilter(status: String) {
        _statusFilterFlow.value =
            if (status.endsWith("all", true))
                NoStatusFilter
            else StatusFilter(status)
    }

    fun setSearchFilter(search: String?) {
        _searchFilterFlow.value =
            if (search.isNullOrBlank())
                NoSearchFilter
            else SearchFilter(search)
    }

    fun updateStateWithCharacterClicked(character: Character?) {
        _characterClicked.value = character
    }

    companion object {
        private const val TAG = "MainViewModel"
    }

}