package com.mjc.lst1995.farmhelper.core.data.repository

import com.google.firebase.auth.FirebaseAuth
import com.mjc.lst1995.farmhelper.core.data.network.api.UserSettingApi
import com.mjc.lst1995.farmhelper.core.data.network.request.AuthToken
import com.mjc.lst1995.farmhelper.core.domain.repository.AuthRepository
import javax.inject.Inject

class AuthRepositoryImpl
    @Inject
    constructor(
        private val firebaseAuth: FirebaseAuth,
        private val userSettingApi: UserSettingApi,
    ) : AuthRepository {
        override suspend fun userIsJoined(): Boolean {
            firebaseAuth.uid?.let { uid ->
                return userSettingApi.userIdCheck(AuthToken(uid)).isJoined
            } ?: return false
        }
    }
