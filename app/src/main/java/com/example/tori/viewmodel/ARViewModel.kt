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
            arSession = Session(getApplication()).apply {
                val config = Config(this).apply {
                    depthMode = Config.DepthMode.AUTOMATIC
                }
                configure(config)
            }

            surfaceView.holder.addCallback(object : SurfaceHolder.Callback {
                override fun surfaceCreated(holder: SurfaceHolder) {
                    Log.d("ARViewModel", "Surface가 생성되었습니다. AR 세션과 연결 중...")
                    try {
                        arSession?.setCameraTextureName(holder.surface.hashCode())
                        Log.d("ARViewModel", "카메라 텍스처가 성공적으로 연결되었습니다.")
                    } catch (e: Exception) {
                        Log.e("ARViewModel", "카메라 텍스처 연결 실패: ${e.message}")
                    }
                }

                override fun surfaceChanged(holder: SurfaceHolder, format: Int, width: Int, height: Int) {
                    Log.d("ARViewModel", "Surface가 변경되었습니다. 크기: ${width}x${height}")
                    try {
                        arSession?.setCameraTextureName(holder.surface.hashCode())
                        Log.d("ARViewModel", "Surface 변경 후 카메라 텍스처 재연결 성공.")
                    } catch (e: Exception) {
                        Log.e("ARViewModel", "Surface 변경 후 텍스처 연결 실패: ${e.message}")
                    }
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

    // 프레임 업데이트
    fun updateFrame() {
        try {
            val frame = arSession?.update()
            if (frame != null) {
                Log.d("ARViewModel", "AR 세션 프레임 업데이트 성공.")
            } else {
                Log.e("ARViewModel", "AR 세션에서 프레임 데이터를 가져오지 못했습니다.")
            }
        } catch (e: Exception) {
            Log.e("ARViewModel", "AR 세션 프레임 업데이트 실패: ${e.message}")
        }
    }
}
