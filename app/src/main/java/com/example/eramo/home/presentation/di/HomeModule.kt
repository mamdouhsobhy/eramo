package com.example.eramo.home.presentation.di

import com.example.eramo.core.data.module.NetworkModule
import com.example.eramo.core.data.utils.EmittingResponse
import com.example.eramo.home.data.datasource.HomeService
import com.example.eramo.home.data.repositoryimb.HomeRepositoryImpl
import com.example.eramo.home.domain.repository.HomeRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import javax.inject.Singleton

@Module(includes = [NetworkModule::class])
@InstallIn(SingletonComponent::class)
class HomeModule {

    @Singleton
    @Provides
    fun provideHomeApi(retrofit: Retrofit): HomeService {
        return retrofit.create(HomeService::class.java)
    }

    @Singleton
    @Provides
    fun provideHomeRepository(homeService: HomeService, emittingResponse: EmittingResponse): HomeRepository {
        return HomeRepositoryImpl(homeService,emittingResponse)
    }

}