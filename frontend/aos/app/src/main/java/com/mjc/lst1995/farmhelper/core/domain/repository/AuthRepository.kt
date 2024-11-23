package com.mjc.lst1995.farmhelper.core.domain.repository

interface AuthRepository {
    suspend fun userIsJoined(): Boolean
}
