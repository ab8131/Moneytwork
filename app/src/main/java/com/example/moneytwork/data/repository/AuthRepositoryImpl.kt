package com.example.moneytwork.data.repository

import android.util.Log
import com.example.moneytwork.core.util.Resource
import com.example.moneytwork.domain.model.User
import com.example.moneytwork.domain.repository.AuthRepository
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.UserProfileChangeRequest
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow
import kotlinx.coroutines.tasks.await
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class AuthRepositoryImpl @Inject constructor(
    private val auth: FirebaseAuth,
    private val firestore: FirebaseFirestore
) : AuthRepository {

    override val currentUser: Flow<User?> = callbackFlow {
        val listener = FirebaseAuth.AuthStateListener { firebaseAuth ->
            val firebaseUser = firebaseAuth.currentUser
            if (firebaseUser != null) {
                val user = User(
                    uid = firebaseUser.uid,
                    username = firebaseUser.displayName ?: "",
                    email = firebaseUser.email ?: ""
                )
                trySend(user)
            } else {
                trySend(null)
            }
        }
        auth.addAuthStateListener(listener)
        awaitClose { auth.removeAuthStateListener(listener) }
    }

    override val isUserSignedIn: Flow<Boolean> = callbackFlow {
        val listener = FirebaseAuth.AuthStateListener { firebaseAuth ->
            trySend(firebaseAuth.currentUser != null)
        }
        auth.addAuthStateListener(listener)
        awaitClose { auth.removeAuthStateListener(listener) }
    }

    override suspend fun signUp(username: String, email: String, password: String): Resource<User> {
        return try {
            Log.d("AuthRepository", "Signing up user: $email")

            // Create user with email and password
            val result = auth.createUserWithEmailAndPassword(email, password).await()
            val firebaseUser = result.user ?: return Resource.Error("Failed to create user")

            // Update profile with username
            val profileUpdates = UserProfileChangeRequest.Builder()
                .setDisplayName(username)
                .build()
            firebaseUser.updateProfile(profileUpdates).await()

            // Save user to Firestore
            val user = User(
                uid = firebaseUser.uid,
                username = username,
                email = email,
                createdAt = System.currentTimeMillis()
            )

            firestore.collection("users")
                .document(firebaseUser.uid)
                .set(user)
                .await()

            Log.d("AuthRepository", "Sign up successful")
            Resource.Success(user)
        } catch (e: Exception) {
            Log.e("AuthRepository", "Sign up failed", e)
            Resource.Error(e.localizedMessage ?: "Sign up failed")
        }
    }

    override suspend fun signIn(email: String, password: String): Resource<User> {
        return try {
            Log.d("AuthRepository", "Signing in user: $email")

            val result = auth.signInWithEmailAndPassword(email, password).await()
            val firebaseUser = result.user ?: return Resource.Error("Failed to sign in")

            // Get user from Firestore
            val userDoc = firestore.collection("users")
                .document(firebaseUser.uid)
                .get()
                .await()

            val user = if (userDoc.exists()) {
                User(
                    uid = userDoc.getString("uid") ?: firebaseUser.uid,
                    username = userDoc.getString("username") ?: firebaseUser.displayName ?: "",
                    email = userDoc.getString("email") ?: firebaseUser.email ?: "",
                    createdAt = userDoc.getLong("createdAt") ?: System.currentTimeMillis()
                )
            } else {
                // Fallback if Firestore doc doesn't exist
                User(
                    uid = firebaseUser.uid,
                    username = firebaseUser.displayName ?: "",
                    email = firebaseUser.email ?: ""
                )
            }

            Log.d("AuthRepository", "Sign in successful")
            Resource.Success(user)
        } catch (e: Exception) {
            Log.e("AuthRepository", "Sign in failed", e)
            Resource.Error(e.localizedMessage ?: "Sign in failed")
        }
    }

    override suspend fun signOut(): Resource<Unit> {
        return try {
            auth.signOut()
            Resource.Success(Unit)
        } catch (e: Exception) {
            Resource.Error(e.localizedMessage ?: "Sign out failed")
        }
    }

    override suspend fun getCurrentUser(): User? {
        val firebaseUser = auth.currentUser ?: return null

        return try {
            val userDoc = firestore.collection("users")
                .document(firebaseUser.uid)
                .get()
                .await()

            if (userDoc.exists()) {
                User(
                    uid = userDoc.getString("uid") ?: firebaseUser.uid,
                    username = userDoc.getString("username") ?: firebaseUser.displayName ?: "",
                    email = userDoc.getString("email") ?: firebaseUser.email ?: "",
                    createdAt = userDoc.getLong("createdAt") ?: System.currentTimeMillis()
                )
            } else {
                User(
                    uid = firebaseUser.uid,
                    username = firebaseUser.displayName ?: "",
                    email = firebaseUser.email ?: ""
                )
            }
        } catch (e: Exception) {
            Log.e("AuthRepository", "Error getting current user", e)
            null
        }
    }
}

