package com.attachmentplatform.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@Composable
fun StudentNavigationRail(
    selectedItem: StudentNavigationItem,
    onItemSelected: (StudentNavigationItem) -> Unit
) {
    NavigationRail(
        modifier = Modifier
            .background(MaterialTheme.colorScheme.surfaceVariant)
            .padding(vertical = 16.dp)
    ) {
        StudentNavigationItem.values().forEach { item ->
            NavigationRailItem(
                icon = { Icon(item.icon, contentDescription = item.title) },
                label = { Text(item.title) },
                selected = selectedItem == item,
                onClick = { onItemSelected(item) }
            )
        }
    }
}
