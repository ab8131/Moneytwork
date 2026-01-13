package com.example.moneytwork.presentation.market.list.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.example.moneytwork.domain.model.Coin
import java.text.NumberFormat
import java.util.Locale

@Composable
fun CoinListItem(
    coin: Coin,
    onItemClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    val priceChangeColor = if ((coin.priceChangePercentage24h ?: 0.0) >= 0) {
        Color(0xFF00C853)
    } else {
        Color(0xFFD50000)
    }

    Card(
        modifier = modifier
            .fillMaxWidth()
            .clickable { onItemClick() }
            .padding(horizontal = 16.dp, vertical = 8.dp),
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            // Coin Image
            AsyncImage(
                model = coin.image,
                contentDescription = coin.name,
                modifier = Modifier.size(40.dp)
            )

            Spacer(modifier = Modifier.width(16.dp))

            // Coin Info
            Column(
                modifier = Modifier.weight(1f)
            ) {
                Text(
                    text = coin.name,
                    style = MaterialTheme.typography.titleMedium,
                    fontWeight = FontWeight.Bold,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis
                )
                Text(
                    text = coin.symbol.uppercase(),
                    style = MaterialTheme.typography.bodyMedium,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
            }

            Spacer(modifier = Modifier.width(8.dp))

            // Price Info
            Column(
                horizontalAlignment = Alignment.End
            ) {
                Text(
                    text = formatPrice(coin.currentPrice),
                    style = MaterialTheme.typography.titleMedium,
                    fontWeight = FontWeight.SemiBold
                )
                coin.priceChangePercentage24h?.let { change ->
                    Text(
                        text = "${if (change >= 0) "+" else ""}${String.format("%.2f", change)}%",
                        style = MaterialTheme.typography.bodyMedium,
                        color = priceChangeColor,
                        fontWeight = FontWeight.Medium
                    )
                }
            }
        }
    }
}

private fun formatPrice(price: Double): String {
    val formatter = NumberFormat.getCurrencyInstance(Locale.US)
    return formatter.format(price)
}

