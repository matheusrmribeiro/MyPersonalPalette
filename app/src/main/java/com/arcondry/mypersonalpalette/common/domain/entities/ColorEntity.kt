package com.arcondry.mypersonalpalette.common.domain.entities

data class ColorEntity(
    val color: Int,
    val colorText: String,
) {

    companion object {
        val empty = ColorEntity(
            color = 0,
            colorText = ""
        )

        fun generate(color: Int): ColorEntity = ColorEntity(
            color = color,
            colorText = "#${color.toString(16)}"
        )
    }
}