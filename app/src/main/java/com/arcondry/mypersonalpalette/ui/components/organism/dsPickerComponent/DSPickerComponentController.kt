package com.arcondry.mypersonalpalette.ui.components.organism.dsPickerComponent

import com.arcondry.mypersonalpalette.common.domain.entities.ColorEntity

typealias TakeColor = () -> ColorEntity
class DSPickerComponentController() {

    private var onTakeColor: TakeColor? = null
    val takeCurrentColor: ColorEntity?
        get() = onTakeColor?.invoke()

    fun setTakeColorAction(action: TakeColor) {
        onTakeColor = action
    }

}