package com.mjc.lst1995.farmhelper.core.data.di

import com.mjc.lst1995.farmhelper.core.data.repository.AuthRepositoryImpl
import com.mjc.lst1995.farmhelper.core.data.repository.CropRepositoryImpl
import com.mjc.lst1995.farmhelper.core.data.repository.ImageRepositoryImpl
import com.mjc.lst1995.farmhelper.core.data.repository.TaskRepositoryImpl
import com.mjc.lst1995.farmhelper.core.data.repository.WeatherRepositoryImpl
import com.mjc.lst1995.farmhelper.core.data.repository.WorkRepositoryImpl
import com.mjc.lst1995.farmhelper.core.domain.repository.AuthRepository
import com.mjc.lst1995.farmhelper.core.domain.repository.CropRepository
import com.mjc.lst1995.farmhelper.core.domain.repository.ImageRepository
import com.mjc.lst1995.farmhelper.core.domain.repository.TaskRepository
import com.mjc.lst1995.farmhelper.core.domain.repository.WeatherRepository
import com.mjc.lst1995.farmhelper.core.domain.repository.WorkRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
interface RepositoryModule {
    @Binds
    @Singleton
    fun bindAuthRepository(authRepositoryImpl: AuthRepositoryImpl): AuthRepository

    @Binds
    @Singleton
    fun bindCropRepository(cropRepositoryImpl: CropRepositoryImpl): CropRepository

    @Binds
    @Singleton
    fun bindWeatherRepository(weatherRepositoryImpl: WeatherRepositoryImpl): WeatherRepository

    @Binds
    @Singleton
    fun bindWorkRepository(workRepositoryImpl: WorkRepositoryImpl): WorkRepository

    @Binds
    @Singleton
    fun bindImageRepository(imageRepositoryImpl: ImageRepositoryImpl): ImageRepository

    @Binds
    @Singleton
    fun bindTaskRepository(taskRepositoryImpl: TaskRepositoryImpl): TaskRepository
}
