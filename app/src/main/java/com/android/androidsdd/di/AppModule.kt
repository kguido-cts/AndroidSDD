package com.android.androidsdd.di

import com.android.androidsdd.data.datasource.AndroidAssetReader
import com.android.androidsdd.data.datasource.AssetReader
import com.android.androidsdd.data.repository.AssetHomeContentRepository
import com.android.androidsdd.domain.repository.HomeContentRepository
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.serialization.json.Json
import javax.inject.Singleton

/**
 * Application-level Hilt module providing:
 * - Configured [Json] instance
 * - IO [CoroutineDispatcher]
 * - [AssetReader] binding (Android implementation)
 * - [HomeContentRepository] binding (asset-backed implementation)
 */
@Module
@InstallIn(SingletonComponent::class)
abstract class AppModule {

    @Binds
    @Singleton
    abstract fun bindAssetReader(impl: AndroidAssetReader): AssetReader

    @Binds
    @Singleton
    abstract fun bindHomeContentRepository(impl: AssetHomeContentRepository): HomeContentRepository

    companion object {

        @Provides
        @Singleton
        fun provideJson(): Json = Json { ignoreUnknownKeys = true }

        @Provides
        @IoDispatcher
        fun provideIoDispatcher(): CoroutineDispatcher = Dispatchers.IO
    }
}


