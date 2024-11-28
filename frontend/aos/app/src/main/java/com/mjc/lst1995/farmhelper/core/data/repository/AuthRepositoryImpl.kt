package com.mjc.lst1995.farmhelper.core.data.repository

import android.util.Log
import com.google.firebase.auth.FirebaseAuth
import com.mjc.lst1995.farmhelper.core.data.network.api.UserSettingApi
import com.mjc.lst1995.farmhelper.core.data.network.request.user.AuthToken
import com.mjc.lst1995.farmhelper.core.data.network.request.user.NickNameToken
import com.mjc.lst1995.farmhelper.core.domain.repository.AuthRepository
import javax.inject.Inject

class AuthRepositoryImpl
@Inject
constructor(
    private val firebaseAuth: FirebaseAuth,
    private val userSettingApi: UserSettingApi,
) : AuthRepository {
    override suspend fun userIsJoined(): Boolean {
        try {
            firebaseAuth.uid?.let { uid ->
                return userSettingApi.userIdCheck(AuthToken(uid)).isJoined
            } ?: return false
        } catch (e: Exception) {
            Log.d("ttttt","통신 에러" + e.message)
            return false
        }
    }

    override suspend fun setUserNickName(nickName: String): Boolean {
        try {
            val response =
                userSettingApi.setUserNickName(NickNameToken(firebaseAuth.uid!!,nickName))
            return response.result
        } catch (e: Exception) {
            Log.d("ttttt","통신 에러" + e.message)
            return false
        }
    }
}
