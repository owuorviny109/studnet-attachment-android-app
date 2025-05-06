package com.attachmentplatform.ui.screens.student

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController

@Composable
fun StudentProfileContent(navController: NavHostController) {
    Column(modifier = Modifier.padding(16.dp)) {
        Text("Your Profile", style = MaterialTheme.typography.headlineSmall)
        Spacer(modifier = Modifier.height(16.dp))
        Text("Profile info, resume, and settings will be shown here.")
    }
}
