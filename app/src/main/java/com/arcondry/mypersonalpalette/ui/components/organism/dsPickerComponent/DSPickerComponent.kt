package com.arcondry.mypersonalpalette.ui.components.organism.dsPickerComponent

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
fun DSPickerComponent(
    controller: DSPickerComponentController,
) {

    val cameraPermissionState: PermissionState =
        rememberPermissionState(android.Manifest.permission.CAMERA)

    MainContent(
        hasPermission = cameraPermissionState.status.isGranted,
        onRequestPermission = cameraPermissionState::launchPermissionRequest,
        controller = controller
    )

}

@Composable
private fun MainContent(
    hasPermission: Boolean,
    onRequestPermission: () -> Unit,
    controller: DSPickerComponentController,
) {
    if (hasPermission) {
        DSCameraPreviewComponent(controller = controller)
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
    val context = LocalContext.current
    val controller = DSPickerComponentController()
    DSPickerComponent(controller)
}