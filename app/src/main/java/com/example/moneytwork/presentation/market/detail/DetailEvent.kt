package com.example.moneytwork.presentation.market.detail

import com.example.moneytwork.domain.model.Transaction

sealed class DetailEvent {
    data class OnTimeframeSelected(val timeframe: String) : DetailEvent()
    object OnWatchlistToggle : DetailEvent()
    object Refresh : DetailEvent()
    data class AddTransaction(val transaction: Transaction) : DetailEvent()
}

