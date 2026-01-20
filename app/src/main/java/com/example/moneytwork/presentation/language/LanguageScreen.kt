package com.example.moneytwork.presentation.language

import android.app.Activity
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Check
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.example.moneytwork.R
import com.example.moneytwork.ui.theme.PositiveGreen

data class LanguageOption(val code: String, val name: String, val nativeName: String)

@Composable
fun LanguageScreen(navController: NavController, viewModel: LanguageViewModel = hiltViewModel()) {
    val selectedLanguage by viewModel.selectedLanguage.collectAsState()
    val shouldRecreate by viewModel.shouldRecreate.collectAsState()
    val context = LocalContext.current

    // Recreate activity when language changes
    LaunchedEffect(shouldRecreate) {
        if (shouldRecreate) {
            (context as? Activity)?.recreate()
            viewModel.onRecreated()
        }
    }

    val languages =
            listOf(
                    LanguageOption("en", "English", "English"),
                    LanguageOption("fr", "French", "Français"),
                    LanguageOption("ha", "Hausa", "Hausa")
            )

    Column(
            modifier =
                    Modifier.fillMaxSize()
                            .background(MaterialTheme.colorScheme.background)
                            .padding(16.dp)
    ) {
        // Header with Back Button
        Row(modifier = Modifier.fillMaxWidth(), verticalAlignment = Alignment.CenterVertically) {
            IconButton(
                    onClick = { navController.popBackStack() },
                    modifier = Modifier.size(40.dp)
            ) {
                Icon(
                        imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                        contentDescription = "Back",
                        tint = Color.White,
                        modifier = Modifier.size(24.dp)
                )
            }
            Box(modifier = Modifier.size(4.dp, 24.dp).background(PositiveGreen))
            Spacer(modifier = Modifier.width(12.dp))
            Text(
                    text = stringResource(R.string.language),
                    style = MaterialTheme.typography.headlineMedium,
                    fontWeight = FontWeight.Bold,
                    color = Color.White
            )
        }

        Spacer(modifier = Modifier.height(24.dp))

        // Language Options
        languages.forEach { language ->
            LanguageOptionCard(
                    language = language,
                    isSelected = selectedLanguage == language.code,
                    onClick = { viewModel.setLanguage(language.code) }
            )
            Spacer(modifier = Modifier.height(12.dp))
        }

        Spacer(modifier = Modifier.height(24.dp))
        Text(
                text = "Language will change immediately",
                style = MaterialTheme.typography.bodySmall,
                color = Color.White.copy(alpha = 0.5f)
        )
    }
}

@Composable
private fun LanguageOptionCard(language: LanguageOption, isSelected: Boolean, onClick: () -> Unit) {
    Card(
            modifier =
                    Modifier.fillMaxWidth()
                            .clickable(onClick = onClick)
                            .then(
                                    if (isSelected) {
                                        Modifier.border(
                                                width = 2.dp,
                                                color = PositiveGreen,
                                                shape = MaterialTheme.shapes.medium
                                        )
                                    } else {
                                        Modifier
                                    }
                            ),
            colors =
                    CardDefaults.cardColors(
                            containerColor =
                                    if (isSelected) PositiveGreen.copy(alpha = 0.2f)
                                    else Color.White.copy(alpha = 0.05f)
                    )
    ) {
        Row(
                modifier = Modifier.fillMaxWidth().padding(16.dp),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Column(modifier = Modifier.weight(1f)) {
                Text(
                        text = language.name,
                        style = MaterialTheme.typography.titleMedium,
                        fontWeight = FontWeight.Bold,
                        color = Color.White
                )
                Spacer(modifier = Modifier.height(4.dp))
                Text(
                        text = language.nativeName,
                        style = MaterialTheme.typography.bodySmall,
                        color = Color.White.copy(alpha = 0.6f)
                )
            }

            if (isSelected) {
                Icon(
                        imageVector = Icons.Default.Check,
                        contentDescription = "Selected",
                        tint = PositiveGreen,
                        modifier = Modifier.size(24.dp)
                )
            }
        }
    }
}
