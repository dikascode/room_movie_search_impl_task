package com.dikascode.moviesearch.di

import com.dikascode.moviesearch.data.network.OMDbApiService
import com.dikascode.moviesearch.data.repository.MovieDao
import com.dikascode.moviesearch.data.repository.MovieRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Named
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object RepositoryModule {

    @Provides
    @Singleton
    fun provideMovieRepository(
        apiService: OMDbApiService,
        movieDao: MovieDao,
        @Named("API_KEY") apiKey: String
    ): MovieRepository {
        return MovieRepository(apiService, movieDao, apiKey)
    }
}