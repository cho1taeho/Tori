package com.example.tori.viewmodel

import android.app.Application
import android.util.Log
import android.view.Surface
import android.view.SurfaceHolder
import android.view.SurfaceView
import androidx.lifecycle.AndroidViewModel
import com.google.ar.core.Config
import com.google.ar.core.Session

class ARViewModel(application: Application) : AndroidViewModel(application) {

    private var arSession: Session? = null

    // ARCore 세션 초기화 및 Surface 연결
    fun initializeSession(surfaceView: SurfaceView) {
        try {
            arSession = Session(getApplication()).apply {
                val config = Config(this).apply {
                    depthMode = Config.DepthMode.AUTOMATIC
                }
                configure(config)
            }

            surfaceView.holder.addCallback(object : android.view.SurfaceHolder.Callback {
                override fun surfaceCreated(holder: SurfaceHolder) {
                    arSession?.setCameraTextureName(holder.surface.hashCode())
                }

                override fun surfaceChanged(
                    holder: SurfaceHolder,
                    format: Int,
                    width: Int,
                    height: Int
                ) {
                    arSession?.setCameraTextureName(holder.surface.hashCode())
                }

                override fun surfaceDestroyed(holder: SurfaceHolder) {
                    arSession?.pause()
                }
            })
        } catch (e: Exception) {
            Log.e("ARViewModel", "ARCore 세션 초기화 실패: ${e.message}")
        }
    }
}
