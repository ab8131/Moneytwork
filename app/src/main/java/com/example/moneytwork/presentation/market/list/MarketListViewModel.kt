package com.example.moneytwork.presentation.market.list

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.moneytwork.core.util.Resource
import com.example.moneytwork.domain.usecase.GetTrendingCoinsUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import javax.inject.Inject

@HiltViewModel
class MarketListViewModel @Inject constructor(
    private val getTrendingCoinsUseCase: GetTrendingCoinsUseCase
) : ViewModel() {

    private val _state = mutableStateOf(MarketListState())
    val state: State<MarketListState> = _state

    init {
        getCoins()
    }

    fun onEvent(event: MarketListEvent) {
        when (event) {
            is MarketListEvent.Refresh -> {
                getCoins(forceRefresh = true)
            }
            is MarketListEvent.NavigateToDetail -> {
                // Navigation will be handled in the UI
            }
        }
    }

    private fun getCoins(forceRefresh: Boolean = false) {
        getTrendingCoinsUseCase(forceRefresh).onEach { result ->
            when (result) {
                is Resource.Success -> {
                    _state.value = MarketListState(coins = result.data ?: emptyList())
                }
                is Resource.Error -> {
                    _state.value = MarketListState(
                        error = result.message ?: "An unexpected error occurred"
                    )
                }
                is Resource.Loading -> {
                    _state.value = MarketListState(isLoading = true)
                }
            }
        }.launchIn(viewModelScope)
    }
}

