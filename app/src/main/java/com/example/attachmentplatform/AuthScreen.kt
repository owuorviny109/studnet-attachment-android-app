package com.attachmentplatform.ui.screens
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.google.firebase.auth.FirebaseAuth
import com.attachmentplatform.NavRoutes // Ensure correct import

enum class UserType { STUDENT, COMPANY }

@Composable
fun AuthScreen(auth: FirebaseAuth, navController: NavHostController) {
    var isSigningIn by remember { mutableStateOf(true) }
    var selectedUserType by remember { mutableStateOf(UserType.STUDENT) }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(32.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Text("Welcome to AttachME", style = MaterialTheme.typography.headlineMedium)

        Spacer(modifier = Modifier.height(20.dp))

        Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
            Button(onClick = { isSigningIn = true }) { Text("Sign In") }
            Button(onClick = { isSigningIn = false }) { Text("Sign Up") }
        }

        Spacer(modifier = Modifier.height(20.dp))

        if (!isSigningIn) {
            Text("I am signing up as a:")
            Row(verticalAlignment = Alignment.CenterVertically) {
                RadioButton(
                    selected = selectedUserType == UserType.STUDENT,
                    onClick = { selectedUserType = UserType.STUDENT }
                )
                Text("Student")

                RadioButton(
                    selected = selectedUserType == UserType.COMPANY,
                    onClick = { selectedUserType = UserType.COMPANY }
                )
                Text("Company")
            }
        }

        Spacer(modifier = Modifier.height(20.dp))

        Button(onClick = {
            if (isSigningIn) {
                navController.navigate(NavRoutes.SIGN_IN_SCREEN)
            } else {
                val route = when (selectedUserType) {
                    UserType.STUDENT -> NavRoutes.STUDENT_SIGN_UP_SCREEN
                    UserType.COMPANY -> NavRoutes.COMPANY_SIGN_UP_SCREEN
                }
                navController.navigate(route)
            }
        }) {
            Text(if (isSigningIn) "Continue to Sign In" else "Continue to Sign Up")
        }
    }
}
