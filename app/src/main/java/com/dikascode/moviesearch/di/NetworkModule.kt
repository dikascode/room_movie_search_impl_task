package com.dikascode.moviesearch.di

import com.dikascode.moviesearch.data.network.OMDbApiService
import com.dikascode.moviesearch.util.HttpException
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Named
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object NetworkModule {

    private const val BASE_URL = "https://www.omdbapi.com/"

    @Provides
    @Singleton
    fun provideLoggingInterceptor(): HttpLoggingInterceptor {
        return HttpLoggingInterceptor().apply {
            level = HttpLoggingInterceptor.Level.BODY
        }
    }

    @Provides
    @Singleton
    fun provideErrorHandlingInterceptor(): Interceptor {
        return Interceptor { chain ->
            val request = chain.request()
            val response = chain.proceed(request)
            if (!response.isSuccessful) {
                val errorBody = response.body?.string()
                val errorMessage = when (response.code) {
                    400 -> "Bad Request: $errorBody"
                    401 -> "Unauthorized: $errorBody"
                    403 -> "Forbidden: $errorBody"
                    404 -> "Not Found: $errorBody"
                    500 -> "Internal Server Error: $errorBody"
                    else -> "Error: $errorBody"
                }
                throw HttpException(response.code, errorMessage)
            }
            response
        }
    }

    @Provides
    @Singleton
    fun provideOkHttpClient(
        loggingInterceptor: HttpLoggingInterceptor,
        errorHandlingInterceptor: Interceptor
    ): OkHttpClient {
        return OkHttpClient.Builder()
            .addInterceptor(loggingInterceptor)
            .addInterceptor(errorHandlingInterceptor)
            .build()
    }

    @Provides
    @Singleton
    fun provideRetrofit(okHttpClient: OkHttpClient): Retrofit {
        return Retrofit.Builder()
            .baseUrl(BASE_URL)
            .client(okHttpClient)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    @Provides
    @Singleton
    fun provideApiService(retrofit: Retrofit): OMDbApiService {
        return retrofit.create(OMDbApiService::class.java)
    }

    @Provides
    @Named("API_KEY")
    fun provideApiKey(): String {
        return ""
    }
}