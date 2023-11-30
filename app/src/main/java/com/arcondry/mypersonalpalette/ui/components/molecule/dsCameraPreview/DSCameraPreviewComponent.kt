package com.arcondry.mypersonalpalette.ui.components.molecule.dsCameraPreview

import android.content.Context
import android.graphics.Bitmap
import android.graphics.Color
import android.util.Log
import android.util.Size
import android.view.ViewGroup.LayoutParams.MATCH_PARENT
import android.widget.LinearLayout
import androidx.camera.core.CameraSelector
import androidx.camera.core.ExperimentalGetImage
import androidx.camera.core.ImageAnalysis
import androidx.camera.core.ImageCapture
import androidx.camera.core.ImageCaptureException
import androidx.camera.core.ImageProxy
import androidx.camera.core.resolutionselector.ResolutionSelector
import androidx.camera.core.resolutionselector.ResolutionStrategy
import androidx.camera.core.resolutionselector.ResolutionStrategy.FALLBACK_RULE_CLOSEST_HIGHER_THEN_LOWER
import androidx.camera.lifecycle.ProcessCameraProvider
import androidx.camera.view.LifecycleCameraController
import androidx.camera.view.PreviewView
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalLifecycleOwner
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.viewinterop.AndroidView
import androidx.core.content.ContextCompat
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.LifecycleOwner
import com.arcondry.mypersonalpalette.core.utils.rotateBitmap
import com.arcondry.mypersonalpalette.ui.components.atom.DSTargetPointerComponent
import com.arcondry.mypersonalpalette.ui.components.organism.dsPickerComponent.DSPickerComponentController
import java.util.concurrent.Executor
import androidx.camera.core.Preview as CameraPreview

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DSCameraPreviewComponent(
    controller: DSPickerComponentController
) {
    val viewModel: CameraPreviewViewModel = hiltViewModel()
    val currentColor by viewModel.currentColor.collectAsState()

    val context: Context = LocalContext.current
    val lifecycleOwner: LifecycleOwner = LocalLifecycleOwner.current
    val cameraController: LifecycleCameraController =
        remember { LifecycleCameraController(context) }

    controller.setTakeColorAction {
        return@setTakeColorAction currentColor
    }

    Scaffold(
        modifier = Modifier.fillMaxSize(),
    ) { paddingValues: PaddingValues ->

        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues),
            contentAlignment = Alignment.Center
        ) {
            AndroidView(
                modifier = Modifier
                    .fillMaxSize(),
                factory = { context ->
                    val previewView = PreviewView(context)
                    val cameraProviderFuture = ProcessCameraProvider.getInstance(context)
                    val selector = CameraSelector.Builder()
                        .requireLensFacing(CameraSelector.LENS_FACING_BACK)
                        .build()
                    val preview = CameraPreview.Builder().build().apply {
                        setSurfaceProvider(previewView.surfaceProvider)
                    }

                    val analyserUseCase = setImageAnalysis(context) {
                        viewModel.updateCurrentColor(it)
                    }

                    val camera = cameraProviderFuture.get().bindToLifecycle(
                        lifecycleOwner,
                        selector,
                        analyserUseCase,
                        preview,
                    )

                    camera.cameraControl.setZoomRatio(1f)

                    previewView.apply {
                        layoutParams = LinearLayout.LayoutParams(MATCH_PARENT, MATCH_PARENT)
                        setBackgroundColor(Color.BLACK)
                        implementationMode = PreviewView.ImplementationMode.COMPATIBLE
                        scaleType = PreviewView.ScaleType.FILL_CENTER
                    }
                }
            )

            DSTargetPointerComponent(colorPreview = currentColor)
        }
    }
}

@androidx.annotation.OptIn(ExperimentalGetImage::class)
private fun setImageAnalysis(
    context: Context,
    onFrameChanged: (Int) -> Unit
): ImageAnalysis {
    val mainExecutor: Executor = ContextCompat.getMainExecutor(context)
    val resolutionSelector = ResolutionSelector.Builder()
        .setResolutionStrategy(
            ResolutionStrategy(
                Size(1280, 720),
                FALLBACK_RULE_CLOSEST_HIGHER_THEN_LOWER
            )
        )
        .build()
    val imageAnalysis = ImageAnalysis.Builder()
        .setResolutionSelector(resolutionSelector)
        .setBackpressureStrategy(ImageAnalysis.STRATEGY_KEEP_ONLY_LATEST)
        .build()

    imageAnalysis.setAnalyzer(mainExecutor) { imageProxy ->
        val correctedBitmap: Bitmap = imageProxy
            .toBitmap()
            .rotateBitmap(imageProxy.imageInfo.rotationDegrees)

        val posX = correctedBitmap.width / 2
        val posY = correctedBitmap.height / 2
        val pixelColor = correctedBitmap.getPixel(
            posX,
            posY
        )
        onFrameChanged(pixelColor)
        imageProxy.close()
    }
    return imageAnalysis
}

private fun captureImage(
    context: Context,
    cameraController: LifecycleCameraController,
    onPhotoCaptured: (Bitmap, Int) -> Unit
) {
    val mainExecutor: Executor = ContextCompat.getMainExecutor(context)

    cameraController.takePicture(mainExecutor, object : ImageCapture.OnImageCapturedCallback() {
        override fun onCaptureSuccess(image: ImageProxy) {
            val correctedBitmap: Bitmap = image
                .toBitmap()
                .rotateBitmap(image.imageInfo.rotationDegrees)
            val pixelColor = correctedBitmap.getPixel(
                correctedBitmap.width / 2,
                correctedBitmap.height / 2
            )
            onPhotoCaptured(correctedBitmap, pixelColor)
            image.close()
        }

        override fun onError(exception: ImageCaptureException) {
            Log.e("CameraContent", "Error capturing image", exception)
        }
    })
}

@Preview
@Composable
private fun PreviewComponent() {
    val context = LocalContext.current
    val controller = DSPickerComponentController()
    DSCameraPreviewComponent(controller)
}