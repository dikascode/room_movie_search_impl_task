package com.dikascode.moviesearch.di

import com.dikascode.moviesearch.data.network.ApiClient
import com.dikascode.moviesearch.data.network.OMDbApiService
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Named
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object NetworkModule {

    @Provides
    @Singleton
    fun provideApiService(): OMDbApiService {
        return ApiClient.getApiService()
    }

    @Provides
    @Named("API_KEY")
    fun provideApiKey(): String {
        return ""
    }
}