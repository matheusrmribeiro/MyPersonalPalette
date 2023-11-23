package com.arcondry.mypersonalpalette.ui.components.atom

import android.widget.Toast
import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.arcondry.mypersonalpalette.common.domain.entities.ColorEntity
import com.arcondry.mypersonalpalette.ui.components.molecule.DSColorComponent
import com.arcondry.mypersonalpalette.ui.components.molecule.dsCameraPreview.DSCameraPreviewComponent

@Composable
fun DSTargetPointerComponent(
    showColorPreview: Boolean = true,
    colorPreview: ColorEntity
) {
    var isExpanded by remember { mutableStateOf(true) }
    val scale: Float by animateFloatAsState(
        if (isExpanded) 2f else 1f,
        label = "Scale Animation",
        animationSpec = infiniteRepeatable(
            animation = tween(durationMillis = 1000, easing = LinearEasing),
            repeatMode = RepeatMode.Reverse
        )
    )

    LaunchedEffect(true) {
        isExpanded = false
    }

    BoxWithConstraints(contentAlignment = Alignment.Center, modifier = Modifier.size(160.dp)) {
        val previewPosX = maxWidth / 2
        val previewPosY = maxHeight / 2
        Canvas(
            modifier = Modifier
                .size(2.dp),
            onDraw = {
                drawCircle(color = Color.Gray)
            })
        Canvas(
            modifier = Modifier
                .size(80.dp)
                .scale(scale),
            onDraw = {
                drawCircle(
                    color = Color.Gray,
                    style = Stroke(width = 4f)
                )
            })
        if (showColorPreview)
            DSColorComponent(
                modifier = Modifier.offset(0.dp, 30.dp),
                colorObject = colorPreview,
                height = 20.dp,
                width = 60.dp
            )
    }
}

@Preview
@Composable
private fun PreviewComponent() {
    DSTargetPointerComponent(
        showColorPreview = true,
        colorPreview = ColorEntity(
            color = Color(0xFFED5537).toArgb(),
            colorText = "#fca521"
        )
    )
}