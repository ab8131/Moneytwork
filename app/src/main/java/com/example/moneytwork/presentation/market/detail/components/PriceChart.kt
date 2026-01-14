package com.example.moneytwork.presentation.market.detail.components

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.example.moneytwork.domain.model.ChartData
import com.example.moneytwork.presentation.components.GlassCard
import com.example.moneytwork.presentation.market.detail.ChartTimeframe
import com.patrykandpatrick.vico.compose.chart.Chart
import com.patrykandpatrick.vico.compose.chart.line.lineChart
import com.patrykandpatrick.vico.compose.component.shape.shader.fromBrush
import com.patrykandpatrick.vico.compose.style.ProvideChartStyle
import com.patrykandpatrick.vico.core.chart.line.LineChart
import com.patrykandpatrick.vico.core.component.shape.shader.DynamicShaders
import com.patrykandpatrick.vico.core.entry.entryModelOf
import com.patrykandpatrick.vico.core.entry.FloatEntry
import androidx.compose.ui.graphics.Brush

@Composable
fun PriceChart(
    chartData: ChartData?,
    selectedTimeframe: String,
    onTimeframeSelected: (String) -> Unit,
    modifier: Modifier = Modifier
) {
    val timeframes = ChartTimeframe.entries

    GlassCard(
        modifier = modifier
            .fillMaxWidth()
            .padding(16.dp)
    ) {
            Text(
                text = "Price Chart",
                style = MaterialTheme.typography.titleLarge,
                fontWeight = FontWeight.Bold
            )

            Spacer(modifier = Modifier.height(16.dp))

            // Timeframe selector
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceEvenly
            ) {
                timeframes.forEach { timeframe ->
                    FilterChip(
                        selected = selectedTimeframe == timeframe.days,
                        onClick = { onTimeframeSelected(timeframe.days) },
                        label = { Text(timeframe.label) }
                    )
                }
            }

            Spacer(modifier = Modifier.height(24.dp))

            // Chart
            if (chartData != null && chartData.prices.isNotEmpty()) {
                val chartEntries = remember(chartData) {
                    chartData.prices.mapIndexed { index, point ->
                        FloatEntry(index.toFloat(), point.price.toFloat())
                    }
                }

                val chartEntryModel = remember(chartEntries) {
                    entryModelOf(chartEntries)
                }

                ProvideChartStyle {
                    Chart(
                        chart = lineChart(
                            lines = listOf(
                                LineChart.LineSpec(
                                    lineColor = Color(0xFF2962FF).hashCode(),
                                    lineBackgroundShader = DynamicShaders.fromBrush(
                                        Brush.verticalGradient(
                                            listOf(
                                                Color(0xFF2962FF).copy(alpha = 0.5f),
                                                Color.Transparent
                                            )
                                        )
                                    )
                                )
                            )
                        ),
                        model = chartEntryModel,
                        startAxis = null, // Remove Y-axis grid
                        bottomAxis = null, // Remove X-axis grid
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(250.dp)
                    )
                }

                // Price info below chart
                Spacer(modifier = Modifier.height(16.dp))
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Column {
                        Text(
                            text = "Low",
                            style = MaterialTheme.typography.labelMedium,
                            color = Color.White.copy(alpha = 0.6f)
                        )
                        Text(
                            text = "$${String.format("%.2f", chartData.prices.minOf { it.price })}",
                            style = MaterialTheme.typography.bodyLarge,
                            color = Color.White,
                            fontWeight = FontWeight.SemiBold
                        )
                    }
                    Column(horizontalAlignment = androidx.compose.ui.Alignment.End) {
                        Text(
                            text = "High",
                            style = MaterialTheme.typography.labelMedium,
                            color = Color.White.copy(alpha = 0.6f)
                        )
                        Text(
                            text = "$${String.format("%.2f", chartData.prices.maxOf { it.price })}",
                            style = MaterialTheme.typography.bodyLarge,
                            color = Color.White,
                            fontWeight = FontWeight.SemiBold
                        )
                    }
                }
            } else {
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(250.dp),
                    contentAlignment = androidx.compose.ui.Alignment.Center
                ) {
                    CircularProgressIndicator()
                }
            }
        }
    }
}

