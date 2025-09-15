package com.example.sideapp.data.remote

import com.google.firebase.auth.FirebaseAuth
import kotlinx.coroutines.tasks.await

class ApiService(private val auth: FirebaseAuth = FirebaseAuth.getInstance()) {

    // 🔹 Signup
    suspend fun signup(email: String, password: String): String {
        val result = auth.createUserWithEmailAndPassword(email, password).await()
        return result.user?.uid ?: throw Exception("Signup failed")
    }

    // 🔹 Login
    suspend fun login(email: String, password: String): String {
        val result = auth.signInWithEmailAndPassword(email, password).await()
        return result.user?.uid ?: throw Exception("Login failed")
    }

    // 🔹 Logout
    fun logout() {
        auth.signOut()
    }

    // 🔹 Get current userId
    fun currentUserId(): String? = auth.currentUser?.uid
}
