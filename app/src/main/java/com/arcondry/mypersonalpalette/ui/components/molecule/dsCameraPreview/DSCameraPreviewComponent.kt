package com.arcondry.mypersonalpalette.ui.components.molecule.dsCameraPreview

import android.content.Context
import android.graphics.Bitmap
import android.graphics.Color
import android.util.Log
import android.view.ViewGroup.LayoutParams.MATCH_PARENT
import android.widget.LinearLayout
import android.widget.Toast
import androidx.camera.core.ExperimentalGetImage
import androidx.camera.core.ImageCapture
import androidx.camera.core.ImageCaptureException
import androidx.camera.core.ImageProxy
import androidx.camera.view.LifecycleCameraController
import androidx.camera.view.PreviewView
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Camera
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExtendedFloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalLifecycleOwner
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.viewinterop.AndroidView
import androidx.core.content.ContextCompat
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.arcondry.mypersonalpalette.common.domain.entities.ColorEntity
import com.arcondry.mypersonalpalette.core.utils.rotateBitmap
import com.arcondry.mypersonalpalette.ui.components.atom.DSTargetPointerComponent
import java.util.concurrent.Executor

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DSCameraPreviewComponent(
    hideButton: Boolean = true,
    onPhotoCaptured: (Bitmap, Int) -> Unit,
) {
    val viewModel: CameraPreviewViewModel = hiltViewModel()
    val cameraState: CameraState by viewModel.state.collectAsStateWithLifecycle()
    var currentColor by remember { mutableStateOf(ColorEntity.empty) }

    val context: Context = LocalContext.current
    val lifecycleOwner: LifecycleOwner = LocalLifecycleOwner.current
    val cameraController: LifecycleCameraController =
        remember { LifecycleCameraController(context) }

    Scaffold(
        modifier = Modifier.fillMaxSize(),
        floatingActionButton = {
            if (!hideButton) {
                ExtendedFloatingActionButton(
                    text = { Text(text = "Take photo") },
                    onClick = { capturePhoto(context, cameraController, onPhotoCaptured) },
                    icon = {
                        Icon(
                            imageVector = Icons.Default.Camera,
                            contentDescription = "Camera capture icon"
                        )
                    }
                )
            }
        }
    ) { paddingValues: PaddingValues ->

        Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
            AndroidView(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(paddingValues),
                factory = { context ->
                    PreviewView(context).apply {
                        layoutParams = LinearLayout.LayoutParams(MATCH_PARENT, MATCH_PARENT)
                        setBackgroundColor(Color.BLACK)
                        implementationMode = PreviewView.ImplementationMode.COMPATIBLE
                        scaleType = PreviewView.ScaleType.FILL_START
                    }.also { previewView ->
                        previewView.controller = cameraController
                        cameraController.bindToLifecycle(lifecycleOwner)
                        previewColor(context, cameraController) {
                            currentColor = ColorEntity.generate(it)
                        }
                    }
                }
            )

            DSTargetPointerComponent(colorPreview = currentColor)
        }
    }
}

@androidx.annotation.OptIn(ExperimentalGetImage::class)
private fun previewColor(
    context: Context,
    cameraController: LifecycleCameraController,
    onFrameChanged: (Int) -> Unit
) {
    val mainExecutor: Executor = ContextCompat.getMainExecutor(context)

    cameraController.setImageAnalysisAnalyzer(mainExecutor) { imageProxy ->
            val correctedBitmap: Bitmap = imageProxy
                .toBitmap()
                .rotateBitmap(imageProxy.imageInfo.rotationDegrees)
            val pixelColor = correctedBitmap.getPixel(
                correctedBitmap.width / 2,
                correctedBitmap.height / 2
            )
        onFrameChanged(pixelColor)
        imageProxy.close()
    }
}

private fun capturePhoto(
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

@Composable
private fun LastPhotoPreview(
    modifier: Modifier = Modifier,
    lastCapturedPhoto: Bitmap
) {

    val capturedPhoto: ImageBitmap =
        remember(lastCapturedPhoto.hashCode()) { lastCapturedPhoto.asImageBitmap() }

    Card(
        modifier = modifier
            .size(128.dp)
            .padding(16.dp),
        elevation = CardDefaults.cardElevation(
            defaultElevation = 10.dp
        ),
        shape = MaterialTheme.shapes.large
    ) {
        Image(
            bitmap = capturedPhoto,
            contentDescription = "Last captured photo",
            contentScale = androidx.compose.ui.layout.ContentScale.Crop
        )
    }
}

@Preview
@Composable
private fun PreviewComponent() {
    val context = LocalContext.current
    DSCameraPreviewComponent() { _, _ ->
        Toast.makeText(context, "Preview", Toast.LENGTH_LONG).show()
    }
}