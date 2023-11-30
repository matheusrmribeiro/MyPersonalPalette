package com.arcondry.mypersonalpalette.ui.components.molecule.dsCameraPreview

import androidx.lifecycle.ViewModel
import com.arcondry.mypersonalpalette.common.domain.entities.ColorEntity
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import javax.inject.Inject

@HiltViewModel
class CameraPreviewViewModel @Inject constructor () : ViewModel() {

    private val _currentColor = MutableStateFlow(ColorEntity.empty)
    val currentColor = _currentColor.asStateFlow()

    fun updateCurrentColor(color: Int) {
        _currentColor.value = ColorEntity.generate(color)
    }

}
