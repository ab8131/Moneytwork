package com.example.moneytwork.presentation.auth

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.moneytwork.core.util.Resource
import com.example.moneytwork.domain.repository.AuthRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject

data class AuthState(
    val isLoading: Boolean = false,
    val error: String? = null,
    val isSuccess: Boolean = false
)

@HiltViewModel
class AuthViewModel @Inject constructor(
    private val authRepository: AuthRepository
) : ViewModel() {

    private val _signUpState = mutableStateOf(AuthState())
    val signUpState: State<AuthState> = _signUpState

    private val _signInState = mutableStateOf(AuthState())
    val signInState: State<AuthState> = _signInState

    val currentUser = authRepository.currentUser.stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(5000),
        initialValue = null
    )

    val isUserSignedIn = authRepository.isUserSignedIn.stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(5000),
        initialValue = false
    )

    fun signUp(username: String, email: String, password: String) {
        viewModelScope.launch {
            _signUpState.value = AuthState(isLoading = true)

            val result = authRepository.signUp(username, email, password)

            _signUpState.value = when (result) {
                is Resource.Success -> AuthState(isSuccess = true)
                is Resource.Error -> AuthState(error = result.message)
                is Resource.Loading -> AuthState(isLoading = true)
            }
        }
    }

    fun signIn(email: String, password: String) {
        viewModelScope.launch {
            _signInState.value = AuthState(isLoading = true)

            val result = authRepository.signIn(email, password)

            _signInState.value = when (result) {
                is Resource.Success -> AuthState(isSuccess = true)
                is Resource.Error -> AuthState(error = result.message)
                is Resource.Loading -> AuthState(isLoading = true)
            }
        }
    }

    fun signOut() {
        viewModelScope.launch {
            authRepository.signOut()
        }
    }

    fun clearError() {
        _signUpState.value = _signUpState.value.copy(error = null)
        _signInState.value = _signInState.value.copy(error = null)
    }
}

