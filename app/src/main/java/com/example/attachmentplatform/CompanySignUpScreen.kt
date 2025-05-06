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
import com.google.firebase.auth.FirebaseUser

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CompanySignUpScreen(
    auth: FirebaseAuth,
    navController: NavHostController,
    onSignUpSuccess: (FirebaseUser) -> Unit
) {
    val context = LocalContext.current

    var companyName by remember { mutableStateOf("") }
    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    var confirmPassword by remember { mutableStateOf("") }
    var passwordVisible by remember { mutableStateOf(false) }
    var confirmPasswordVisible by remember { mutableStateOf(false) }
    var isLoading by remember { mutableStateOf(false) }

    fun validateAndSignUp() {
        when {
            companyName.isBlank() || email.isBlank() || password.isBlank() || confirmPassword.isBlank() -> {
                Toast.makeText(context, "Please fill in all fields.", Toast.LENGTH_SHORT).show()
            }
            !Patterns.EMAIL_ADDRESS.matcher(email).matches() -> {
                Toast.makeText(context, "Please enter a valid email address.", Toast.LENGTH_SHORT).show()
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
                            task.result?.user?.let {
                                Toast.makeText(context, "Sign up successful!", Toast.LENGTH_SHORT).show()
                                onSignUpSuccess(it)

                                // Navigate to CompanyDashboardScreen
                                navController.navigate(NavRoutes.COMPANY_HOME_SCREEN) {
                                    popUpTo(NavRoutes.COMPANY_SIGN_UP_SCREEN) { inclusive = true }
                                    launchSingleTop = true
                                }
                            }
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
                        text = "Company Sign Up",
                        style = MaterialTheme.typography.headlineMedium,
                        modifier = Modifier.padding(bottom = 24.dp)
                    )

                    OutlinedTextField(
                        value = companyName,
                        onValueChange = { companyName = it },
                        label = { Text("Company Name") },
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(vertical = 4.dp)
                    )

                    OutlinedTextField(
                        value = email,
                        onValueChange = { email = it },
                        label = { Text("Company Email") },
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

                    Button(
                        onClick = { validateAndSignUp() },
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(top = 16.dp)
                    ) {
                        Text("Create Company Account")
                    }

                    Spacer(modifier = Modifier.height(16.dp))

                    TextButton(onClick = {
                        navController.navigate(NavRoutes.STUDENT_SIGN_UP_SCREEN) {
                            launchSingleTop = true
                        }
                    }) {
                        Text("Are you a student? Sign up here")
                    }

                    TextButton(onClick = {
                        navController.navigate(NavRoutes.COMPANY_SIGN_IN_SCREEN)
                    }) {
                        Text("Already a company? Sign in")
                    }
                }
            }
        }
    }
}
