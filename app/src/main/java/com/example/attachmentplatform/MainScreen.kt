package com.attachmentplatform.ui.screens

import com.attachmentplatform.ui.screens.student.StudentDashboardScreen
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
import com.attachmentplatform.ui.screens.* // Assuming your screen composables are in this package or sub-packages
import com.google.firebase.auth.FirebaseAuth

@Composable
fun MainScreen(auth: FirebaseAuth) {
    val navController = rememberNavController()

    NavHost(
        navController = navController,
        startDestination = NavRoutes.STUDENT_SIGN_IN_SCREEN // Set your desired starting destination
    ) {
        // Student Sign In Screen
        composable(NavRoutes.STUDENT_SIGN_IN_SCREEN) {
            // Use the appropriate screen composable for student sign-in
            // Based on your code snippets, you have both SignInScreen and AuthScreen
            // You should choose the one you intend to use for student sign-in.
            // If SignInScreen is specifically for students, use that.
            // If AuthScreen handles both student and company sign-in/up, use that.
            // Let's assume SignInScreen is your intended student sign-in for now.
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
            // If AuthScreen is the intended student sign-in, use this instead:
            /*
            AuthScreen(auth = auth, navController = navController) // You'll need to adapt AuthScreen to handle success navigation
            */
        }
        // Company Sign In Screen
        composable(NavRoutes.COMPANY_SIGN_IN_SCREEN) {
            CompanySignInScreen(
                auth = auth,
                navController = navController,
                onSignInSuccess = {
                    navController.navigate(NavRoutes.COMPANY_HOME_SCREEN) {
                        // Check if this popUpTo route is correct based on your flow
                        popUpTo(NavRoutes.COMPANY_SIGN_IN_SCREEN) { inclusive = true }
                        launchSingleTop = true
                    }
                }
            )
        }

        // Optional START_SCREEN landing page (if you still want this as an option)
        // If you don't need this screen, you can remove this composable block.
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

        // AUTH_SCREEN (if you have a generic auth screen for choosing role or similar)
        // If SignInScreen/SignUpScreen are the primary entry points, you might not need this route.
        composable(NavRoutes.AUTH_SCREEN) {
            AuthScreen(auth = auth, navController = navController)
        }

        // Student Sign Up Screen
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

        // Company Sign Up Screen
        composable(NavRoutes.COMPANY_SIGN_UP_SCREEN) {
            CompanySignUpScreen(
                auth = auth,
                navController = navController,
                onSignUpSuccess = {
                    navController.navigate(NavRoutes.COMPANY_HOME_SCREEN) {
                        // CORRECTED: Pop up to the sign-up screen itself
                        popUpTo(NavRoutes.COMPANY_SIGN_IN_SCREEN) { inclusive = true }
                        launchSingleTop = true
                    }
                }
            )
        }

        // Student Home Screen
        composable(NavRoutes.STUDENT_HOME_SCREEN) {
            StudentDashboardScreen(navController=navController)// Ensure StudentHomeScreen is correctly implemented
        }

        // Company Home Screen
        composable(NavRoutes.COMPANY_HOME_SCREEN) {
            CompanyHomeScreen(navController = navController) // Ensure CompanyHomeScreen is correctly implemented
        }

    }
}

// You should define your NavRoutes object here or in a separate NavRoutes.kt file
object NavRoutes {
    const val START_SCREEN = "start_screen"
    const val AUTH_SCREEN = "auth_screen"
    const val STUDENT_SIGN_IN_SCREEN = "student_sign_in_screen"
    const val STUDENT_SIGN_UP_SCREEN = "student_sign_up_screen"
    const val COMPANY_SIGN_IN_SCREEN = "company_sign_in_screen"
    const val COMPANY_SIGN_UP_SCREEN = "company_sign_up_screen"
    const val STUDENT_HOME_SCREEN = "student_home_screen"
    const val COMPANY_HOME_SCREEN = "company_home_screen"
    // Add other routes as needed
}