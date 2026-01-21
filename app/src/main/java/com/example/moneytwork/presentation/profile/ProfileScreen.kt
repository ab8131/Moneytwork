package com.example.moneytwork.presentation.profile

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Person
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.example.moneytwork.R
import com.example.moneytwork.presentation.auth.AuthViewModel
import com.example.moneytwork.ui.theme.PositiveGreen

@Composable
fun ProfileScreen(navController: NavController, viewModel: AuthViewModel = hiltViewModel()) {
    val currentUser by viewModel.currentUser.collectAsState()

    Column(
            modifier =
                    Modifier.fillMaxSize()
                            .background(MaterialTheme.colorScheme.background)
                            .padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally
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
                    text = stringResource(R.string.profile),
                    style = MaterialTheme.typography.headlineMedium,
                    fontWeight = FontWeight.Bold,
                    color = Color.White
            )
        }

        Spacer(modifier = Modifier.height(48.dp))

        // Avatar
        Box(
                modifier =
                        Modifier.size(120.dp)
                                .clip(CircleShape)
                                .background(PositiveGreen.copy(alpha = 0.2f)),
                contentAlignment = Alignment.Center
        ) {
            Icon(
                    imageVector = Icons.Default.Person,
                    contentDescription = "Profile",
                    modifier = Modifier.size(64.dp),
                    tint = PositiveGreen
            )
        }

        Spacer(modifier = Modifier.height(24.dp))

        // Username
        Text(
                text = currentUser?.username ?: "User",
                style = MaterialTheme.typography.headlineSmall,
                fontWeight = FontWeight.Bold,
                color = Color.White
        )

        Spacer(modifier = Modifier.height(8.dp))

        // Email
        Text(
                text = currentUser?.email ?: "",
                style = MaterialTheme.typography.bodyLarge,
                color = Color.White.copy(alpha = 0.7f)
        )

        Spacer(modifier = Modifier.height(48.dp))

        Text(
                text = "${stringResource(R.string.member_since)} ${formatDate(currentUser?.createdAt ?: 0L)}",
                style = MaterialTheme.typography.bodySmall,
                color = Color.White.copy(alpha = 0.5f)
        )
    }
}

private fun formatDate(timestamp: Long): String {
    if (timestamp == 0L) return ""
    val date = java.util.Date(timestamp)
    val format = java.text.SimpleDateFormat("MMM yyyy", java.util.Locale.getDefault())
    return format.format(date)
}
