package com.example.moneytwork.presentation.market.detail.components

import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Star
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import com.example.moneytwork.domain.model.CoinDetail
import java.text.NumberFormat
import java.util.Locale
@Composable
fun PriceHeader(
    coinDetail: CoinDetail,
    isInWatchlist: Boolean,
    onWatchlistClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    val priceChangeColor = if ((coinDetail.priceChangePercentage24h ?: 0.0) >= 0) {
        Color(0xFF00C853)
    } else {
        Color(0xFFD50000)
    }
    Card(
        modifier = modifier
            .fillMaxWidth()
            .padding(16.dp),
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(20.dp)
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    AsyncImage(
                        model = coinDetail.image,
                        contentDescription = coinDetail.name,
                        modifier = Modifier.size(48.dp)
                    )
                    Spacer(modifier = Modifier.width(12.dp))
                    Column {
                        Text(
                            text = coinDetail.name,
                            style = MaterialTheme.typography.headlineSmall,
                            fontWeight = FontWeight.Bold,
                            color = Color.White
                        )
                        Text(
                            text = coinDetail.symbol.uppercase(),
                            style = MaterialTheme.typography.bodyLarge,
                            color = Color.White.copy(alpha = 0.7f)
                        )
                    }
                }
                IconButton(onClick = onWatchlistClick) {
                    Icon(
                        imageVector = Icons.Filled.Star,
                        contentDescription = "Watchlist",
                        tint = if (isInWatchlist) Color(0xFFFFC107) else MaterialTheme.colorScheme.onSurfaceVariant.copy(alpha = 0.5f)
                    )
                }
            }
            Spacer(modifier = Modifier.height(24.dp))
            Text(
                text = formatPrice(coinDetail.currentPrice),
                style = MaterialTheme.typography.displaySmall,
                fontWeight = FontWeight.Bold,
                fontSize = 36.sp
            )
            Spacer(modifier = Modifier.height(8.dp))
            coinDetail.priceChangePercentage24h?.let { change ->
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Text(
                        text = "${if (change >= 0) "+" else ""}${String.format("%.2f", change)}%",
                        style = MaterialTheme.typography.titleMedium,
                        color = priceChangeColor,
                        fontWeight = FontWeight.SemiBold
                    )
                    Spacer(modifier = Modifier.width(8.dp))
                    Text(
                        text = "(24h)",
                        style = MaterialTheme.typography.bodyMedium,
                        color = MaterialTheme.colorScheme.onSurfaceVariant
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

