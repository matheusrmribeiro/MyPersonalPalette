package com.arcondry.mypersonalpalette.ui.components.organism

import android.widget.Toast
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Camera
import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import com.arcondry.mypersonalpalette.R
import com.arcondry.mypersonalpalette.ui.components.molecule.DSNoPermissionComponent
import com.arcondry.mypersonalpalette.ui.components.molecule.dsCameraPreview.DSCameraPreviewComponent
import com.google.accompanist.permissions.ExperimentalPermissionsApi
import com.google.accompanist.permissions.PermissionState
import com.google.accompanist.permissions.isGranted
import com.google.accompanist.permissions.rememberPermissionState

@OptIn(ExperimentalPermissionsApi::class)
@Composable
fun DSLivePickerComponent() {

    val cameraPermissionState: PermissionState =
        rememberPermissionState(android.Manifest.permission.CAMERA)

    MainContent(
        hasPermission = cameraPermissionState.status.isGranted,
        onRequestPermission = cameraPermissionState::launchPermissionRequest
    )

}

@Composable
private fun MainContent(
    hasPermission: Boolean,
    onRequestPermission: () -> Unit
) {
    val context = LocalContext.current

    if (hasPermission) {
        DSCameraPreviewComponent() {
            Toast.makeText(context, "Captured", Toast.LENGTH_LONG).show()
        }
    } else {
        DSNoPermissionComponent(
            icon = Icons.Default.Camera,
            textRes = R.string.camera_permission,
            onRequestPermission = onRequestPermission
        )
    }
}

@Preview
@Composable
private fun PreviewComponent() {
    DSLivePickerComponent()
}