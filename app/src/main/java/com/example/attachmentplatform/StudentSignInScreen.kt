package com.attachmentplatform.ui.screens

import android.util.Patterns
import android.widget.Toast
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Visibility
import androidx.compose.material.icons.filled.VisibilityOff
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.input.*
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.attachmentplatform.NavRoutes
import com.google.firebase.auth.FirebaseAuth

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SignInScreen(
    auth: FirebaseAuth,
    navController: NavHostController,
    onSignInSuccess: () -> Unit // This lambda handles the navigation
) {
    val context = LocalContext.current

    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    var passwordVisible by remember { mutableStateOf(false) }
    var isLoading by remember { mutableStateOf(false) }

    fun validateAndSignIn() {
        when {
            email.isBlank() || password.isBlank() -> {
                Toast.makeText(context, "Please fill in all fields.", Toast.LENGTH_SHORT).show()
            }

            !Patterns.EMAIL_ADDRESS.matcher(email).matches() -> {
                Toast.makeText(context, "Please enter a valid email address.", Toast.LENGTH_SHORT).show()
            }

            password.length < 6 -> {
                Toast.makeText(context, "Password must be at least 6 characters.", Toast.LENGTH_SHORT).show()
            }

            else -> {
                isLoading = true
                auth.signInWithEmailAndPassword(email.trim(), password)
                    .addOnCompleteListener { task ->
                        isLoading = false
                        if (task.isSuccessful) {
                            Toast.makeText(context, "Login successful!", Toast.LENGTH_SHORT).show()
                            onSignInSuccess() // Call the lambda to trigger navigation from MainScreen.kt
                            // REMOVE the direct navigation call here:
                            /*
                            navController.navigate(NavRoutes.STUDENT_HOME_SCREEN) {
                                popUpTo(NavRoutes.STUDENT_SIGN_IN_SCREEN) { inclusive = true }
                                launchSingleTop = true
                            }
                            */
                        } else {
                            Toast.makeText(
                                context,
                                task.exception?.localizedMessage ?: "Sign-in failed.",
                                Toast.LENGTH_LONG
                            ).show()
                        }
                    }
            }
        }
    }

    // ... (rest of your composable remains the same)

    Surface(
        modifier = Modifier.fillMaxSize(),
        color = MaterialTheme.colorScheme.background
    ) {
        Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
            if (isLoading) {
                CircularProgressIndicator()
            } else {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 24.dp),
                    verticalArrangement = Arrangement.Center,
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Text(
                        text = "Student Sign In",
                        style = MaterialTheme.typography.headlineMedium,
                        modifier = Modifier.padding(bottom = 24.dp)
                    )

                    OutlinedTextField(
                        value = email,
                        onValueChange = { email = it },
                        label = { Text("Email") },
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(vertical = 4.dp),
                        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Email),
                        singleLine = true
                    )

                    OutlinedTextField(
                        value = password,
                        onValueChange = { password = it },
                        label = { Text("Password") },
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(vertical = 4.dp),
                        visualTransformation = if (passwordVisible) VisualTransformation.None else PasswordVisualTransformation(),
                        trailingIcon = {
                            val icon = if (passwordVisible) Icons.Filled.Visibility else Icons.Filled.VisibilityOff
                            IconButton(onClick = { passwordVisible = !passwordVisible }) {
                                Icon(icon, contentDescription = "Toggle Password Visibility")
                            }
                        },
                        singleLine = true
                    )

                    Spacer(modifier = Modifier.height(16.dp))

                    Button(
                        onClick = { validateAndSignIn() },
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        Text("Sign In")
                    }

                    Spacer(modifier = Modifier.height(16.dp))

                    TextButton(onClick = {
                        navController.navigate(NavRoutes.STUDENT_SIGN_UP_SCREEN) {
                            launchSingleTop = true
                        }
                    }) {
                        Text("Don't have an account? Sign up")
                    }

                    TextButton(onClick = {
                        navController.navigate(NavRoutes.COMPANY_SIGN_IN_SCREEN) {
                            launchSingleTop = true
                        }
                    }) {
                        Text("Are you a company? Sign in")
                    }
                }
            }
        }
    }
}