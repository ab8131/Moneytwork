package com.example.moneytwork.presentation.portfolio

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class PortfolioViewModel @Inject constructor() : ViewModel() {

    private val _state = mutableStateOf(PortfolioState())
    val state: State<PortfolioState> = _state

    init {
        // TODO: Load portfolio data
        // For now, use mock data
        _state.value = PortfolioState(
            totalInvestment = 900.0, // $400 + $500
            currentValue = 1250.0,
            profitLoss = 350.0,
            profitLossPercentage = 38.89
        )
    }
}

