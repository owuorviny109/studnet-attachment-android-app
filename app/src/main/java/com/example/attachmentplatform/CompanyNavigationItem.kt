package com.attachmentplatform.ui.components

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.List
import androidx.compose.material.icons.filled.Message
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Timeline
import androidx.compose.ui.graphics.vector.ImageVector

enum class CompanyNavigationItem(val title: String, val icon: ImageVector) {
    OVERVIEW("Overview", Icons.Filled.Timeline),
    OPPORTUNITIES("Opportunities", Icons.Filled.List),
    APPLICATIONS("Applications", Icons.Filled.Person),
    MESSAGES("Messages", Icons.Filled.Message),
    PROFILE("Profile", Icons.Filled.Person)
}