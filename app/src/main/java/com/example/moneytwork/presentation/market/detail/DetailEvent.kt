package com.example.moneytwork.presentation.market.detail

sealed class DetailEvent {
    data class OnTimeframeSelected(val timeframe: String) : DetailEvent()
    object OnWatchlistToggle : DetailEvent()
    object Refresh : DetailEvent()
}

