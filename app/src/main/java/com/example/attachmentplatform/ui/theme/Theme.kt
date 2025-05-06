package com.attachmentplatform.ui.theme

import androidx.compose.ui.graphics.Color

import android.app.Activity
import android.os.Build
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.dynamicDarkColorScheme
import androidx.compose.material3.dynamicLightColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalContext

// Import the colors
import com.example.attachmentplatform.ui.theme.Purple80
import com.example.attachmentplatform.ui.theme.PurpleGrey80
import com.example.attachmentplatform.ui.theme.Pink80
import com.example.attachmentplatform.ui.theme.BluePrimary
import com.example.attachmentplatform.ui.theme.BlueSecondary
import com.example.attachmentplatform.ui.theme.BlueAccent
import com.example.attachmentplatform.ui.theme.BackgroundLight
import com.example.attachmentplatform.ui.theme.SurfaceLight
import com.example.attachmentplatform.ui.theme.TextDark
import com.example.attachmentplatform.ui.theme.TextMuted

private val DarkColorScheme = darkColorScheme(
    primary = Purple80,
    secondary = PurpleGrey80,
    tertiary = Pink80
)

private val LightColorScheme = lightColorScheme(
    primary = BluePrimary,
    secondary = BlueSecondary,
    tertiary = BlueAccent,
    background = BackgroundLight,
    surface = SurfaceLight,
    onPrimary = Color.White,
    onSecondary = Color.White,
    onTertiary = Color.White,
    onBackground = TextDark,
    onSurface = TextDark,
)

@Composable
fun AttachmentPlatformTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    // Dynamic color is available on Android 12+
    dynamicColor: Boolean = true,
    content: @Composable () -> Unit
) {
    val colorScheme = when {
        dynamicColor && Build.VERSION.SDK_INT >= Build.VERSION_CODES.S -> {
            val context = LocalContext.current
            if (darkTheme) dynamicDarkColorScheme(context) else dynamicLightColorScheme(context)
        }

        darkTheme -> DarkColorScheme
        else -> LightColorScheme
    }

    MaterialTheme(
        colorScheme = colorScheme,
        typography = Typography,
        content = content
    )
}
