package com.mjc.lst1995.farmhelper.core.data.di

import com.mjc.lst1995.farmhelper.core.data.network.api.CropApi
import com.mjc.lst1995.farmhelper.core.data.network.api.UserSettingApi
import com.mjc.lst1995.farmhelper.core.data.network.api.WeatherApi
import com.mjc.lst1995.farmhelper.core.data.network.api.WorkApi
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.create
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object ApiModule {
    @Provides
    @Singleton
    fun provideUserSettingApi(retrofit: Retrofit): UserSettingApi = retrofit.create()

    @Provides
    @Singleton
    fun provideCropApi(retrofit: Retrofit): CropApi = retrofit.create()

    @Provides
    @Singleton
    fun provideWeatherApi(retrofit: Retrofit): WeatherApi = retrofit.create()

    @Provides
    @Singleton
    fun provideWorkApi(retrofit: Retrofit): WorkApi = retrofit.create()
}
