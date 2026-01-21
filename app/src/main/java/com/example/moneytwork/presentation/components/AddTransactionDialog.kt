package com.example.moneytwork.presentation.components

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import com.example.moneytwork.domain.model.AssetType
import com.example.moneytwork.domain.model.Transaction
import com.example.moneytwork.domain.model.TransactionType
import java.text.NumberFormat
import java.util.Locale

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AddTransactionDialog(
    assetId: String,
    assetName: String,
    assetSymbol: String,
    assetType: AssetType,
    currentPrice: Double,
    onDismiss: () -> Unit,
    onConfirm: (Transaction) -> Unit
) {
    var selectedType by remember { mutableStateOf(TransactionType.BUY) }
    var quantityInput by remember { mutableStateOf("") }
    var totalInput by remember { mutableStateOf("") }
    var pricePerUnit by remember { mutableStateOf(currentPrice.toString()) }
    var notes by remember { mutableStateOf("") }
    
    // Track which field user is editing
    var lastEditedField by remember { mutableStateOf("quantity") } // "quantity" or "total"

    // Calculate based on which field was last edited
    val finalQuantity: Double
    val finalTotal: Double
    val priceVal = pricePerUnit.toDoubleOrNull() ?: currentPrice

    when (lastEditedField) {
        "quantity" -> {
            finalQuantity = quantityInput.toDoubleOrNull() ?: 0.0
            finalTotal = finalQuantity * priceVal
        }
        "total" -> {
            finalTotal = totalInput.toDoubleOrNull() ?: 0.0
            finalQuantity = if (priceVal > 0) finalTotal / priceVal else 0.0
        }
        else -> {
            finalQuantity = 0.0
            finalTotal = 0.0
        }
    }

    Dialog(onDismissRequest = onDismiss) {
        Card(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            shape = RoundedCornerShape(16.dp),
            colors = CardDefaults.cardColors(
                containerColor = Color(0xFF1E1E1E)
            )
        ) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(20.dp)
            ) {
                // Header
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = "Record Transaction",
                        style = MaterialTheme.typography.titleLarge,
                        fontWeight = FontWeight.Bold,
                        color = Color.White
                    )
                    IconButton(onClick = onDismiss) {
                        Icon(Icons.Default.Close, "Close", tint = Color.White)
                    }
                }

                Spacer(modifier = Modifier.height(8.dp))

                // Asset Info
                Text(
                    text = "$assetName ($assetSymbol)",
                    style = MaterialTheme.typography.bodyLarge,
                    color = Color.White.copy(alpha = 0.7f)
                )

                Spacer(modifier = Modifier.height(16.dp))

                // Transaction Type Selector
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    FilterChip(
                        selected = selectedType == TransactionType.BUY,
                        onClick = { selectedType = TransactionType.BUY },
                        label = { Text("Buy") },
                        modifier = Modifier.weight(1f),
                        colors = FilterChipDefaults.filterChipColors(
                            selectedContainerColor = MaterialTheme.colorScheme.primary,
                            selectedLabelColor = Color.White,
                            containerColor = Color.White.copy(alpha = 0.1f),
                            labelColor = Color.White.copy(alpha = 0.6f)
                        )
                    )
                    FilterChip(
                        selected = selectedType == TransactionType.SELL,
                        onClick = { selectedType = TransactionType.SELL },
                        label = { Text("Sell") },
                        modifier = Modifier.weight(1f),
                        colors = FilterChipDefaults.filterChipColors(
                            selectedContainerColor = MaterialTheme.colorScheme.primary,
                            selectedLabelColor = Color.White,
                            containerColor = Color.White.copy(alpha = 0.1f),
                            labelColor = Color.White.copy(alpha = 0.6f)
                        )
                    )
                }

                Spacer(modifier = Modifier.height(16.dp))

                // Quantity Field
                OutlinedTextField(
                    value = quantityInput,
                    onValueChange = { 
                        quantityInput = it
                        lastEditedField = "quantity"
                        totalInput = "" // Clear the other field
                    },
                    label = { Text("Quantity (e.g., 0.0082 $assetSymbol)", color = Color.White.copy(alpha = 0.6f)) },
                    placeholder = { Text("0.00", color = Color.White.copy(alpha = 0.4f)) },
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Decimal),
                    modifier = Modifier.fillMaxWidth(),
                    colors = TextFieldDefaults.colors(
                        focusedContainerColor = Color.White.copy(alpha = 0.1f),
                        unfocusedContainerColor = Color.White.copy(alpha = 0.05f),
                        focusedTextColor = Color.White,
                        unfocusedTextColor = Color.White,
                        cursorColor = MaterialTheme.colorScheme.primary,
                        focusedIndicatorColor = MaterialTheme.colorScheme.primary,
                        unfocusedIndicatorColor = Color.White.copy(alpha = 0.3f)
                    ),
                    singleLine = true
                )

                Spacer(modifier = Modifier.height(12.dp))

                Text(
                    text = "OR",
                    style = MaterialTheme.typography.bodyMedium,
                    color = Color.White.copy(alpha = 0.6f),
                    modifier = Modifier.align(Alignment.CenterHorizontally)
                )

                Spacer(modifier = Modifier.height(12.dp))

                // Total Value Field
                OutlinedTextField(
                    value = totalInput,
                    onValueChange = { 
                        totalInput = it
                        lastEditedField = "total"
                        quantityInput = "" // Clear the other field
                    },
                    label = { Text("Total Amount (e.g., $400)", color = Color.White.copy(alpha = 0.6f)) },
                    placeholder = { Text("$0.00", color = Color.White.copy(alpha = 0.4f)) },
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Decimal),
                    modifier = Modifier.fillMaxWidth(),
                    colors = TextFieldDefaults.colors(
                        focusedContainerColor = Color.White.copy(alpha = 0.1f),
                        unfocusedContainerColor = Color.White.copy(alpha = 0.05f),
                        focusedTextColor = Color.White,
                        unfocusedTextColor = Color.White,
                        cursorColor = MaterialTheme.colorScheme.primary,
                        focusedIndicatorColor = MaterialTheme.colorScheme.primary,
                        unfocusedIndicatorColor = Color.White.copy(alpha = 0.3f)
                    ),
                    singleLine = true
                )

                Spacer(modifier = Modifier.height(12.dp))

                // Price Per Unit Field
                OutlinedTextField(
                    value = pricePerUnit,
                    onValueChange = { pricePerUnit = it },
                    label = { Text("Price Per Unit", color = Color.White.copy(alpha = 0.6f)) },
                    placeholder = { Text("0.00", color = Color.White.copy(alpha = 0.4f)) },
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Decimal),
                    modifier = Modifier.fillMaxWidth(),
                    colors = TextFieldDefaults.colors(
                        focusedContainerColor = Color.White.copy(alpha = 0.1f),
                        unfocusedContainerColor = Color.White.copy(alpha = 0.05f),
                        focusedTextColor = Color.White,
                        unfocusedTextColor = Color.White,
                        cursorColor = MaterialTheme.colorScheme.primary,
                        focusedIndicatorColor = MaterialTheme.colorScheme.primary,
                        unfocusedIndicatorColor = Color.White.copy(alpha = 0.3f)
                    ),
                    singleLine = true
                )

                Spacer(modifier = Modifier.height(12.dp))

                // Notes Field
                OutlinedTextField(
                    value = notes,
                    onValueChange = { notes = it },
                    label = { Text("Notes (Optional)", color = Color.White.copy(alpha = 0.6f)) },
                    placeholder = { Text("Add a note...", color = Color.White.copy(alpha = 0.4f)) },
                    modifier = Modifier.fillMaxWidth(),
                    colors = TextFieldDefaults.colors(
                        focusedContainerColor = Color.White.copy(alpha = 0.1f),
                        unfocusedContainerColor = Color.White.copy(alpha = 0.05f),
                        focusedTextColor = Color.White,
                        unfocusedTextColor = Color.White,
                        cursorColor = MaterialTheme.colorScheme.primary,
                        focusedIndicatorColor = MaterialTheme.colorScheme.primary,
                        unfocusedIndicatorColor = Color.White.copy(alpha = 0.3f)
                    ),
                    maxLines = 3
                )

                Spacer(modifier = Modifier.height(16.dp))

                // Calculation Summary Display
                Card(
                    modifier = Modifier.fillMaxWidth(),
                    colors = CardDefaults.cardColors(
                        containerColor = MaterialTheme.colorScheme.primary.copy(alpha = 0.2f)
                    )
                ) {
                    Column(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(12.dp)
                    ) {
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceBetween,
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Text(
                                text = "You're ${if (selectedType == TransactionType.BUY) "getting" else "selling"}",
                                style = MaterialTheme.typography.bodyLarge,
                                color = Color.White.copy(alpha = 0.8f)
                            )
                            Text(
                                text = String.format("%.8f %s", finalQuantity, assetSymbol),
                                style = MaterialTheme.typography.titleMedium,
                                fontWeight = FontWeight.Bold,
                                color = MaterialTheme.colorScheme.primary
                            )
                        }
                        Spacer(modifier = Modifier.height(8.dp))
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceBetween,
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Text(
                                text = "Total Cost",
                                style = MaterialTheme.typography.bodyLarge,
                                color = Color.White.copy(alpha = 0.8f)
                            )
                            Text(
                                text = NumberFormat.getCurrencyInstance(Locale.US).format(finalTotal),
                                style = MaterialTheme.typography.titleLarge,
                                fontWeight = FontWeight.Bold,
                                color = MaterialTheme.colorScheme.primary
                            )
                        }
                    }
                }

                Spacer(modifier = Modifier.height(20.dp))

                // Action Buttons
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    OutlinedButton(
                        onClick = onDismiss,
                        modifier = Modifier.weight(1f),
                        colors = ButtonDefaults.outlinedButtonColors(
                            contentColor = Color.White
                        )
                    ) {
                        Text("Cancel")
                    }
                    Button(
                        onClick = {
                            if (finalQuantity > 0 && priceVal > 0 && finalTotal > 0) {
                                val transaction = Transaction(
                                    assetId = assetId,
                                    assetType = assetType,
                                    assetName = assetName,
                                    assetSymbol = assetSymbol,
                                    transactionType = selectedType,
                                    amount = finalQuantity,
                                    pricePerUnit = priceVal,
                                    totalValue = finalTotal,
                                    timestamp = System.currentTimeMillis(),
                                    notes = notes
                                )
                                onConfirm(transaction)
                            }
                        },
                        modifier = Modifier.weight(1f),
                        enabled = (quantityInput.isNotEmpty() || totalInput.isNotEmpty()) &&
                                pricePerUnit.toDoubleOrNull()?.let { it > 0 } == true
                    ) {
                        Text("Confirm")
                    }
                }
            }
        }
    }
}

