package com.attachmentplatform

import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.attachmentplatform.ui.screens.AuthScreen
import com.attachmentplatform.ui.screens.CompanyHomeScreen
import com.attachmentplatform.ui.screens.CompanySignUpScreen
import com.attachmentplatform.ui.screens.StudentHomeScreen
import com.attachmentplatform.ui.screens.StudentSignUpScreen
import com.google.firebase.auth.FirebaseAuth

@Composable
fun MainScreen(auth: FirebaseAuth) {
    val navController = rememberNavController()

    NavHost(navController = navController, startDestination = NavRoutes.AUTH_SCREEN) {

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
    }
}