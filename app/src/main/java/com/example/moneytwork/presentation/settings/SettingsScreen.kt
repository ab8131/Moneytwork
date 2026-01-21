package com.example.moneytwork.presentation.settings

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowForward
import androidx.compose.material.icons.automirrored.filled.ExitToApp
import androidx.compose.material.icons.filled.Email
import androidx.compose.material.icons.filled.Person
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.example.moneytwork.R
import com.example.moneytwork.presentation.auth.AuthViewModel
import com.example.moneytwork.ui.theme.PositiveGreen

@Composable
fun SettingsScreen(navController: NavController, authViewModel: AuthViewModel = hiltViewModel()) {
    Column(
            modifier =
                    Modifier.fillMaxSize()
                            .background(MaterialTheme.colorScheme.background)
                            .padding(16.dp)
    ) {
        Row(modifier = Modifier.fillMaxWidth(), verticalAlignment = Alignment.CenterVertically) {
            Box(modifier = Modifier.size(4.dp, 24.dp).background(PositiveGreen))
            Spacer(modifier = Modifier.width(12.dp))
            Text(
                    text = stringResource(R.string.settings),
                    style = MaterialTheme.typography.headlineMedium,
                    fontWeight = FontWeight.Bold,
                    color = Color.White
            )
        }
        Spacer(modifier = Modifier.height(24.dp))
        SettingsMenuItem(Icons.Default.Person, stringResource(R.string.profile), stringResource(R.string.view_and_edit_your_profile)) {
            navController.navigate("profile")
        }
        Spacer(modifier = Modifier.height(12.dp))
        SettingsMenuItem(Icons.Default.Email, stringResource(R.string.language), stringResource(R.string.change_app_language)) {
            navController.navigate("language")
        }
        Spacer(modifier = Modifier.height(12.dp))
        SettingsMenuItemLogout(
                Icons.AutoMirrored.Filled.ExitToApp,
                stringResource(R.string.logout),
                stringResource(R.string.sign_out_of_your_account)
        ) { authViewModel.signOut() }
    }
}

@Composable
private fun SettingsMenuItem(
        icon: ImageVector,
        title: String,
        subtitle: String,
        onClick: () -> Unit
) {
    Card(
            modifier = Modifier.fillMaxWidth().clickable(onClick = onClick),
            colors = CardDefaults.cardColors(containerColor = Color.White.copy(alpha = 0.05f))
    ) {
        Row(
                modifier = Modifier.fillMaxWidth().padding(16.dp),
                verticalAlignment = Alignment.CenterVertically
        ) {
            Icon(
                    imageVector = icon,
                    contentDescription = title,
                    tint = PositiveGreen,
                    modifier = Modifier.size(32.dp)
            )
            Spacer(modifier = Modifier.width(16.dp))
            Column(modifier = Modifier.weight(1f)) {
                Text(
                        text = title,
                        style = MaterialTheme.typography.titleMedium,
                        fontWeight = FontWeight.Bold,
                        color = Color.White
                )
                Spacer(modifier = Modifier.height(4.dp))
                Text(
                        text = subtitle,
                        style = MaterialTheme.typography.bodySmall,
                        color = Color.White.copy(alpha = 0.6f)
                )
            }
            Icon(
                    imageVector = Icons.AutoMirrored.Filled.ArrowForward,
                    contentDescription = "Navigate",
                    tint = Color.White.copy(alpha = 0.4f),
                    modifier = Modifier.size(20.dp)
            )
        }
    }
}

@Composable
private fun SettingsMenuItemLogout(
        icon: ImageVector,
        title: String,
        subtitle: String,
        onClick: () -> Unit
) {
    Card(
            modifier = Modifier.fillMaxWidth().clickable(onClick = onClick),
            colors =
                    CardDefaults.cardColors(
                            containerColor = MaterialTheme.colorScheme.error.copy(alpha = 0.1f)
                    )
    ) {
        Row(
                modifier = Modifier.fillMaxWidth().padding(16.dp),
                verticalAlignment = Alignment.CenterVertically
        ) {
            Icon(
                    imageVector = icon,
                    contentDescription = title,
                    tint = MaterialTheme.colorScheme.error,
                    modifier = Modifier.size(32.dp)
            )
            Spacer(modifier = Modifier.width(16.dp))
            Column(modifier = Modifier.weight(1f)) {
                Text(
                        text = title,
                        style = MaterialTheme.typography.titleMedium,
                        fontWeight = FontWeight.Bold,
                        color = MaterialTheme.colorScheme.error
                )
                Spacer(modifier = Modifier.height(4.dp))
                Text(
                        text = subtitle,
                        style = MaterialTheme.typography.bodySmall,
                        color = MaterialTheme.colorScheme.error.copy(alpha = 0.7f)
                )
            }
            Icon(
                    imageVector = Icons.AutoMirrored.Filled.ArrowForward,
                    contentDescription = "Navigate",
                    tint = MaterialTheme.colorScheme.error.copy(alpha = 0.4f),
                    modifier = Modifier.size(20.dp)
            )
        }
    }
}
