package com.example.moneytwork.presentation.search

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.moneytwork.core.util.Resource
import com.example.moneytwork.domain.model.Coin
import com.example.moneytwork.domain.usecase.SearchCoinsUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SearchViewModel @Inject constructor(
    private val searchCoinsUseCase: SearchCoinsUseCase
) : ViewModel() {

    private val _state = mutableStateOf(SearchState())
    val state: State<SearchState> = _state

    private var searchJob: Job? = null

    fun onSearchQueryChange(query: String) {
        _state.value = _state.value.copy(searchQuery = query)

        searchJob?.cancel()
        searchJob = viewModelScope.launch {
            delay(500) // Debounce
            searchCoins(query)
        }
    }

    private fun searchCoins(query: String) {
        if (query.isBlank()) {
            _state.value = _state.value.copy(searchResults = emptyList(), isLoading = false)
            return
        }

        searchCoinsUseCase(query).onEach { result ->
            when (result) {
                is Resource.Success -> {
                    _state.value = _state.value.copy(
                        searchResults = result.data ?: emptyList(),
                        isLoading = false,
                        error = ""
                    )
                }
                is Resource.Error -> {
                    _state.value = _state.value.copy(
                        error = result.message ?: "Unknown error",
                        isLoading = false
                    )
                }
                is Resource.Loading -> {
                    _state.value = _state.value.copy(isLoading = true)
                }
            }
        }.launchIn(viewModelScope)
    }
}

data class SearchState(
    val searchQuery: String = "",
    val searchResults: List<Coin> = emptyList(),
    val isLoading: Boolean = false,
    val error: String = ""
)

