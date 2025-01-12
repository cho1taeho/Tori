package com.example.tori.viewmodel

import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import com.google.ar.core.Config
import com.google.ar.core.Frame
import com.google.ar.core.Session

class ARViewModel(application: Application) : AndroidViewModel(application) {
    private var arSession: Session? = null
    private val _depthData = MutableStateFlow<List<Float>>(emptyList())
    val depthData = _depthData.asStateFlow()

    // ARCore 세션 초기화
    fun initializeSession() {
        try {
            arSession = Session(getApplication()).apply {
                configureSession(this)
            }
        } catch (e: Exception) {
            Log.e("ARViewModel", "AR Session 초기화 실패: ${e.message}")
        }
    }

    // ARCore 세션 구성
    private fun configureSession(session: Session) {
        val config = Config(session)

        // Depth 모드 지원 확인
        if (session.isDepthModeSupported(Config.DepthMode.AUTOMATIC)) {
            config.depthMode = Config.DepthMode.AUTOMATIC
        } else {
            Log.e("ARViewModel", "Depth 모드가 지원되지 않습니다.")
        }

        session.configure(config)
    }

    // Depth 데이터 가져오기
    fun fetchDepthData() {
        arSession?.let { session ->
            try {
                val frame: Frame = session.update()

                // Depth 이미지 가져오기
                val depthImage = frame.acquireDepthImage()
                val buffer = depthImage.planes[0].buffer
                val depthList = mutableListOf<Float>()

                while (buffer.hasRemaining()) {
                    depthList.add(buffer.float)
                }
                depthImage.close()

                // StateFlow에 데이터 업데이트
                _depthData.value = depthList
            } catch (e: Exception) {
                Log.e("ARViewModel", "Depth 데이터 가져오기 실패: ${e.message}")
            }
        }
    }
}
