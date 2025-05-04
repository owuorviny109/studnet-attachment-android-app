package com.attachmentplatform

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.tasks.await

class AuthViewModel(private val auth: FirebaseAuth) : ViewModel() {

    private val db = Firebase.firestore

    private val _currentUser = MutableStateFlow<FirebaseUser?>(null)
    val currentUser: StateFlow<FirebaseUser?> = _currentUser.asStateFlow()

    private val _userType = MutableStateFlow<UserType?>(null)
    val userType: StateFlow<UserType?> = _userType.asStateFlow()

    private val _fetchState = MutableStateFlow(FetchState.Idle)
    val fetchState: StateFlow<FetchState> = _fetchState.asStateFlow()

    private fun fetchUserType(uid: String) {
        _fetchState.value = FetchState.Loading
        viewModelScope.launch {
            try {
                val document = db.collection("users").document(uid).get().await()
                if (document != null && document.exists()) {
                    val typeString = document.getString("userType")
                    val fetchedType = try {
                        typeString?.let { UserType.valueOf(it.uppercase()) }
                    } catch (e: IllegalArgumentException) {
                        Log.e("AuthViewModel", "Invalid userType in DB: $typeString for user $uid")
                        null
                    }

                    if (fetchedType != null) {
                        _userType.value = fetchedType
                        _fetchState.value = FetchState.Success
                        Log.d("AuthViewModel", "User type fetched successfully: $fetchedType for user $uid")
                    } else {
                        _userType.value = null
                        _fetchState.value = FetchState.Error
                        Log.e("AuthViewModel", "User type was null or invalid in DB for user $uid")
                    }
                } else {
                    _userType.value = null
                    _fetchState.value = FetchState.Error
                    Log.e("AuthViewModel", "User document not found in DB for $uid")
                }
            } catch (e: Exception) {
                _userType.value = null
                _fetchState.value = FetchState.Error
                Log.e("AuthViewModel", "Error fetching user type for $uid", e)
            }
        }
    }

    fun handleAuthenticationResult(user: FirebaseUser?) {
        _currentUser.value = user
        if (user != null) {
            fetchUserType(user.uid)
        } else {
            _userType.value = null
            _fetchState.value = FetchState.Idle
        }
    }
}
