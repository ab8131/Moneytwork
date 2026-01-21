package com.example.moneytwork.presentation.stocks.detail

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.moneytwork.core.util.Resource
import com.example.moneytwork.domain.repository.StockRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class StockDetailViewModel @Inject constructor(
    private val repository: StockRepository,
    private val addTransactionUseCase: com.example.moneytwork.domain.usecase.AddTransactionUseCase,
    savedStateHandle: SavedStateHandle
) : ViewModel() {

    private val _state = mutableStateOf(StockDetailState())
    val state: State<StockDetailState> = _state

    init {
        savedStateHandle.get<String>("symbol")?.let { symbol ->
            getStockDetail(symbol)
        }
    }

    private fun getStockDetail(symbol: String) {
        viewModelScope.launch {
            repository.getStockDetail(symbol).onEach { result ->
                when (result) {
                    is Resource.Success -> {
                        _state.value = StockDetailState(stock = result.data)
                    }
                    is Resource.Error -> {
                        _state.value = StockDetailState(
                            error = result.message ?: "An unexpected error occurred"
                        )
                    }
                    is Resource.Loading -> {
                        _state.value = StockDetailState(isLoading = true)
                    }
                }
            }.launchIn(this)
        }
    }

    fun refresh() {
        state.value.stock?.let { stock ->
            getStockDetail(stock.symbol)
        }
    }

    fun addTransaction(transaction: com.example.moneytwork.domain.model.Transaction) {
        viewModelScope.launch {
            try {
                addTransactionUseCase(transaction)
            } catch (e: Exception) {
                // Handle error
            }
        }
    }
}

