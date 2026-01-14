package com.example.moneytwork.presentation.portfolio

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.example.moneytwork.presentation.components.GlassCard
import com.example.moneytwork.presentation.market.list.MarketListViewModel
import com.example.moneytwork.presentation.market.list.components.CoinListItem
import com.example.moneytwork.presentation.navigation.Screen
import com.example.moneytwork.ui.theme.NegativeRed
import com.example.moneytwork.ui.theme.PositiveGreen
import java.text.NumberFormat
import java.util.Locale

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PortfolioScreen(
    navController: NavController,
    portfolioViewModel: PortfolioViewModel = hiltViewModel(),
    marketViewModel: MarketListViewModel = hiltViewModel()
) {
    val portfolioState = portfolioViewModel.state.value
    val marketState = marketViewModel.state.value

    val topCryptos = marketState.coins.take(5)
    val profitLossColor = if (portfolioState.profitLoss >= 0) PositiveGreen else NegativeRed

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Portfolio") }
            )
        }
    ) { paddingValues ->
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .padding(horizontal = 16.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            item { Spacer(modifier = Modifier.height(4.dp)) }

            // Portfolio Info Card
            item {
                GlassCard {
                    Text(
                        text = "Total Portfolio Value",
                        style = MaterialTheme.typography.titleMedium,
                        color = Color.White.copy(alpha = 0.7f)
                    )
                    Spacer(modifier = Modifier.height(8.dp))
                    Text(
                        text = formatCurrency(portfolioState.currentValue),
                        style = MaterialTheme.typography.displayMedium,
                        fontWeight = FontWeight.Bold,
                        color = Color.White,
                        fontSize = 42.sp
                    )
                    Spacer(modifier = Modifier.height(8.dp))
                    Row(
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text(
                            text = "${if (portfolioState.profitLoss >= 0) "+" else ""}${formatCurrency(portfolioState.profitLoss)}",
                            style = MaterialTheme.typography.titleLarge,
                            color = profitLossColor,
                            fontWeight = FontWeight.SemiBold
                        )
                        Spacer(modifier = Modifier.width(8.dp))
                        Text(
                            text = "(${if (portfolioState.profitLossPercentage >= 0) "+" else ""}${String.format("%.2f", portfolioState.profitLossPercentage)}%)",
                            style = MaterialTheme.typography.titleMedium,
                            color = profitLossColor
                        )
                    }
                    Spacer(modifier = Modifier.height(16.dp))
                    Divider(color = Color.White.copy(alpha = 0.2f))
                    Spacer(modifier = Modifier.height(12.dp))
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        Column {
                            Text(
                                text = "Total Invested",
                                style = MaterialTheme.typography.bodyMedium,
                                color = Color.White.copy(alpha = 0.6f)
                            )
                            Text(
                                text = formatCurrency(portfolioState.totalInvestment),
                                style = MaterialTheme.typography.titleMedium,
                                color = Color.White,
                                fontWeight = FontWeight.SemiBold
                            )
                        }
                        Column(horizontalAlignment = Alignment.End) {
                            Text(
                                text = "Returns",
                                style = MaterialTheme.typography.bodyMedium,
                                color = Color.White.copy(alpha = 0.6f)
                            )
                            Text(
                                text = "${String.format("%.2f", portfolioState.profitLossPercentage)}%",
                                style = MaterialTheme.typography.titleMedium,
                                color = profitLossColor,
                                fontWeight = FontWeight.SemiBold
                            )
                        }
                    }
                }
            }

            // Top Cryptocurrencies Card
            item {
                GlassCard(
                    onClick = { navController.navigate("crypto") }
                ) {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text(
                            text = "Top Cryptocurrencies",
                            style = MaterialTheme.typography.titleLarge,
                            color = Color.White,
                            fontWeight = FontWeight.Bold
                        )
                        Text(
                            text = "View All →",
                            style = MaterialTheme.typography.bodyMedium,
                            color = Color.White.copy(alpha = 0.7f)
                        )
                    }
                    Spacer(modifier = Modifier.height(16.dp))
                    if (topCryptos.isNotEmpty()) {
                        topCryptos.take(3).forEach { coin ->
                            CoinListItem(
                                coin = coin,
                                onItemClick = {
                                    navController.navigate(Screen.CoinDetail.createRoute(coin.id))
                                },
                                modifier = Modifier.padding(vertical = 4.dp)
                            )
                        }
                    } else {
                        Text(
                            text = "Loading...",
                            color = Color.White.copy(alpha = 0.6f)
                        )
                    }
                }
            }

            // Top Stocks Card
            item {
                GlassCard(
                    onClick = { navController.navigate("stocks") }
                ) {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text(
                            text = "Top Stocks",
                            style = MaterialTheme.typography.titleLarge,
                            color = Color.White,
                            fontWeight = FontWeight.Bold
                        )
                        Text(
                            text = "View All →",
                            style = MaterialTheme.typography.bodyMedium,
                            color = Color.White.copy(alpha = 0.7f)
                        )
                    }
                    Spacer(modifier = Modifier.height(16.dp))
                    Text(
                        text = "Coming Soon",
                        style = MaterialTheme.typography.bodyLarge,
                        color = Color.White.copy(alpha = 0.6f)
                    )
                    Text(
                        text = "Stock market integration will be added next",
                        style = MaterialTheme.typography.bodyMedium,
                        color = Color.White.copy(alpha = 0.4f)
                    )
                }
            }

            item { Spacer(modifier = Modifier.height(16.dp)) }
        }
    }
}

private fun formatCurrency(value: Double): String {
    val formatter = NumberFormat.getCurrencyInstance(Locale.US)
    return formatter.format(value)
}

