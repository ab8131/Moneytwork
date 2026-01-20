package com.example.moneytwork.presentation.portfolio

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.moneytwork.domain.repository.PortfolioRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class PortfolioViewModel @Inject constructor(
    private val portfolioRepository: PortfolioRepository
) : ViewModel() {

    private val _state = mutableStateOf(PortfolioState())
    val state: State<PortfolioState> = _state

    // Expose holdings for the holdings screen
    val holdings = portfolioRepository.getAllHoldings()

    init {
        loadPortfolioData()
    }

    private fun loadPortfolioData() {
        portfolioRepository.getAllHoldings().onEach { holdings ->
            viewModelScope.launch {
                val summary = portfolioRepository.calculatePortfolioValue(holdings)
                _state.value = PortfolioState(
                    isLoading = false,
                    totalInvestment = summary.totalInvestment,
                    currentValue = summary.currentValue,
                    profitLoss = summary.profitLoss,
                    profitLossPercentage = summary.profitLossPercentage
                )
            }
        }.launchIn(viewModelScope)
    }
}

