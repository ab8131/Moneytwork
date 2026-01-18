package com.example.moneytwork.presentation.chat

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.moneytwork.core.util.Resource
import com.example.moneytwork.domain.model.ChatMessage
import com.example.moneytwork.domain.repository.AuthRepository
import com.example.moneytwork.domain.repository.ChatRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import javax.inject.Inject

data class ChatState(
    val messages: List<ChatMessage> = emptyList(),
    val isLoading: Boolean = false,
    val error: String? = null,
    val isSendingMessage: Boolean = false
)

@HiltViewModel
class ChatViewModel @Inject constructor(
    private val chatRepository: ChatRepository,
    private val authRepository: AuthRepository,
    savedStateHandle: SavedStateHandle
) : ViewModel() {

    private val _state = mutableStateOf(ChatState())
    val state: State<ChatState> = _state

    fun loadMessages(assetId: String) {
        chatRepository.getMessages(assetId).onEach { result ->
            when (result) {
                is Resource.Success -> {
                    _state.value = ChatState(
                        messages = result.data ?: emptyList(),
                        isLoading = false
                    )
                }
                is Resource.Error -> {
                    _state.value = ChatState(
                        error = result.message,
                        isLoading = false
                    )
                }
                is Resource.Loading -> {
                    _state.value = ChatState(isLoading = true)
                }
            }
        }.launchIn(viewModelScope)
    }

    fun sendMessage(assetId: String, assetName: String, message: String) {
        if (message.isBlank()) return

        viewModelScope.launch {
            _state.value = _state.value.copy(isSendingMessage = true)

            val result = chatRepository.sendMessage(assetId, assetName, message)

            _state.value = _state.value.copy(
                isSendingMessage = false,
                error = if (result is Resource.Error) result.message else null
            )
        }
    }

    suspend fun isUserSignedIn(): Boolean {
        return authRepository.getCurrentUser() != null
    }
}

