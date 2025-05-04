package com.attachmentplatform

import android.app.Activity
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.attachmentplatform.ui.screens.*
import com.google.firebase.auth.FirebaseAuth

@Composable
fun MainScreen(auth: FirebaseAuth) {
    val navController = rememberNavController()

    NavHost(
        navController = navController,
        startDestination = NavRoutes.STUDENT_SIGN_IN_SCREEN // ✅ Changed from START_SCREEN
    ) {
        // Student Sign In Screen (Default Landing)
        composable(NavRoutes.STUDENT_SIGN_IN_SCREEN) {
            SignInScreen(
                navController = navController,
                auth = auth,
                onSignInSuccess = {
                    navController.navigate(NavRoutes.STUDENT_HOME_SCREEN) {
                        popUpTo(NavRoutes.STUDENT_SIGN_IN_SCREEN) { inclusive = true }
                        launchSingleTop = true
                    }
                }
            )
        }

        // Optional START_SCREEN landing page (now unused unless explicitly navigated to)
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
                    Text(
                        text = "Welcome to AttachME!",
                        style = MaterialTheme.typography.headlineLarge,
                        modifier = Modifier.padding(bottom = 32.dp)
                    )
                    Button(
                        onClick = { navController.navigate(NavRoutes.STUDENT_SIGN_IN_SCREEN) },
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        Text("Sign In")
                    }
                    Button(
                        onClick = { navController.navigate(NavRoutes.STUDENT_SIGN_UP_SCREEN) },
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        Text("Sign Up")
                    }
                    val context = LocalContext.current
                    if (context is Activity) {
                        TextButton(onClick = { context.finish() }) {
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
                onSignUpSuccess = {
                    navController.navigate(NavRoutes.STUDENT_HOME_SCREEN) {
                        popUpTo(NavRoutes.STUDENT_SIGN_IN_SCREEN) { inclusive = true }
                        launchSingleTop = true
                    }
                }
            )
        }

        composable(NavRoutes.COMPANY_SIGN_UP_SCREEN) {
            CompanySignUpScreen(
                auth = auth,
                navController = navController,
                onSignUpSuccess = {
                    navController.navigate(NavRoutes.COMPANY_HOME) {
                        popUpTo(NavRoutes.STUDENT_SIGN_IN_SCREEN) { inclusive = true }
                        launchSingleTop = true
                    }
                }
            )
        }

        composable(NavRoutes.STUDENT_HOME_SCREEN) {
            StudentHomeScreen()
        }

        composable(NavRoutes.COMPANY_HOME) {
            CompanyHomeScreen(navController = navController)
        }

        composable(NavRoutes.COMPANY_SIGN_IN_SCREEN) {
            CompanySignInScreen(
                auth = auth,
                navController = navController,
                onSignInSuccess = {
                    navController.navigate(NavRoutes.COMPANY_HOME) {
                        popUpTo(NavRoutes.COMPANY_SIGN_IN_SCREEN) { inclusive = true }
                        launchSingleTop = true
                    }
                }
            )
        }
    }
}
