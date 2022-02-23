package com.hotmart.locations.core.di

import com.hotmart.locations.core.http.HttpClient
import com.hotmart.locations.core.http.HttpManager
import com.jakewharton.retrofit2.converter.kotlinx.serialization.asConverterFactory
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import kotlinx.serialization.json.Json
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.OkHttpClient
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class CoreModule {

    @Provides
    @Singleton
    internal fun providesHttpClient(): OkHttpClient = HttpClient().instantiate()

    @Provides
    @Singleton
    internal fun providesHttpManager(okHttpClient: OkHttpClient): HttpManager {
        val contentType = "application/json".toMediaType()
        val converterFactory = Json.asConverterFactory(contentType)
        return HttpManager(okHttpClient, converterFactory)
    }
}