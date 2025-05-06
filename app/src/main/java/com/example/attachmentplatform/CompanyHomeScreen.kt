package com.attachmentplatform.ui.screens

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.attachmentplatform.ui.components.CompanyNavigationItem
import com.attachmentplatform.ui.components.CustomNavigationRail
import com.attachmentplatform.ui.components.DashboardTopBar
import com.attachmentplatform.ui.screens.opportunities.OpportunitiesContent
import com.attachmentplatform.ui.screens.applications.ApplicationsContent
import com.attachmentplatform.ui.screens.OverviewContent
import com.attachmentplatform.ui.screens.MessagesContent
import com.attachmentplatform.ui.screens.ProfileContent



@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CompanyHomeScreen(navController: NavHostController) {
    var selectedItem by remember { mutableStateOf(CompanyNavigationItem.OVERVIEW) }

    Scaffold(
        topBar = { DashboardTopBar() }
    ) { paddingValues ->
        Row(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
        ) {
            CustomNavigationRail(
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
                    CompanyNavigationItem.OVERVIEW -> OverviewContent()
                    CompanyNavigationItem.OPPORTUNITIES -> OpportunitiesContent()
                    CompanyNavigationItem.APPLICATIONS -> ApplicationsContent()
                    CompanyNavigationItem.MESSAGES -> MessagesContent()
                    CompanyNavigationItem.PROFILE -> ProfileContent(navController)
                }
            }
        }
    }
}

