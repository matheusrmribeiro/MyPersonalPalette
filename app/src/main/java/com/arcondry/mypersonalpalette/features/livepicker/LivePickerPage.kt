package com.arcondry.mypersonalpalette.features.livepicker

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Camera
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.arcondry.mypersonalpalette.common.domain.entities.ColorEntity
import com.arcondry.mypersonalpalette.ui.components.molecule.DSColorComponent
import com.arcondry.mypersonalpalette.ui.components.organism.dsPickerComponent.DSPickerComponent
import com.arcondry.mypersonalpalette.ui.components.organism.dsPickerComponent.DSPickerComponentController

@Composable
fun LivePickerPage() {
    val viewModel: LivePickerViewModel = hiltViewModel()
    val colorsState: List<ColorEntity> by viewModel.colors.collectAsStateWithLifecycle()

    val pickerController = DSPickerComponentController()

    Box(
        modifier = Modifier,
        contentAlignment = Alignment.BottomCenter
    ) {
        DSPickerComponent(pickerController)
        Row() {
            LazyRow(
                modifier = Modifier
                    .weight(1f, true)
                    .padding(8.dp)
            ) {
                items(colorsState) { item ->
                    DSColorComponent(colorObject = item)
                }
            }
            Button(
                onClick = {
                    pickerController.takeCurrentColor?.let { color ->
                        viewModel.addColor(
                            color
                        )
                    }
                },
                content = {
                    Icon(
                        imageVector = Icons.Default.Camera,
                        contentDescription = "Camera capture icon"
                    )
                }
            )
        }
    }
}