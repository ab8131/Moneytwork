package com.example.moneytwork.presentation.stocks

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.moneytwork.core.util.Resource
import com.example.moneytwork.domain.usecase.GetStocksUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import javax.inject.Inject

@HiltViewModel
class StockListViewModel @Inject constructor(
    private val getStocksUseCase: GetStocksUseCase
) : ViewModel() {

    private val _state = mutableStateOf(StockListState())
    val state: State<StockListState> = _state

    // Popular US stocks
    private val popularStocks = listOf(
        "AAPL",  // Apple
        "MSFT",  // Microsoft
        "GOOGL", // Google
        "AMZN",  // Amazon
        "TSLA",  // Tesla
        "META",  // Meta
        "NVDA",  // NVIDIA
        "JPM",   // JPMorgan
        "V",     // Visa
        "WMT",   // Walmart
        "DIS",   // Disney
        "NFLX",  // Netflix
        "BA",    // Boeing
        "COIN",  // Coinbase
        "AMD"    // AMD
    )

    init {
        getStocks()
    }

    fun getStocks() {
        viewModelScope.run {
            getStocksUseCase(popularStocks).onEach { result ->
                when (result) {
                    is Resource.Success -> {
                        _state.value = StockListState(stocks = result.data ?: emptyList())
                    }
                    is Resource.Error -> {
                        _state.value = StockListState(
                            error = result.message ?: "An unexpected error occurred"
                        )
                    }
                    is Resource.Loading -> {
                        _state.value = StockListState(isLoading = true)
                    }
                }
            }.launchIn(this)
        }
    }
}

