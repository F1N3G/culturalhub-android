package com.g.culturalhub

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import com.g.culturalhub.ui.EventListScreen
import com.g.culturalhub.ui.theme.CulturalHubTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            CulturalHubTheme {
                EventListScreen()
            }
        }
    }
}