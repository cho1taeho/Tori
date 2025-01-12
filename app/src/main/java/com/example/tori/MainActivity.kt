package com.example.tori

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import com.example.tori.screens.DepthScreen
import com.example.tori.ui.theme.TORITheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            TORITheme {
                DepthScreen()
            }
        }
    }
}
