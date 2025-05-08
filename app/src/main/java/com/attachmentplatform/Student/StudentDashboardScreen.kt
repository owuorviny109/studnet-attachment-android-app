@file:OptIn(ExperimentalMaterial3Api::class)

package com.attachmentplatform.ui.screens

import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Email
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.List
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Work
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController

// Enum defining the navigation items
enum class StudentNavigationItem(
    val title: String,
    val icon: ImageVector
) {
    OVERVIEW("Overview", Icons.Default.Home),
    OPPORTUNITIES("Opportunities", Icons.Default.Work),
    APPLICATIONS("Applications", Icons.Default.List),
    MESSAGES("Messages", Icons.Default.Email),
    PROFILE("Profile", Icons.Default.Person)
}

@Composable
fun StudentDashboardScreen(navController: NavHostController) {
    var selectedItem by remember {
        mutableStateOf(StudentNavigationItem.OVERVIEW)
    }

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
                    StudentNavigationItem.PROFILE -> StudentProfileContent(navController)
                }
            }
        }
    }
}

@Composable
fun StudentNavigationRail(
    selectedItem: StudentNavigationItem,
    onItemSelected: (StudentNavigationItem) -> Unit
) {
    NavigationRail {
        StudentNavigationItem.values().forEach { item ->
            NavigationRailItem(
                selected = selectedItem == item,
                onClick = { onItemSelected(item) },
                icon = { Icon(item.icon, contentDescription = item.title) },
                label = { Text(item.title) },
                alwaysShowLabel = true
            )
        }
    }
}

@Composable
fun DashboardTopBar() {
    CenterAlignedTopAppBar(
        title = { Text("Student Dashboard") },
        colors = TopAppBarDefaults.centerAlignedTopAppBarColors()
    )
}

@Composable
fun StudentOverviewContent() {
    Column(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text("Overview Content", style = MaterialTheme.typography.headlineMedium)
    }
}

@Composable
fun StudentOpportunitiesContent() {
    Column(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text("Opportunities Content", style = MaterialTheme.typography.headlineMedium)
    }
}

@Composable
fun StudentApplicationsContent() {
    Column(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text("Applications Content", style = MaterialTheme.typography.headlineMedium)
    }
}

@Composable
fun StudentMessagesContent() {
    Column(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text("Messages Content", style = MaterialTheme.typography.headlineMedium)
    }
}

@Composable
fun StudentProfileContent(navController: NavHostController) {
    Column(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text("Profile Content", style = MaterialTheme.typography.headlineMedium)
        Button(onClick = { /* Handle navigation */ }) {
            Text("Edit Profile")
        }
    }
}