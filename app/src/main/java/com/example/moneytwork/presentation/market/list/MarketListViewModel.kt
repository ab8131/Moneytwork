package com.example.moneytwork.presentation.market.list

import android.util.Log
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
        Log.d("MarketListViewModel", "ViewModel initialized")
        getCoins()
    }

    fun onEvent(event: MarketListEvent) {
        when (event) {
            is MarketListEvent.Refresh -> {
                Log.d("MarketListViewModel", "Refresh event")
                getCoins(forceRefresh = true)
            }
            is MarketListEvent.NavigateToDetail -> {
                // Navigation will be handled in the UI
            }
            is MarketListEvent.OnTabSelected -> {
                Log.d("MarketListViewModel", "Tab selected: ${event.tabIndex}")
                _state.value = _state.value.copy(selectedTabIndex = event.tabIndex)
                // For now, we'll just show crypto. Stocks will be added later
                if (event.tabIndex == 0) {
                    getCoins(forceRefresh = false)
                } else {
                    // Show empty state for stocks for now
                    _state.value = _state.value.copy(coins = emptyList(), isLoading = false)
                }
            }
        }
    }

    private fun getCoins(forceRefresh: Boolean = false) {
        Log.d("MarketListViewModel", "getCoins called, forceRefresh=$forceRefresh")
        getTrendingCoinsUseCase(forceRefresh).onEach { result ->
            Log.d("MarketListViewModel", "Result received: ${result::class.simpleName}")
            when (result) {
                is Resource.Success -> {
                    Log.d("MarketListViewModel", "Success: ${result.data?.size} coins")
                    _state.value = MarketListState(coins = result.data ?: emptyList())
                }
                is Resource.Error -> {
                    Log.e("MarketListViewModel", "Error: ${result.message}")
                    _state.value = MarketListState(
                        error = result.message ?: "An unexpected error occurred"
                    )
                }
                is Resource.Loading -> {
                    Log.d("MarketListViewModel", "Loading...")
                    _state.value = MarketListState(isLoading = true)
                }
            }
        }.launchIn(viewModelScope)
    }
}

