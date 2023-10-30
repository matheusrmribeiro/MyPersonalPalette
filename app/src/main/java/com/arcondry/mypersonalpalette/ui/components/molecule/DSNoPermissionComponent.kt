package com.arcondry.mypersonalpalette.ui.components.molecule

import android.widget.Toast
import android.widget.Toast.LENGTH_LONG
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Camera
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.arcondry.mypersonalpalette.R

@Composable
fun DSNoPermissionComponent(icon: ImageVector, textRes: Int, onRequestPermission: () -> Unit) {
    Column(
        modifier = Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Text(text = stringResource(id = textRes), textAlign = TextAlign.Center)
        Spacer(modifier = Modifier.height(10.dp))
        Button(onClick = onRequestPermission) {
            Icon(imageVector = icon, contentDescription = "Permission icon")
            Text(text = stringResource(id = R.string.grant_permission))
        }
    }
}

@Preview
@Composable
private fun PreviewComponent() {
    val context = LocalContext.current
    DSNoPermissionComponent(
        icon = Icons.Default.Camera,
        textRes = R.string.camera_permission
    ) {
        Toast.makeText(context, "Preview", LENGTH_LONG).show()
    }
}