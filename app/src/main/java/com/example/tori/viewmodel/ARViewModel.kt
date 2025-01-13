package com.example.tori.viewmodel

import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import com.google.ar.core.Config
import com.google.ar.core.Frame
import com.google.ar.core.Session
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow

class ARViewModel(application: Application) : AndroidViewModel(application) {

    private var arSession: Session? = null
    private val _depthData = MutableStateFlow<Pair<Float, Float>?>(null)
    val depthData = _depthData.asStateFlow()

    // 세션 초기화
    fun initializeSession() {
        try {
            arSession = Session(getApplication()).apply {
                val config = Config(this).apply {
                    depthMode = Config.DepthMode.AUTOMATIC
                }
                configure(config)
            }
        } catch (e: Exception) {
            Log.e("ARViewModel", "AR Session 초기화 실패: ${e.message}")
        }
    }

    // 특정 프레임의 Depth 데이터 가져오기
    fun getDepthData(x: Int, y: Int) {
        arSession?.let { session ->
            try {
                val frame = session.update()

                // Depth 이미지 가져오기
                val depthImage = frame.acquireDepthImage()

                // Buffer에서 Depth 값을 읽기
                val width = depthImage.width
                val height = depthImage.height
                val buffer = depthImage.planes[0].buffer

                // x, y 좌표의 Depth 값 계산
                val index = y * width + x
                if (index < buffer.capacity()) {
                    val depthValue = buffer.getFloat(index * 4) // 4바이트(float) 단위로 읽기
                    _depthData.value = Pair(x.toFloat(), depthValue)
                } else {
                    Log.e("ARViewModel", "Invalid index for depth data.")
                }

                depthImage.close()
            } catch (e: Exception) {
                Log.e("ARViewModel", "Depth 데이터 가져오기 실패: ${e.message}")
            }
        }
    }
}
