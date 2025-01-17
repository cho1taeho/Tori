package com.example.tori

import android.Manifest
import android.content.pm.PackageManager
import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.text.BasicText
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import com.example.tori.screens.DepthScreen
import com.example.tori.ui.theme.TORITheme
import com.example.tori.viewmodel.ARViewModel
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.core.content.ContextCompat
import com.example.tori.screens.PermissionDeniedScreen

class MainActivity : ComponentActivity() {
    // 권한 상태를 추적하기 위한 변수
    private var isPermissionGranted by mutableStateOf(false)

    private val permissionLauncher =
        registerForActivityResult(ActivityResultContracts.RequestPermission()) { isGranted ->
            isPermissionGranted = isGranted
            if (isGranted) {
                Log.d("MainActivity", "카메라 권한이 허용되었습니다.")
            } else {
                Log.e("MainActivity", "카메라 권한이 거부되었습니다.")
            }
        }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // 권한 확인 및 요청
        checkCameraPermission()

        setContent {
            TORITheme {
                if (isPermissionGranted) {
                    val viewModel: ARViewModel = viewModel()
                    DepthScreen(viewModel = viewModel)
                } else {
                    PermissionDeniedScreen()
                }
            }
        }
    }

    private fun checkCameraPermission() {
        // 권한이 이미 허용되었는지 확인
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.CAMERA)
            == PackageManager.PERMISSION_GRANTED) {
            isPermissionGranted = true
            Log.d("MainActivity", "카메라 권한이 이미 허용되었습니다.")
        } else {
            // 권한 요청
            permissionLauncher.launch(Manifest.permission.CAMERA)
        }
    }
}
