package com.attachmentplatform.ui.screens.student

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@Composable
fun StudentMessagesContent() {
    Column(modifier = Modifier.padding(16.dp)) {
        Text("Messages", style = MaterialTheme.typography.headlineSmall)
        Spacer(modifier = Modifier.height(16.dp))
        Text("Here you’ll see messages from companies or system notifications.")
    }
}
