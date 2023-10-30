package com.arcondry.mypersonalpalette.common.di

import android.content.Context
import com.arcondry.mypersonalpalette.common.data.usecases.ISavePhotoToGalleryUseCase
import com.arcondry.mypersonalpalette.common.data.usecases.SavePhotoToGalleryUseCaseImpl
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object CommonDI {

    @Singleton
    @Provides
    fun provideSavePhotoToGalleryUseCase(@ApplicationContext context: Context): ISavePhotoToGalleryUseCase =
        SavePhotoToGalleryUseCaseImpl(context)

}