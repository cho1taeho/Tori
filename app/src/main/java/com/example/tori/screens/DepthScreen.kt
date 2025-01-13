package com.example.tori.screens

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.tori.viewmodel.ARViewModel

@Composable
fun DepthScreen(
    viewModel: ARViewModel = viewModel()
) {
    val depthData by viewModel.depthData.collectAsState()

    Box(
        modifier = Modifier
            .fillMaxSize()
            .pointerInput(Unit) {
                detectTapGestures { offset ->
                    // 화면 클릭 시 Depth 데이터 요청
                    viewModel.getDepthData(offset.x.toInt(), offset.y.toInt())
                }
            }
    ) {
        Text(
            text = depthData?.let { "X: ${it.first}, Depth: ${it.second}" } ?: "화면을 터치하세요",
            modifier = Modifier.padding(16.dp)
        )
    }
}
