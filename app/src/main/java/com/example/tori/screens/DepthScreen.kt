package com.example.tori.screens

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.tori.viewmodel.ARViewModel
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.onEach

@Composable
fun DepthScreen(viewModel: ARViewModel = viewModel()) {
    // StateFlow 관찰
    val depthData by viewModel.depthData.collectAsState(initial = emptyList())

    // Depth 데이터 가져오기
    LaunchedEffect(Unit) {
        viewModel.initializeSession()
        viewModel.fetchDepthData()
    }

    // UI 출력
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        Text(text = "Depth Data", style = MaterialTheme.typography.headlineSmall)
        Spacer(modifier = Modifier.height(8.dp))

        LazyColumn {
            items(depthData.size) { index ->
                Text(text = "Depth: ${depthData[index]} meters")
                Divider()
            }
        }
    }
}
