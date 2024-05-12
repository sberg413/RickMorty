package com.sberg413.rickandmorty.ui.main

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.sberg413.rickandmorty.models.*
import com.sberg413.rickandmorty.repository.CharacterRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import javax.inject.Inject


data class MainUiState(
    val isLoading: Boolean = false,
    val currentCharacter: Character? = null,
    val characterFilter: CharacterFilter = CharacterFilter(NoStatusFilter, NoSearchFilter),
    val errorMessage: String? = null
)

@OptIn(ExperimentalCoroutinesApi::class)
@HiltViewModel
class MainViewModel @Inject constructor(private val characterRepository: CharacterRepository): ViewModel() {

    val characterClicked: StateFlow<Character?> get() = _characterClicked
    private val _characterClicked = MutableStateFlow<Character?>(null)

    // val characterFilterFlow: StateFlow<CharacterFilter> get() = _characterFilterFlow
    private val _characterFilterFlow =  MutableStateFlow(CharacterFilter(NoStatusFilter, NoSearchFilter))

    @OptIn(FlowPreview::class)
    val listData: StateFlow<PagingData<Character>> = _characterFilterFlow.mapLatest {
        Log.d(TAG, "in combine transfer ...")
        characterRepository.getCharacterList(it.searchFilter.search, it.statusFilter.status)
    }
        .flattenMerge()
        .cachedIn(viewModelScope)
        .catch {
            Log.e(TAG, "updateCharacterList: a network error occurred!")
        }
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), PagingData.empty())



    fun setSatusFilter(status: String) {
        viewModelScope.launch {
            _characterFilterFlow.value = _characterFilterFlow.value.let {
                val value =
                    if (status.endsWith("all", true))
                        NoStatusFilter
                    else StatusFilter(status)
                CharacterFilter(value, it.searchFilter)
            }
        }
    }

    fun setSearchFilter(search: String?) {
        viewModelScope.launch {
            _characterFilterFlow.value = _characterFilterFlow.value.let {
                val value =
                    if (search.isNullOrBlank())
                        NoSearchFilter
                    else SearchFilter(search)
                CharacterFilter(it.statusFilter, value)
            }
        }
    }

    fun updateStateWithCharacterClicked(character: Character?) {
        _characterClicked.value = character
    }

    fun getSelectedStatusIndex(options: Array<String>): Int {
        return _characterFilterFlow.value.statusFilter.status?.let {
            options.indexOf(it)
        } ?: -1
    }

    companion object {
        private const val TAG = "MainViewModel"
    }

}