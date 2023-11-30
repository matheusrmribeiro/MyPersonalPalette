package com.arcondry.mypersonalpalette.features.livepicker

import androidx.lifecycle.ViewModel
import com.arcondry.mypersonalpalette.common.data.usecases.ISavePhotoToGalleryUseCase
import com.arcondry.mypersonalpalette.common.domain.entities.ColorEntity
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import javax.inject.Inject

@HiltViewModel
class LivePickerViewModel @Inject constructor(
    private val savePhotoToGalleryUseCase: ISavePhotoToGalleryUseCase
) : ViewModel() {

    private val _colors = MutableStateFlow(mutableListOf<ColorEntity>())
    val colors = _colors.asStateFlow()

    fun addColor(color: ColorEntity) {
        _colors.value = _colors.value.toMutableList().apply { add(color) }
    }

    fun addColor(color: Int) {
        val colorObject = ColorEntity.generate(color)
        addColor(colorObject)
    }

    override fun onCleared() {
        _colors.value = mutableListOf()
        super.onCleared()
    }
}
