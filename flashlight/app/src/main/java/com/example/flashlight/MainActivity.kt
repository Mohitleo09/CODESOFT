package com.example.flashlight

import android.content.Context
import android.content.pm.PackageManager
import android.hardware.camera2.CameraAccessException
import android.hardware.camera2.CameraManager
import android.os.Build
import android.os.Bundle
import android.widget.CompoundButton
import androidx.activity.ComponentActivity
import androidx.annotation.RequiresApi

class MainActivity : ComponentActivity() {
    private lateinit var toggleButton: CompoundButton
    private var isFlashOn: Boolean = false
    private var cameraManager: CameraManager? = null
    private var cameraId: String? = null

    @RequiresApi(Build.VERSION_CODES.LOLLIPOP)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        toggleButton = findViewById(R.id.toggleButton)
        cameraManager = getSystemService(Context.CAMERA_SERVICE) as CameraManager?

        // Check if the device has a flash
        if (!hasFlash()) {
            toggleButton.isEnabled = false
            return
        }

        // Get the camera ID
        cameraId = cameraManager?.cameraIdList?.get(0)

        toggleButton.setOnCheckedChangeListener { _, isChecked ->
            if (isChecked) {
                turnOnFlashlight()
            } else {
                turnOffFlashlight()
            }
        }
    }

    @RequiresApi(Build.VERSION_CODES.M)
    private fun hasFlash(): Boolean {
        return packageManager.hasSystemFeature(PackageManager.FEATURE_CAMERA_FLASH)
    }

    @RequiresApi(Build.VERSION_CODES.M)
    private fun turnOnFlashlight() {
        try {
            if (cameraId != null) {
                cameraManager?.setTorchMode(cameraId!!, true)
                isFlashOn = true
            }
        } catch (e: CameraAccessException) {
            e.printStackTrace()
        }
    }

    @RequiresApi(Build.VERSION_CODES.M)
    private fun turnOffFlashlight() {
        try {
            if (cameraId != null) {
                cameraManager?.setTorchMode(cameraId!!, false)
                isFlashOn = false
            }
        } catch (e: CameraAccessException) {
            e.printStackTrace()
        }
    }

    override fun onStop() {
        super.onStop()
        if (isFlashOn) {
            turnOffFlashlight()
        }
    }
}