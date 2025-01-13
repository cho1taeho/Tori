package com.example.tori.screens

import android.content.Context
import android.view.SurfaceView
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.compose.ui.viewinterop.AndroidView
import com.example.tori.viewmodel.ARViewModel

@Composable
fun DepthScreen(
    viewModel: ARViewModel
) {
    val context = LocalContext.current

    // 카메라 프리뷰를 위한 SurfaceView
    Box(modifier = Modifier.fillMaxSize()) {
        CameraPreview(context, viewModel)

        // Depth 데이터 표시
        Text(
            text = "화면을 터치하세요",
            style = MaterialTheme.typography.headlineSmall,
            modifier = Modifier.padding(16.dp)
        )
    }
}

@Composable
fun CameraPreview(context: Context, viewModel: ARViewModel) {
    AndroidView(
        factory = {
            SurfaceView(context).apply {
                viewModel.initializeSession(this) // ARCore 세션 초기화 및 연결
            }
        },
        modifier = Modifier.fillMaxSize()
    )
}
