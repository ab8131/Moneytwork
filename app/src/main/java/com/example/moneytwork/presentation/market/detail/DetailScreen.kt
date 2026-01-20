package com.example.moneytwork.presentation.market.detail

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.example.moneytwork.presentation.market.detail.components.PriceChart
import com.example.moneytwork.presentation.market.detail.components.PriceHeader
import com.example.moneytwork.presentation.market.detail.components.StatsSection

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DetailScreen(
    navController: NavController,
    viewModel: DetailViewModel = hiltViewModel()
) {
    val state = viewModel.state.value
    val scrollState = rememberScrollState()
    var selectedTabIndex by remember { mutableStateOf(0) }
    val tabs = listOf("Graph", "Financials", "Ownership", "Chat")
    var showTransactionDialog by remember { mutableStateOf(false) }

    Scaffold(
        topBar = {
            Column {
                TopAppBar(
                    title = { Text(state.coinDetail?.name ?: "Loading...") },
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
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(20.dp)
                        )
                        Spacer(modifier = Modifier.height(16.dp))
                        Button(onClick = { viewModel.onEvent(DetailEvent.Refresh) }) {
                            Text("Retry")
                        }
                    }
                }

                state.coinDetail != null -> {
                    Column(
                        modifier = Modifier
                            .fillMaxSize()
                            .verticalScroll(scrollState)
                    ) {
                        Spacer(modifier = Modifier.height(8.dp))

                        // Price Header
                        PriceHeader(
                            coinDetail = state.coinDetail,
                            isInWatchlist = state.isInWatchlist,
                            onWatchlistClick = { viewModel.onEvent(DetailEvent.OnWatchlistToggle) }
                        )

                        Spacer(modifier = Modifier.height(8.dp))

                        // Tab Content
                        when (selectedTabIndex) {
                            0 -> {
                                // Graph Tab
                                PriceChart(
                                    chartData = state.chartData,
                                    selectedTimeframe = state.selectedTimeframe,
                                    onTimeframeSelected = { timeframe ->
                                        viewModel.onEvent(DetailEvent.OnTimeframeSelected(timeframe))
                                    }
                                )
                            }
                            1 -> {
                                // Financials Tab
                                StatsSection(coinDetail = state.coinDetail)

                                Spacer(modifier = Modifier.height(8.dp))

                                // Description
                                if (state.coinDetail.description.isNotBlank()) {
                                    com.example.moneytwork.presentation.components.GlassCard(
                                        modifier = Modifier.padding(16.dp)
                                    ) {
                                        Text(
                                            text = "About ${state.coinDetail.name}",
                                            style = MaterialTheme.typography.titleLarge,
                                            fontWeight = androidx.compose.ui.text.font.FontWeight.Bold
                                        )
                                        Spacer(modifier = Modifier.height(12.dp))
                                        Text(
                                            text = state.coinDetail.description
                                                .replace(Regex("<[^>]*>"), "")
                                                .take(500) + if (state.coinDetail.description.length > 500) "..." else "",
                                            style = MaterialTheme.typography.bodyMedium,
                                            color = androidx.compose.ui.graphics.Color.White.copy(alpha = 0.7f)
                                        )
                                    }
                                }
                            }
                            2 -> {
                                // Ownership Tab
                                com.example.moneytwork.presentation.components.GlassCard(
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .padding(16.dp)
                                ) {
                                    Text(
                                        text = "Your ${state.coinDetail.name} Holdings",
                                        style = MaterialTheme.typography.titleLarge,
                                        fontWeight = androidx.compose.ui.text.font.FontWeight.Bold
                                    )
                                    Spacer(modifier = Modifier.height(16.dp))
                                    Text(
                                        text = "No holdings yet",
                                        style = MaterialTheme.typography.bodyLarge,
                                        color = androidx.compose.ui.graphics.Color.White.copy(alpha = 0.6f)
                                    )
                                    Spacer(modifier = Modifier.height(8.dp))
                                    Text(
                                        text = "Record your first transaction to track your investment",
                                        style = MaterialTheme.typography.bodyMedium,
                                        color = androidx.compose.ui.graphics.Color.White.copy(alpha = 0.4f)
                                    )
                                }
                            }
                            3 -> {
                                // Chat Tab
                                Column(
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .padding(16.dp)
                                ) {
                                    com.example.moneytwork.presentation.chat.ChatSection(
                                        assetId = state.coinDetail.id,
                                        assetName = state.coinDetail.name,
                                        onSignInRequired = {
                                            navController.navigate("sign_in")
                                        }
                                    )
                                }
                            }
                        }

                        Spacer(modifier = Modifier.height(16.dp))

                        // Action Buttons - Record Transaction
                        com.example.moneytwork.presentation.components.GlassCard(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(horizontal = 16.dp)
                        ) {
                            Text(
                                text = "Track Investment",
                                style = MaterialTheme.typography.titleMedium,
                                fontWeight = androidx.compose.ui.text.font.FontWeight.SemiBold
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

                        Spacer(modifier = Modifier.height(16.dp))

                        // Join Chat Button
                        Button(
                            onClick = { selectedTabIndex = 3 }, // Switch to Chat tab
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(horizontal = 16.dp)
                                .height(56.dp)
                        ) {
                            Text(
                                text = "Join ${state.coinDetail.name} Community Chat",
                                style = MaterialTheme.typography.titleMedium
                            )
                        }

                        Spacer(modifier = Modifier.height(32.dp))
                    }
                }
            }

            if (state.isLoading) {
                CircularProgressIndicator(
                    modifier = Modifier.align(Alignment.Center)
                )
            }
        }
    }

    // Transaction Dialog
    if (showTransactionDialog && state.coinDetail != null) {
        com.example.moneytwork.presentation.components.AddTransactionDialog(
            assetId = state.coinDetail.id,
            assetName = state.coinDetail.name,
            assetSymbol = state.coinDetail.symbol,
            assetType = com.example.moneytwork.domain.model.AssetType.CRYPTO,
            currentPrice = state.coinDetail.currentPrice,
            onDismiss = { showTransactionDialog = false },
            onConfirm = { transaction ->
                viewModel.onEvent(DetailEvent.AddTransaction(transaction))
                showTransactionDialog = false
            }
        )
    }
}

