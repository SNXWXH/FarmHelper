package com.mjc.lst1995.farmhelper.core.data.di

import com.mjc.lst1995.farmhelper.core.data.network.api.UserSettingApi
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
}
