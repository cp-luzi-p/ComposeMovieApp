package com.example.composemovieapp.di.module

import com.example.composemovieapp.data.ApiService
import com.example.composemovieapp.repository.AppRepository
import com.example.composemovieapp.repository.AppRepositoryImpl
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import kotlinx.coroutines.DelicateCoroutinesApi
import javax.inject.Singleton

@DelicateCoroutinesApi
@Module
@InstallIn(SingletonComponent::class)
class RepositoryModule {

    @Singleton
    @Provides
    fun provideRepository(apiService: ApiService) : AppRepository {
        return AppRepositoryImpl(apiService)
    }
}