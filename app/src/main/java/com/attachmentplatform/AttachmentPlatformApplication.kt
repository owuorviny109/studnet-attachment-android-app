package com.attachmentplatform

import android.app.Application
import dagger.hilt.android.HiltAndroidApp

@HiltAndroidApp
class AttachmentPlatformApplication : Application() {
    override fun onCreate() {
        super.onCreate()
        // Initialize any application-wide logic if needed
    }
}