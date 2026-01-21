package com.example.moneytwork.domain.model

data class ChatMessage(
    val id: String = "",
    val assetId: String = "",
    val assetName: String = "",
    val userId: String = "",
    val username: String = "",
    val message: String = "",
    val timestamp: Long = System.currentTimeMillis()
)

