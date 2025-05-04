package com.attachmentplatform.ui.screens

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.graphics.Color
import androidx.compose.material3.Scaffold
import androidx.compose.material3.ExperimentalMaterial3Api // Import the experimental annotation

@OptIn(ExperimentalMaterial3Api::class) // Apply the annotation to the function
@Composable
fun StudentHomeScreen() {
    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        text = "AttachME",
                        fontWeight = FontWeight.Bold,
                        fontSize = 20.sp,
                        color = MaterialTheme.colorScheme.primary
                    )
                },
                colors = TopAppBarDefaults.smallTopAppBarColors(
                    containerColor = MaterialTheme.colorScheme.surfaceVariant
                )
            )
        }
    ) { paddingValues ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .padding(24.dp),
            contentAlignment = Alignment.Center
        ) {
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                Text(
                    text = "Welcome, Student!",
                    style = MaterialTheme.typography.headlineSmall.copy(
                        fontWeight = FontWeight.SemiBold,
                        fontSize = 24.sp
                    ),
                    textAlign = TextAlign.Center
                )

                Text(
                    text = "You're now signed in to AttachME.\nExplore internship opportunities tailored just for you.",
                    style = MaterialTheme.typography.bodyMedium.copy(color = Color.Gray),
                    textAlign = TextAlign.Center
                )

                Button(
                    onClick = { /* TODO: Implement browse internships or profile */ },
                    modifier = Modifier.fillMaxWidth(0.8f)
                ) {
                    Text("Browse Internships")
                }

                OutlinedButton(
                    onClick = { /* TODO: Implement sign out or navigate to company sign in */ },
                    modifier = Modifier.fillMaxWidth(0.8f)
                ) {
                    Text("Sign Out")
                }
            }
        }
    }
}