package com.example.moneytwork.presentation.market.detail

import android.util.Log
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.moneytwork.core.constants.Constants
import com.example.moneytwork.core.util.Resource
import com.example.moneytwork.domain.usecase.GetCoinChartUseCase
import com.example.moneytwork.domain.usecase.GetCoinDetailUseCase
import com.example.moneytwork.domain.usecase.ToggleWatchlistUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class DetailViewModel @Inject constructor(
    private val getCoinDetailUseCase: GetCoinDetailUseCase,
    private val getCoinChartUseCase: GetCoinChartUseCase,
    private val toggleWatchlistUseCase: ToggleWatchlistUseCase,
    savedStateHandle: SavedStateHandle
) : ViewModel() {

    private val _state = mutableStateOf(DetailState())
    val state: State<DetailState> = _state

    private val coinId: String = savedStateHandle.get<String>(Constants.PARAM_COIN_ID) ?: ""

    init {
        Log.d("DetailViewModel", "Init with coinId: $coinId")
        getCoinDetail()
        getChartData(_state.value.selectedTimeframe)
    }

    fun onEvent(event: DetailEvent) {
        when (event) {
            is DetailEvent.OnTimeframeSelected -> {
                Log.d("DetailViewModel", "Timeframe selected: ${event.timeframe}")
                _state.value = _state.value.copy(selectedTimeframe = event.timeframe)
                getChartData(event.timeframe)
            }
            is DetailEvent.OnWatchlistToggle -> {
                viewModelScope.launch {
                    toggleWatchlistUseCase(coinId)
                    _state.value = _state.value.copy(isInWatchlist = !_state.value.isInWatchlist)
                }
            }
            is DetailEvent.Refresh -> {
                getCoinDetail()
                getChartData(_state.value.selectedTimeframe)
            }
        }
    }

    private fun getCoinDetail() {
        getCoinDetailUseCase(coinId).onEach { result ->
            when (result) {
                is Resource.Success -> {
                    Log.d("DetailViewModel", "Coin detail loaded: ${result.data?.name}")
                    _state.value = _state.value.copy(
                        coinDetail = result.data,
                        isLoading = false,
                        error = ""
                    )
                }
                is Resource.Error -> {
                    Log.e("DetailViewModel", "Error loading detail: ${result.message}")
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

    private fun getChartData(days: String) {
        getCoinChartUseCase(coinId, days).onEach { result ->
            when (result) {
                is Resource.Success -> {
                    Log.d("DetailViewModel", "Chart data loaded: ${result.data?.prices?.size} points")
                    _state.value = _state.value.copy(chartData = result.data)
                }
                is Resource.Error -> {
                    Log.e("DetailViewModel", "Error loading chart: ${result.message}")
                }
                is Resource.Loading -> {
                    // Don't show loading for chart to avoid UI flickering
                }
            }
        }.launchIn(viewModelScope)
    }
}

