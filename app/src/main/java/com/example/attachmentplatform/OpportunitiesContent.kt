package com.attachmentplatform.ui.screens.opportunities

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.attachmentplatform.viewmodel.OpportunityViewModel
import com.attachmentplatform.viewmodel.UiState

@Composable
fun OpportunitiesContent(
    viewModel: OpportunityViewModel = hiltViewModel()
) {
    var showForm by remember { mutableStateOf(false) }

    // 🔁 Load opportunities when this Composable is first shown
    LaunchedEffect(Unit) {
        viewModel.loadOpportunities()
    }

    val state by viewModel.creationState.collectAsState()

    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        item {
            Text(
                text = if (showForm) "Post a New Opportunity for a Student" else "Create an Opportunity",
                style = MaterialTheme.typography.headlineSmall.copy(fontWeight = FontWeight.SemiBold),
                modifier = Modifier.padding(vertical = 24.dp)
            )
        }

        // 🔴 Optional: Display Firestore errors if they happen
        item {
            if (state is UiState.Error) {
                Text(
                    text = "Error: ${(state as UiState.Error).message}",
                    color = MaterialTheme.colorScheme.error,
                    style = MaterialTheme.typography.bodyMedium,
                    modifier = Modifier.padding(bottom = 16.dp)
                )
            }
        }

        item {
            if (!showForm) {
                Button(
                    onClick = { showForm = true },
                    colors = ButtonDefaults.buttonColors(containerColor = MaterialTheme.colorScheme.primary)
                ) {
                    Text("Create Opportunity")
                }
            }
        }

        item {
            AnimatedVisibility(
                visible = showForm,
                enter = fadeIn(),
                exit = fadeOut()
            ) {
                Card(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 16.dp),
                    shape = MaterialTheme.shapes.medium,
                    elevation = CardDefaults.cardElevation(defaultElevation = 6.dp)
                ) {
                    Column(modifier = Modifier.padding(16.dp)) {
                        CreateOpportunityForm(viewModel = viewModel)
                    }
                }
            }
        }
    }
}
