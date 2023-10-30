package com.arcondry.mypersonalpalette.common.data.usecases

import android.graphics.Bitmap

interface ISavePhotoToGalleryUseCase {
    suspend fun call(capturePhotoBitmap: Bitmap): Result<Unit>
}