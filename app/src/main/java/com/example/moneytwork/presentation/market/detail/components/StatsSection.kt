package com.example.moneytwork.presentation.market.detail.components

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.example.moneytwork.domain.model.CoinDetail
import java.text.NumberFormat
import java.util.Locale

@Composable
fun StatsSection(
    coinDetail: CoinDetail,
    modifier: Modifier = Modifier
) {
    Card(
        modifier = modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp),
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp)
        ) {
            Text(
                text = "Statistics",
                style = MaterialTheme.typography.titleLarge,
                fontWeight = FontWeight.Bold
            )

            Spacer(modifier = Modifier.height(16.dp))

            // Market Cap
            StatItem(
                label = "Market Cap",
                value = formatLargeNumber(coinDetail.marketCap)
            )

            Divider(modifier = Modifier.padding(vertical = 12.dp))

            // Volume
            StatItem(
                label = "24h Volume",
                value = formatLargeNumber(coinDetail.totalVolume)
            )

            coinDetail.high24h?.let { high ->
                Divider(modifier = Modifier.padding(vertical = 12.dp))
                StatItem(
                    label = "24h High",
                    value = formatPrice(high)
                )
            }

            coinDetail.low24h?.let { low ->
                Divider(modifier = Modifier.padding(vertical = 12.dp))
                StatItem(
                    label = "24h Low",
                    value = formatPrice(low)
                )
            }

            coinDetail.priceChangePercentage7d?.let { change ->
                Divider(modifier = Modifier.padding(vertical = 12.dp))
                StatItem(
                    label = "7d Change",
                    value = "${if (change >= 0) "+" else ""}${String.format("%.2f", change)}%",
                    valueColor = if (change >= 0)
                        androidx.compose.ui.graphics.Color(0xFF00C853)
                    else
                        androidx.compose.ui.graphics.Color(0xFFD50000)
                )
            }

            coinDetail.priceChangePercentage30d?.let { change ->
                Divider(modifier = Modifier.padding(vertical = 12.dp))
                StatItem(
                    label = "30d Change",
                    value = "${if (change >= 0) "+" else ""}${String.format("%.2f", change)}%",
                    valueColor = if (change >= 0)
                        androidx.compose.ui.graphics.Color(0xFF00C853)
                    else
                        androidx.compose.ui.graphics.Color(0xFFD50000)
                )
            }
        }
    }
}

@Composable
private fun StatItem(
    label: String,
    value: String,
    valueColor: androidx.compose.ui.graphics.Color = MaterialTheme.colorScheme.onSurface
) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Text(
            text = label,
            style = MaterialTheme.typography.bodyLarge,
            color = MaterialTheme.colorScheme.onSurfaceVariant
        )
        Text(
            text = value,
            style = MaterialTheme.typography.bodyLarge,
            fontWeight = FontWeight.SemiBold,
            color = valueColor
        )
    }
}

private fun formatPrice(price: Double): String {
    val formatter = NumberFormat.getCurrencyInstance(Locale.US)
    return formatter.format(price)
}

private fun formatLargeNumber(number: Long): String {
    return when {
        number >= 1_000_000_000_000 -> String.format("$%.2fT", number / 1_000_000_000_000.0)
        number >= 1_000_000_000 -> String.format("$%.2fB", number / 1_000_000_000.0)
        number >= 1_000_000 -> String.format("$%.2fM", number / 1_000_000.0)
        else -> NumberFormat.getCurrencyInstance(Locale.US).format(number)
    }
}

