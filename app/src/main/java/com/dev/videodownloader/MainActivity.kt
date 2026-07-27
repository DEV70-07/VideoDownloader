package com.dev.videodownloader

import android.os.Build
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.SystemBarStyle
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import com.dev.videodownloader.ui.theme.VideoDownloaderTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge(
            SystemBarStyle.dark(0x0),
            SystemBarStyle.dark(0x0)
        )
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
            window.isNavigationBarContrastEnforced = false
        }

        setContent {
            VideoDownloaderTheme (darkTheme = true) {
                MainScreen()
            }
        }
    }
}

@Composable
fun AddText() {
    Text("Olá?")
}