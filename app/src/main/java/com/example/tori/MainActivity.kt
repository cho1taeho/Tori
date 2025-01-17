package com.example.tori

import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.tori.screens.DepthScreen
import com.example.tori.ui.theme.TORITheme
import com.example.tori.viewmodel.ARViewModel
import com.google.ar.core.ArCoreApk

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            TORITheme {
                val viewModel: ARViewModel = viewModel()
                DepthScreen(viewModel = viewModel)
            }
        }

        try {
            val availability = ArCoreApk.getInstance().checkAvailability(this)
            if (availability.isSupported) {
                Log.d("MainActivity", "ARCore가 지원됩니다.")
            } else {
                Log.e("MainActivity", "ARCore가 지원되지 않습니다.")
            }
        } catch (e: Exception) {
            Log.e("MainActivity", "ARCore 지원 확인 중 오류 발생: ${e.message}")
        }
    }
}
