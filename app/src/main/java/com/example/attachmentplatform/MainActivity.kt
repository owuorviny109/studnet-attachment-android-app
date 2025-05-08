package com.attachmentplatform

import com.attachmentplatform.ui.screens.MainScreen
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import com.google.firebase.auth.FirebaseAuth
import com.attachmentplatform.ui.theme.AttachmentPlatformTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val auth = FirebaseAuth.getInstance()

        setContent {
            AttachmentPlatformTheme {
                MainScreen(auth = auth)
            }
        }
    }
}
