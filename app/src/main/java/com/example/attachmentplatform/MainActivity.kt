package com.attachmentplatform

import com.attachmentplatform.ui.screens.MainScreen
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import com.google.firebase.auth.FirebaseAuth
import com.attachmentplatform.ui.theme.AttachmentPlatformTheme // Assuming you have a theme defined

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // Get an instance of FirebaseAuth here
        val auth = FirebaseAuth.getInstance()

        setContent {
            AttachmentPlatformTheme { // Apply your app's theme if you have one
                // Call your MainScreen composable here
                MainScreen(auth = auth)
            }
        }
    }
}
