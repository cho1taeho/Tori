package com.example.tori.viewmodel

import android.app.Application
import android.util.Log
import android.view.SurfaceHolder
import android.view.SurfaceView
import androidx.lifecycle.AndroidViewModel
import com.google.ar.core.Config
import com.google.ar.core.Session

class ARViewModel(application: Application) : AndroidViewModel(application) {

    private var arSession: Session? = null

    // AR 세션 초기화
    fun initializeSession(surfaceView: SurfaceView) {
        try {
            // AR 세션 생성
            arSession = Session(getApplication()).apply {
                val config = Config(this).apply {
                    depthMode = Config.DepthMode.AUTOMATIC
                }
                configure(config)
            }

            // SurfaceView와 연결
            surfaceView.holder.addCallback(object : SurfaceHolder.Callback {
                override fun surfaceCreated(holder: SurfaceHolder) {
                    Log.d("ARViewModel", "Surface가 생성되었습니다. AR 세션과 연결 중...")
                    arSession?.setCameraTextureName(holder.surface.hashCode())
                }

                override fun surfaceChanged(holder: SurfaceHolder, format: Int, width: Int, height: Int) {
                    Log.d("ARViewModel", "Surface가 변경되었습니다. 크기: ${width}x${height}")
                    arSession?.setCameraTextureName(holder.surface.hashCode())
                }

                override fun surfaceDestroyed(holder: SurfaceHolder) {
                    Log.d("ARViewModel", "Surface가 제거되었습니다. AR 세션 일시 중지 중...")
                    arSession?.pause()
                }
            })

            Log.d("ARViewModel", "AR 세션이 성공적으로 초기화되었습니다.")
        } catch (e: Exception) {
            Log.e("ARViewModel", "AR 세션 초기화 실패: ${e.message}")
        }
    }

    // AR 세션 종료
    override fun onCleared() {
        super.onCleared()
        try {
            arSession?.close()
            arSession = null
            Log.d("ARViewModel", "AR 세션이 성공적으로 종료되었습니다.")
        } catch (e: Exception) {
            Log.e("ARViewModel", "AR 세션 종료 실패: ${e.message}")
        }
    }
}
