package com.arcondry.mypersonalpalette.ui.components.molecule

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.CornerRadius
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.arcondry.mypersonalpalette.common.domain.entities.ColorEntity

@Composable
fun DSColorComponent(
    modifier: Modifier = Modifier,
    colorObject: ColorEntity,
    height: Dp = 50.dp,
    width: Dp = 50.dp,
) {
    Box(
        modifier = modifier
            .size(width = width, height = height),
        contentAlignment = Alignment.Center
    ) {
        Canvas(
            modifier = Modifier.fillMaxSize(),
            onDraw = {
                drawRoundRect(
                    color = Color(colorObject.color),
                    cornerRadius = CornerRadius(10f, 10f)
                )
            })
        Spacer(modifier = Modifier.height(4.dp))
        Text(
            modifier = Modifier.padding(4.dp),
            text = colorObject.colorText.uppercase(),
            fontSize = 10.sp,
            fontWeight = FontWeight.Medium
        )
    }
}

@Preview
@Composable
private fun PreviewComponent() {
    DSColorComponent(Modifier, ColorEntity(color = Color(0xFFED5537).toArgb(), colorText = "#ed5537"))
}