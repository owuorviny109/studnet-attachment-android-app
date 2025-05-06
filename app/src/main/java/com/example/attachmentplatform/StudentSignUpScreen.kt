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
import com.google.firebase.auth.FirebaseAuth
import com.attachmentplatform.NavRoutes

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun StudentSignUpScreen(
    auth: FirebaseAuth,
    navController: NavHostController,
    onSignUpSuccess: () -> Unit = {
        navController.navigate(NavRoutes.STUDENT_HOME_SCREEN) {
            popUpTo(NavRoutes.STUDENT_SIGN_UP_SCREEN) { inclusive = true }
            launchSingleTop = true
        }
    }
) {
    val context = LocalContext.current

    var email by remember { mutableStateOf("") }
    var fullName by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    var confirmPassword by remember { mutableStateOf("") }
    var passwordVisible by remember { mutableStateOf(false) }
    var confirmPasswordVisible by remember { mutableStateOf(false) }
    var isLoading by remember { mutableStateOf(false) }

    fun validateAndSignUp() {
        when {
            fullName.isBlank() || email.isBlank() || password.isBlank() || confirmPassword.isBlank() -> {
                Toast.makeText(context, "Please fill in all fields.", Toast.LENGTH_SHORT).show()
            }
            !Patterns.EMAIL_ADDRESS.matcher(email).matches() -> {
                Toast.makeText(context, "Invalid email format.", Toast.LENGTH_SHORT).show()
            }
            password.length < 6 -> {
                Toast.makeText(context, "Password must be at least 6 characters.", Toast.LENGTH_SHORT).show()
            }
            password != confirmPassword -> {
                Toast.makeText(context, "Passwords do not match.", Toast.LENGTH_SHORT).show()
            }
            else -> {
                isLoading = true
                auth.createUserWithEmailAndPassword(email.trim(), password)
                    .addOnCompleteListener { task ->
                        isLoading = false
                        if (task.isSuccessful) {
                            Toast.makeText(context, "Sign up successful!", Toast.LENGTH_SHORT).show()
                            onSignUpSuccess() // Call the lambda to trigger navigation from MainScreen.kt
                        } else {
                            Toast.makeText(
                                context,
                                task.exception?.message ?: "Sign up failed.",
                                Toast.LENGTH_LONG
                            ).show()
                        }
                    }
            }
        }
    }

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
                        text = "Student Sign Up",
                        style = MaterialTheme.typography.headlineMedium,
                        modifier = Modifier.padding(bottom = 24.dp)
                    )

                    OutlinedTextField(
                        value = fullName,
                        onValueChange = { fullName = it },
                        label = { Text("Full Name") },
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(vertical = 4.dp)
                    )

                    OutlinedTextField(
                        value = email,
                        onValueChange = { email = it },
                        label = { Text("Email") },
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(vertical = 4.dp),
                        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Email)
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
                            IconButton(onClick = { passwordVisible = !passwordVisible }) {
                                Icon(
                                    if (passwordVisible) Icons.Default.Visibility else Icons.Default.VisibilityOff,
                                    contentDescription = "Toggle Password Visibility"
                                )
                            }
                        },
                        singleLine = true
                    )

                    OutlinedTextField(
                        value = confirmPassword,
                        onValueChange = { confirmPassword = it },
                        label = { Text("Confirm Password") },
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(vertical = 4.dp),
                        visualTransformation = if (confirmPasswordVisible) VisualTransformation.None else PasswordVisualTransformation(),
                        trailingIcon = {
                            IconButton(onClick = { confirmPasswordVisible = !confirmPasswordVisible }) {
                                Icon(
                                    if (confirmPasswordVisible) Icons.Default.Visibility else Icons.Default.VisibilityOff,
                                    contentDescription = "Toggle Confirm Password Visibility"
                                )
                            }
                        },
                        singleLine = true
                    )

                    Spacer(modifier = Modifier.height(16.dp))

                    Button(
                        onClick = { validateAndSignUp() },
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        Text("Create Account")
                    }

                    Spacer(modifier = Modifier.height(16.dp))

                    TextButton(onClick = {
                        navController.navigate(NavRoutes.COMPANY_SIGN_UP_SCREEN) {
                            launchSingleTop = true
                        }
                    }) {
                        Text("Are you a company? Sign up here")
                    }

                    TextButton(onClick = {
                        navController.navigate(NavRoutes.STUDENT_SIGN_IN_SCREEN) {
                            launchSingleTop = true
                        }
                    }) {
                        Text("Already a student? Sign in")
                    }
                }
            }
        }
    }
}
