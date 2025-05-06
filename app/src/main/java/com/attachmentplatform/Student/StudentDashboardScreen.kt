package com.attachmentplatform.ui.screens

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
// Change NavController to NavHostController
import androidx.navigation.NavHostController // Import NavHostController
import com.attachmentplatform.ui.components.*
import com.attachmentplatform.ui.screens.student.*

// ... (NavRoutes object remains the same)

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun StudentDashboardScreen(navController: NavHostController) { // Changed to NavHostController
    var selectedItem by remember { mutableStateOf(StudentNavigationItem.OVERVIEW) }

    Scaffold(
        topBar = { DashboardTopBar() }
    ) { paddingValues ->
        Row(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
        ) {
            StudentNavigationRail(
                selectedItem = selectedItem,
                onItemSelected = { selectedItem = it }
            )

            Spacer(modifier = Modifier.width(16.dp))

            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(16.dp)
            ) {
                when (selectedItem) {
                    StudentNavigationItem.OVERVIEW -> StudentOverviewContent()
                    StudentNavigationItem.OPPORTUNITIES -> StudentOpportunitiesContent()
                    StudentNavigationItem.APPLICATIONS -> StudentApplicationsContent()
                    StudentNavigationItem.MESSAGES -> StudentMessagesContent()
                    // Pass the navController (which is now NavHostController)
                    StudentNavigationItem.PROFILE -> StudentProfileContent(navController)
                }
            }
        }
    }
}