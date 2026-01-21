package com.example.moneytwork.domain.repository

import com.example.moneytwork.core.util.Resource
import com.example.moneytwork.domain.model.User
import kotlinx.coroutines.flow.Flow

interface AuthRepository {
    val currentUser: Flow<User?>
    val isUserSignedIn: Flow<Boolean>

    suspend fun signUp(username: String, email: String, password: String): Resource<User>
    suspend fun signIn(email: String, password: String): Resource<User>
    suspend fun signOut(): Resource<Unit>
    suspend fun getCurrentUser(): User?
}

