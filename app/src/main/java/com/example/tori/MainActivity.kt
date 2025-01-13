package com.example.tori

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.tori.screens.DepthScreen
import com.example.tori.ui.theme.TORITheme
import com.example.tori.viewmodel.ARViewModel

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            TORITheme {
                val viewModel: ARViewModel = viewModel()
                DepthScreen(viewModel = viewModel)
            }
        }
    }
}
