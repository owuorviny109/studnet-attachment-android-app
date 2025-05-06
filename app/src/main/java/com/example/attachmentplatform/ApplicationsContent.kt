package com.attachmentplatform.ui.screens.applications

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.tooling.preview.Preview
import com.example.attachmentplatform.ui.theme.BluePrimary
import com.example.attachmentplatform.ui.theme.TextMuted

@Composable
fun ApplicationsContent() {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        // Title
        Text(
            text = "Applications",
            style = MaterialTheme.typography.headlineLarge,
            modifier = Modifier.padding(bottom = 16.dp)
        )

        // Application Cards
        ApplicationCard(applicationName = "Application 1")
        ApplicationCard(applicationName = "Application 2")
        ApplicationCard(applicationName = "Application 3")
    }
}

@Composable
fun ApplicationCard(applicationName: String) {
    Card(
        shape = RoundedCornerShape(16.dp),
        elevation = CardDefaults.elevatedCardElevation(defaultElevation = 4.dp),
        modifier = Modifier
            .fillMaxWidth()
            .padding(bottom = 16.dp)
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp)
        ) {
            Text(
                text = applicationName,
                style = MaterialTheme.typography.bodyLarge,
                color = TextMuted
            )

            Spacer(modifier = Modifier.height(8.dp))

            Button(
                onClick = { /* Handle application click */ },
                colors = ButtonDefaults.buttonColors(containerColor = BluePrimary),
                shape = RoundedCornerShape(8.dp)
            ) {
                Text(text = "View Details", color = Color.White)
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun PreviewApplicationsContent() {
    ApplicationsContent()
}
