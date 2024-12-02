package com.mjc.lst1995.farmhelper.core.domain.repository

import kotlinx.coroutines.flow.Flow

interface AuthRepository {
    fun firebaseAuthWithGoogle(idToken: String)

    fun firebaseSignOut()

    fun loginStateFlow(): Flow<Boolean>

    fun getUserNickName(): Flow<String?>

    suspend fun userIsJoined(): Boolean

    suspend fun setUserNickName(nickName: String): Boolean
}
