package com.example.moneytwork.presentation.market.detail

import com.example.moneytwork.domain.model.ChartData
import com.example.moneytwork.domain.model.CoinDetail

data class DetailState(
    val isLoading: Boolean = false,
    val coinDetail: CoinDetail? = null,
    val chartData: ChartData? = null,
    val selectedTimeframe: String = "7",
    val error: String = "",
    val isInWatchlist: Boolean = false
)

enum class ChartTimeframe(val days: String, val label: String) {
    ONE_DAY("1", "1D"),
    ONE_WEEK("7", "1W"),
    ONE_MONTH("30", "1M"),
    THREE_MONTHS("90", "3M"),
    ONE_YEAR("365", "1Y"),
    ALL("max", "ALL")
}

