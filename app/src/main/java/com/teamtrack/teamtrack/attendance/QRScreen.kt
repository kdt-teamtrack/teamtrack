package com.teamtrack.teamtrack.attendance

import BarcodeImageAnalyzer
import android.Manifest
import android.content.pm.PackageManager
import android.util.Log
import android.util.Size
import androidx.camera.core.*
import androidx.camera.lifecycle.ProcessCameraProvider
import androidx.camera.view.PreviewView
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalLifecycleOwner
import androidx.compose.ui.unit.dp
import androidx.compose.ui.viewinterop.AndroidView
import androidx.core.content.ContextCompat
import androidx.navigation.NavController
import com.google.accompanist.permissions.ExperimentalPermissionsApi
import com.google.accompanist.permissions.rememberPermissionState

fun isPublicQRCode(result: String): Boolean {
    // QR 코드의 내용이 "PUBLIC_QR_CODE_CONTENT"인지 확인
    return result == "PUBLIC_QR_CODE_CONTENT"
}

@OptIn(ExperimentalPermissionsApi::class)
@Composable
fun QRScreen(navController: NavController) {
    val context = LocalContext.current
    val lifecycleOwner = LocalLifecycleOwner.current
    val cameraProviderFuture = remember { ProcessCameraProvider.getInstance(context) }

    val hasCameraPermission by remember {
        mutableStateOf(
            ContextCompat.checkSelfPermission(
                context,
                Manifest.permission.CAMERA
            ) == PackageManager.PERMISSION_GRANTED
        )
    }

    val cameraPermissionState = rememberPermissionState(Manifest.permission.CAMERA)

    LaunchedEffect(Unit) {
        if (!hasCameraPermission) {
            cameraPermissionState.launchPermissionRequest()
        }
    }

    if (hasCameraPermission) {
        Box(modifier = Modifier.fillMaxSize()) {
            val previewView = remember { PreviewView(context) }
            var camera: Camera? = null
            AndroidView(
                factory = { previewView },
                modifier = Modifier.fillMaxSize()
            ) {
                cameraProviderFuture.addListener({
                    val cameraProvider = cameraProviderFuture.get()
                    val cameraSelector = CameraSelector.DEFAULT_BACK_CAMERA
                    val preview = Preview.Builder().build().also {
                        it.setSurfaceProvider(previewView.surfaceProvider)
                    }

                    val imageAnalysis = ImageAnalysis.Builder()
                        .setTargetResolution(Size(1280, 720))
                        .setBackpressureStrategy(ImageAnalysis.STRATEGY_KEEP_ONLY_LATEST)
                        .build()
                        .also {
                            it.setAnalyzer(ContextCompat.getMainExecutor(context),
                                BarcodeImageAnalyzer { result ->
                                    if (isPublicQRCode(result)) {
                                        Log.d("QRScreen", "Navigating with result: $result")
                                        navController.navigate("resultScreen/${result}")
                                    } else {
                                        Log.d("QRScreen", "Invalid QR Code")
                                    }
                                })
                        }

                    try {
                        cameraProvider.unbindAll()
                        camera = cameraProvider.bindToLifecycle(
                            lifecycleOwner,
                            cameraSelector,
                            preview,
                            imageAnalysis
                        )
                    } catch (exc: Exception) {
                        Log.e("QRScreen", "Use case binding failed", exc)
                    }
                }, ContextCompat.getMainExecutor(context))
            }

            Box(
                modifier = Modifier
                    .size(200.dp)
                    .align(Alignment.Center)
                    .border(BorderStroke(2.dp, Color.Green))
            )
        }
    }
}
