package com.example.tori

import android.Manifest
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.result.contract.ActivityResultContracts
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.tori.screens.DepthScreen
import com.example.tori.ui.theme.TORITheme
import com.example.tori.viewmodel.ARViewModel

class MainActivity : ComponentActivity() {

    // 권한 요청 런처
    private val cameraPermissionLauncher =
        registerForActivityResult(ActivityResultContracts.RequestPermission()) { isGranted ->
            if (isGranted) {
                android.util.Log.d("MainActivity", "카메라 권한이 허용되었습니다.")
            } else {
                android.util.Log.e("MainActivity", "카메라 권한이 거부되었습니다.")
            }
        }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // 카메라 권한 요청
        cameraPermissionLauncher.launch(Manifest.permission.CAMERA)

        // Compose UI 설정
        setContent {
            TORITheme {
                val viewModel: ARViewModel = viewModel()
                DepthScreen(viewModel = viewModel)
            }
        }
    }
}
