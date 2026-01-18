package com.example.moneytwork.data.repository

import android.util.Log
import com.example.moneytwork.core.util.Resource
import com.example.moneytwork.domain.model.ChatMessage
import com.example.moneytwork.domain.repository.AuthRepository
import com.example.moneytwork.domain.repository.ChatRepository
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.Query
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow
import kotlinx.coroutines.tasks.await
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class ChatRepositoryImpl @Inject constructor(
    private val firestore: FirebaseFirestore,
    private val authRepository: AuthRepository
) : ChatRepository {

    override fun getMessages(assetId: String): Flow<Resource<List<ChatMessage>>> = callbackFlow {
        Log.d("ChatRepository", "Subscribing to messages for asset: $assetId")

        trySend(Resource.Loading())

        val subscription = firestore.collection("chats")
            .document(assetId)
            .collection("messages")
            .orderBy("timestamp", Query.Direction.ASCENDING)
            .limit(100) // Limit to last 100 messages
            .addSnapshotListener { snapshot, error ->
                if (error != null) {
                    Log.e("ChatRepository", "Error listening to messages", error)
                    trySend(Resource.Error(error.localizedMessage ?: "Failed to load messages"))
                    return@addSnapshotListener
                }

                if (snapshot != null) {
                    val messages = snapshot.documents.mapNotNull { doc ->
                        try {
                            ChatMessage(
                                id = doc.id,
                                assetId = doc.getString("assetId") ?: "",
                                assetName = doc.getString("assetName") ?: "",
                                userId = doc.getString("userId") ?: "",
                                username = doc.getString("username") ?: "Anonymous",
                                message = doc.getString("message") ?: "",
                                timestamp = doc.getLong("timestamp") ?: 0L
                            )
                        } catch (e: Exception) {
                            Log.e("ChatRepository", "Error parsing message", e)
                            null
                        }
                    }
                    Log.d("ChatRepository", "Received ${messages.size} messages")
                    trySend(Resource.Success(messages))
                }
            }

        awaitClose {
            Log.d("ChatRepository", "Unsubscribing from messages")
            subscription.remove()
        }
    }

    override suspend fun sendMessage(assetId: String, assetName: String, message: String): Resource<Unit> {
        return try {
            val currentUser = authRepository.getCurrentUser()
                ?: return Resource.Error("You must be signed in to send messages")

            Log.d("ChatRepository", "Sending message to asset: $assetId")

            val chatMessage = hashMapOf(
                "assetId" to assetId,
                "assetName" to assetName,
                "userId" to currentUser.uid,
                "username" to currentUser.username,
                "message" to message,
                "timestamp" to System.currentTimeMillis()
            )

            firestore.collection("chats")
                .document(assetId)
                .collection("messages")
                .add(chatMessage)
                .await()

            Log.d("ChatRepository", "Message sent successfully")
            Resource.Success(Unit)
        } catch (e: Exception) {
            Log.e("ChatRepository", "Failed to send message", e)
            Resource.Error(e.localizedMessage ?: "Failed to send message")
        }
    }
}

