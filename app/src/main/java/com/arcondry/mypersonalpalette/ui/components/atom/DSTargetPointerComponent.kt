package com.arcondry.mypersonalpalette.ui.components.atom

import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Canvas
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
import androidx.compose.ui.unit.dp
import com.arcondry.mypersonalpalette.common.domain.entities.ColorEntity
import com.arcondry.mypersonalpalette.ui.components.molecule.DSColorComponent

@Composable
fun DSTargetPointerComponent(
    showColorPreview: Boolean = true,
    colorPreview: ColorEntity
) {
    var isExpanded by remember { mutableStateOf(true) }
    val scale: Float by animateFloatAsState(
        if (isExpanded) 2f else 0.5f,
        label = "Scale Animation",
        animationSpec = infiniteRepeatable(
            animation = tween(durationMillis = 1000, easing = LinearEasing),
            repeatMode = RepeatMode.Reverse
        )
    )

    LaunchedEffect(true) {
        isExpanded = false
    }

    BoxWithConstraints(contentAlignment = Alignment.Center) {
        val previewPosX = maxWidth / 2
        val previewPosY = (maxHeight / 2) + 20.dp
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
            DSColorComponent(Modifier.offset(previewPosX, previewPosY), colorObject = colorPreview)
    }
}