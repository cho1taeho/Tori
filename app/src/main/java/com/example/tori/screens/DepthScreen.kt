package com.example.tori.screens

import android.content.Context
import android.view.SurfaceView
import androidx.compose.foundation.layout.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.viewinterop.AndroidView
import com.example.tori.viewmodel.ARViewModel

@Composable
fun DepthScreen(viewModel: ARViewModel) {
    val context = LocalContext.current

    Box(modifier = Modifier.fillMaxSize()) {
        CameraPreview(context, viewModel)
    }
}

@Composable
fun CameraPreview(context: Context, viewModel: ARViewModel) {
    AndroidView(
        factory = {
            SurfaceView(context).apply {
                viewModel.initializeSession(this)
            }
        },
        modifier = Modifier.fillMaxSize()
    )
}
