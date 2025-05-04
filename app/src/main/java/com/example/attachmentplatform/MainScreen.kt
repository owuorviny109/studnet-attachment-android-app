package com.attachmentplatform

import android.app.Activity
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.attachmentplatform.ui.screens.AuthScreen
import com.attachmentplatform.ui.screens.CompanyHomeScreen
import com.attachmentplatform.ui.screens.CompanySignUpScreen
import com.attachmentplatform.ui.screens.SignInScreen
import com.attachmentplatform.ui.screens.StudentHomeScreen
import com.attachmentplatform.ui.screens.StudentSignUpScreen
import com.google.firebase.auth.FirebaseAuth

@Composable
fun MainScreen(auth: FirebaseAuth) {
    val navController = rememberNavController()

    NavHost(navController = navController, startDestination = NavRoutes.START_SCREEN) {
        composable(NavRoutes.START_SCREEN) {
            Surface(
                modifier = Modifier.fillMaxSize(),
                color = MaterialTheme.colorScheme.background
            ) {
                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(24.dp),
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.Center
                ) {
                    // Header
                    Text(
                        text = "Welcome to AttachME!",
                        style = MaterialTheme.typography.headlineLarge,
                        modifier = Modifier.padding(bottom = 32.dp)
                    )
                    // Sign In Button
                    Button(
                        onClick = { navController.navigate(NavRoutes.STUDENT_SIGN_IN_SCREEN) },
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        Text("Sign In")
                    }

                    // Sign Up Button
                    Button(
                        onClick = { navController.navigate(NavRoutes.STUDENT_SIGN_UP_SCREEN) },
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        Text("Sign Up")
                    }
                    // Get the current activity context
                    val context = LocalContext.current
                    if (context is Activity) {
                        // Exit App Button
                        TextButton(onClick = {
                            context.finish() // Finish the current Activity, effectively closing the app
                        }) {
                            Text("Exit the App")
                        }
                    }
                }
            }
        }

        composable(NavRoutes.AUTH_SCREEN) {
            AuthScreen(auth = auth, navController = navController)
        }

        composable(NavRoutes.STUDENT_SIGN_UP_SCREEN) {
            StudentSignUpScreen(
                auth = auth,
                navController = navController,
                onSignUpSuccess = { navController.navigate(NavRoutes.STUDENT_HOME) }
            )
        }

        composable(NavRoutes.COMPANY_SIGN_UP_SCREEN) {
            CompanySignUpScreen(
                auth = auth,
                navController = navController,
                onSignUpSuccess = { navController.navigate(NavRoutes.COMPANY_HOME) }
            )
        }

        composable(NavRoutes.STUDENT_HOME) {
            StudentHomeScreen()
        }

        composable(NavRoutes.COMPANY_HOME) {
            CompanyHomeScreen(navController = navController)
        }
        composable(NavRoutes.STUDENT_SIGN_IN_SCREEN) {
            SignInScreen(navController = navController, auth = auth, onSignInSuccess = {})
        }
    }
}