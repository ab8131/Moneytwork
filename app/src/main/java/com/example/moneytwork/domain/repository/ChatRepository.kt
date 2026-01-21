package com.example.moneytwork.domain.repository

import com.example.moneytwork.core.util.Resource
import com.example.moneytwork.domain.model.ChatMessage
import kotlinx.coroutines.flow.Flow

interface ChatRepository {
    fun getMessages(assetId: String): Flow<Resource<List<ChatMessage>>>
    suspend fun sendMessage(assetId: String, assetName: String, message: String): Resource<Unit>
}

