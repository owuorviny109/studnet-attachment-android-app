package com.attachmentplatform.ui.screens

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.attachmentplatform.NavRoutes

@Composable
fun ProfileContent(navController: NavHostController) {
    Column(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text("Profile Content")
        Spacer(modifier = Modifier.height(16.dp))
        OutlinedButton(
            onClick = {
                navController.navigate(NavRoutes.STUDENT_SIGN_IN_SCREEN) {
                    popUpTo(NavRoutes.COMPANY_HOME_SCREEN) { inclusive = true }
                }
            },
            modifier = Modifier.fillMaxWidth(0.8f)
        ) {
            Text("Sign Out")
        }
    }
}
