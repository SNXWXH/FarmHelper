package com.mjc.lst1995.farmhelper.core.data.di

import com.mjc.lst1995.farmhelper.core.data.repository.AuthRepositoryImpl
import com.mjc.lst1995.farmhelper.core.domain.repository.AuthRepository
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
}
