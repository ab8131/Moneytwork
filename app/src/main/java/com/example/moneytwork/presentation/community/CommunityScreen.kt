package com.example.moneytwork.presentation.community

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Email
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.example.moneytwork.presentation.auth.AuthViewModel
import com.example.moneytwork.ui.theme.PositiveGreen

data class ChatRoom(
    val assetId: String,
    val assetName: String,
    val lastMessage: String = "Tap to view chat",
    val messageCount: Int = 0
)

@Composable
fun CommunityScreen(
    navController: NavController? = null,
    viewModel: AuthViewModel = hiltViewModel()
) {
    val isSignedIn by viewModel.isUserSignedIn.collectAsState()

    val chatRooms = listOf(
        ChatRoom("bitcoin", "Bitcoin", "Join the Bitcoin discussion", 156),
        ChatRoom("ethereum", "Ethereum", "Join the Ethereum discussion", 89),
        ChatRoom("AAPL", "Apple Inc.", "Join the AAPL discussion", 234),
        ChatRoom("TSLA", "Tesla Inc.", "Join the TSLA discussion", 178),
        ChatRoom("GOOGL", "Alphabet Inc.", "Join the GOOGL discussion", 92)
    )

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background)
            .padding(16.dp)
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Box(
                modifier = Modifier
                    .size(4.dp, 24.dp)
                    .background(PositiveGreen)
            )
            Spacer(modifier = Modifier.width(12.dp))
            Text(
                text = "Community Chats",
                style = MaterialTheme.typography.headlineMedium,
                fontWeight = FontWeight.Bold,
                color = Color.White
            )
        }

        Spacer(modifier = Modifier.height(8.dp))

        Text(
            text = if (isSignedIn)
                "Join conversations about your favorite assets"
            else
                "Sign in to participate in chats",
            style = MaterialTheme.typography.bodyMedium,
            color = Color.White.copy(alpha = 0.7f)
        )

        Spacer(modifier = Modifier.height(24.dp))

        if (!isSignedIn) {
            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .clickable { navController?.navigate("sign_in") },
                colors = CardDefaults.cardColors(
                    containerColor = Color.White.copy(alpha = 0.1f)
                )
            ) {
                Column(
                    modifier = Modifier.padding(24.dp),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Icon(
                        Icons.Default.Email,
                        contentDescription = null,
                        modifier = Modifier.size(48.dp),
                        tint = PositiveGreen
                    )
                    Spacer(modifier = Modifier.height(16.dp))
                    Text(
                        text = "Sign in to chat",
                        style = MaterialTheme.typography.titleLarge,
                        color = Color.White,
                        fontWeight = FontWeight.Bold
                    )
                    Spacer(modifier = Modifier.height(8.dp))
                    Text(
                        text = "Join the community and discuss your favorite assets",
                        style = MaterialTheme.typography.bodyMedium,
                        color = Color.White.copy(alpha = 0.7f)
                    )
                }
            }
        } else {
            LazyColumn(
                verticalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                items(chatRooms) { room ->
                    ChatRoomItem(
                        room = room,
                        onClick = {
                            if (room.assetId == room.assetId.lowercase()) {
                                navController?.navigate("coin_detail/${room.assetId}")
                            } else {
                                navController?.navigate("stock_detail/${room.assetId}")
                            }
                        }
                    )
                }
            }
        }
    }
}

@Composable
private fun ChatRoomItem(
    room: ChatRoom,
    onClick: () -> Unit
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .clickable(onClick = onClick),
        colors = CardDefaults.cardColors(
            containerColor = Color.White.copy(alpha = 0.05f)
        ),
        shape = RoundedCornerShape(12.dp)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Box(
                modifier = Modifier
                    .size(48.dp)
                    .clip(CircleShape)
                    .background(PositiveGreen.copy(alpha = 0.2f)),
                contentAlignment = Alignment.Center
            ) {
                Icon(
                    Icons.Default.Email,
                    contentDescription = null,
                    tint = PositiveGreen,
                    modifier = Modifier.size(24.dp)
                )
            }

            Spacer(modifier = Modifier.width(16.dp))

            Column(modifier = Modifier.weight(1f)) {
                Text(
                    text = room.assetName,
                    style = MaterialTheme.typography.titleMedium,
                    fontWeight = FontWeight.Bold,
                    color = Color.White
                )
                Spacer(modifier = Modifier.height(4.dp))
                Text(
                    text = room.lastMessage,
                    style = MaterialTheme.typography.bodySmall,
                    color = Color.White.copy(alpha = 0.6f)
                )
            }

            if (room.messageCount > 0) {
                Box(
                    modifier = Modifier
                        .clip(CircleShape)
                        .background(PositiveGreen)
                        .padding(horizontal = 8.dp, vertical = 4.dp)
                ) {
                    Text(
                        text = room.messageCount.toString(),
                        style = MaterialTheme.typography.labelSmall,
                        color = Color.White,
                        fontWeight = FontWeight.Bold
                    )
                }
            }
        }
    }
}

