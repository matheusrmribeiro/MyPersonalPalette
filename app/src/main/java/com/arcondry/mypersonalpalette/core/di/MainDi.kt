package com.arcondry.mypersonalpalette.core.di

import android.content.Context
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object MainDi {
    private const val SCHEMA_VERSION: Long = 1
//
//    @Provides
//    fun provideDefaultRealm(config: RealmConfiguration) = Realm.getInstance(config)
//
//    @Provides
//    @Singleton
//    fun provideRealmConfiguration(@ApplicationContext appContext: Context): RealmConfiguration {
//        Realm.init(appContext)
//        return RealmConfiguration.Builder()
//            .schemaVersion(SCHEMA_VERSION)
//            .deleteRealmIfMigrationNeeded()
//            .migration { _, _, _ -> }
//            .build()
//    }

}