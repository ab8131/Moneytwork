package com.example.moneytwork.presentation.market.detail

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
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

    Scaffold(
        topBar = {
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

                        // Price Chart
                        PriceChart(
                            chartData = state.chartData,
                            selectedTimeframe = state.selectedTimeframe,
                            onTimeframeSelected = { timeframe ->
                                viewModel.onEvent(DetailEvent.OnTimeframeSelected(timeframe))
                            }
                        )

                        Spacer(modifier = Modifier.height(8.dp))

                        // Statistics
                        StatsSection(coinDetail = state.coinDetail)

                        Spacer(modifier = Modifier.height(8.dp))

                        // Description (if available)
                        if (state.coinDetail.description.isNotBlank()) {
                            Card(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(16.dp),
                                elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
                            ) {
                                Column(
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .padding(16.dp)
                                ) {
                                    Text(
                                        text = "About ${state.coinDetail.name}",
                                        style = MaterialTheme.typography.titleLarge,
                                        fontWeight = androidx.compose.ui.text.font.FontWeight.Bold
                                    )
                                    Spacer(modifier = Modifier.height(12.dp))
                                    Text(
                                        text = state.coinDetail.description
                                            .replace(Regex("<[^>]*>"), "") // Remove HTML tags
                                            .take(500) + if (state.coinDetail.description.length > 500) "..." else "",
                                        style = MaterialTheme.typography.bodyMedium,
                                        color = MaterialTheme.colorScheme.onSurfaceVariant
                                    )
                                }
                            }
                        }

                        Spacer(modifier = Modifier.height(16.dp))

                        // Join Chat Button (for partner's integration)
                        Button(
                            onClick = { /* TODO: Navigate to chat */ },
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
}

