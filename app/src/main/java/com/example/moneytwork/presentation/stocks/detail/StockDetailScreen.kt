package com.example.moneytwork.presentation.stocks.detail

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.example.moneytwork.presentation.components.GlassCard
import com.example.moneytwork.ui.theme.NegativeRed
import com.example.moneytwork.ui.theme.PositiveGreen
import java.text.NumberFormat
import java.util.Locale

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun StockDetailScreen(
    navController: NavController,
    viewModel: StockDetailViewModel = hiltViewModel()
) {
    val state = viewModel.state.value
    val scrollState = rememberScrollState()
    var showTransactionDialog by remember { mutableStateOf(false) }
    var selectedTabIndex by remember { mutableStateOf(0) }
    val tabs = listOf("Overview", "Chat")

    Scaffold(
        topBar = {
            Column {
                TopAppBar(
                    title = { Text(state.stock?.symbol ?: "Loading...") },
                    navigationIcon = {
                        IconButton(onClick = { navController.popBackStack() }) {
                            Icon(
                                imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                                contentDescription = "Back"
                            )
                        }
                    }
                )
                TabRow(selectedTabIndex = selectedTabIndex) {
                    tabs.forEachIndexed { index, title ->
                        Tab(
                            selected = selectedTabIndex == index,
                            onClick = { selectedTabIndex = index },
                            text = { Text(title) }
                        )
                    }
                }
            }
        }
    ) { paddingValues ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
        ) {
        when {
            state.error.isNotBlank() -> {
                Column(
                    modifier = Modifier.align(Alignment.Center),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Text(
                        text = state.error,
                        color = MaterialTheme.colorScheme.error,
                        textAlign = TextAlign.Center,
                        modifier = Modifier.padding(20.dp)
                    )
                    Spacer(modifier = Modifier.height(16.dp))
                    Button(onClick = { viewModel.refresh() }) {
                        Text("Retry")
                    }
                }
            }

            state.stock != null -> {
                when (selectedTabIndex) {
                    0 -> {
                        // Overview Tab
                        Column(
                            modifier = Modifier
                                .fillMaxSize()
                                .verticalScroll(scrollState)
                                .padding(16.dp)
                        ) {
                            // Price Header
                            GlassCard {
                                Text(
                                    text = state.stock.name,
                                    style = MaterialTheme.typography.titleLarge,
                                    color = Color.White.copy(alpha = 0.7f)
                                )

                                Spacer(modifier = Modifier.height(16.dp))

                                Text(
                                    text = formatCurrency(state.stock.currentPrice),
                                    style = MaterialTheme.typography.displaySmall,
                                    fontWeight = FontWeight.Bold,
                                    fontSize = 36.sp,
                                    color = Color.White
                                )

                                Spacer(modifier = Modifier.height(8.dp))

                                val priceChangeColor = if (state.stock.priceChange >= 0) PositiveGreen else NegativeRed
                                Row(verticalAlignment = Alignment.CenterVertically) {
                                    Text(
                                        text = "${if (state.stock.priceChange >= 0) "+" else ""}${formatCurrency(state.stock.priceChange)}",
                                        style = MaterialTheme.typography.titleLarge,
                                        color = priceChangeColor,
                                        fontWeight = FontWeight.SemiBold
                                    )
                                    Spacer(modifier = Modifier.width(8.dp))
                                    Text(
                                        text = "(${if (state.stock.priceChangePercentage >= 0) "+" else ""}${String.format(Locale.US, "%.2f", state.stock.priceChangePercentage)}%)",
                                        style = MaterialTheme.typography.titleMedium,
                                        color = priceChangeColor
                                    )
                                }
                            }

                            Spacer(modifier = Modifier.height(16.dp))

                            // Statistics
                            GlassCard {
                                Text(
                                    text = "Market Data",
                                    style = MaterialTheme.typography.titleLarge,
                                    fontWeight = FontWeight.Bold,
                                    color = Color.White
                                )

                                Spacer(modifier = Modifier.height(16.dp))

                                StatItem("Open Price", formatCurrency(state.stock.openPrice))
                                Spacer(modifier = Modifier.height(12.dp))
                                HorizontalDivider(color = Color.White.copy(alpha = 0.2f))
                                Spacer(modifier = Modifier.height(12.dp))

                                StatItem("Previous Close", formatCurrency(state.stock.previousClose))
                                Spacer(modifier = Modifier.height(12.dp))
                                HorizontalDivider(color = Color.White.copy(alpha = 0.2f))
                                Spacer(modifier = Modifier.height(12.dp))

                                StatItem("Day High", formatCurrency(state.stock.highPrice))
                                Spacer(modifier = Modifier.height(12.dp))
                                HorizontalDivider(color = Color.White.copy(alpha = 0.2f))
                                Spacer(modifier = Modifier.height(12.dp))

                                StatItem("Day Low", formatCurrency(state.stock.lowPrice))

                                state.stock.marketCap?.let { marketCap ->
                                    Spacer(modifier = Modifier.height(12.dp))
                                    HorizontalDivider(color = Color.White.copy(alpha = 0.2f))
                                    Spacer(modifier = Modifier.height(12.dp))
                                    StatItem("Market Cap", formatLargeNumber(marketCap))
                                }
                            }

                            Spacer(modifier = Modifier.height(16.dp))

                            // Record Transaction
                            GlassCard {
                                Text(
                                    text = "Track Investment",
                                    style = MaterialTheme.typography.titleMedium,
                                    fontWeight = FontWeight.SemiBold,
                                    color = Color.White
                                )
                                Spacer(modifier = Modifier.height(12.dp))
                                Row(
                                    modifier = Modifier.fillMaxWidth(),
                                    horizontalArrangement = Arrangement.spacedBy(12.dp)
                                ) {
                                    Button(
                                        onClick = { showTransactionDialog = true },
                                        modifier = Modifier.weight(1f)
                                    ) {
                                        Text("Record Transaction")
                                    }
                                }
                            }

                            Spacer(modifier = Modifier.height(80.dp))
                        }
                    }
                    1 -> {
                        // Chat Tab
                        Column(
                            modifier = Modifier
                                .fillMaxSize()
                                .padding(16.dp)
                        ) {
                            com.example.moneytwork.presentation.chat.ChatSection(
                                assetId = state.stock.symbol,
                                assetName = state.stock.name,
                                onSignInRequired = {
                                    navController.navigate("sign_in")
                                }
                            )
                        }
                    }
                }
            }
        }

        // Transaction Dialog
        if (showTransactionDialog && state.stock != null) {
            com.example.moneytwork.presentation.components.AddTransactionDialog(
                assetId = state.stock.symbol,
                assetName = state.stock.name,
                assetSymbol = state.stock.symbol,
                assetType = com.example.moneytwork.domain.model.AssetType.STOCK,
                currentPrice = state.stock.currentPrice,
                onDismiss = { showTransactionDialog = false },
                onConfirm = { transaction ->
                    viewModel.addTransaction(transaction)
                    showTransactionDialog = false
                }
            )
        }


        if (state.isLoading) {
            CircularProgressIndicator(
                modifier = Modifier.align(Alignment.Center)
            )
        }
    }
    }
}

@Composable
private fun StatItem(label: String, value: String) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Text(
            text = label,
            style = MaterialTheme.typography.bodyLarge,
            color = Color.White.copy(alpha = 0.7f)
        )
        Text(
            text = value,
            style = MaterialTheme.typography.bodyLarge,
            fontWeight = FontWeight.SemiBold,
            color = Color.White
        )
    }
}

private fun formatCurrency(value: Double): String {
    val formatter = NumberFormat.getCurrencyInstance(Locale.US)
    return formatter.format(value)
}

private fun formatLargeNumber(number: Double): String {
    return when {
        number >= 1_000_000_000_000 -> String.format(Locale.US, "$%.2fT", number / 1_000_000_000_000.0)
        number >= 1_000_000_000 -> String.format(Locale.US, "$%.2fB", number / 1_000_000_000.0)
        number >= 1_000_000 -> String.format(Locale.US, "$%.2fM", number / 1_000_000.0)
        else -> formatCurrency(number)
    }
}

