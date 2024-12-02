package com.mjc.lst1995.farmhelper.core.data.repository

import android.util.Log
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.GoogleAuthProvider
import com.mjc.lst1995.farmhelper.core.data.network.api.UserSettingApi
import com.mjc.lst1995.farmhelper.core.data.network.api.WorkApi
import com.mjc.lst1995.farmhelper.core.data.network.request.user.AuthToken
import com.mjc.lst1995.farmhelper.core.data.network.request.user.NickNameToken
import com.mjc.lst1995.farmhelper.core.data.network.request.work.WorkDetailToken
import com.mjc.lst1995.farmhelper.core.domain.repository.AuthRepository
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

class AuthRepositoryImpl
    @Inject
    constructor(
        private val auth: FirebaseAuth,
        private val userSettingApi: UserSettingApi,
        private val workApi: WorkApi,
    ) : AuthRepository {
        override fun firebaseAuthWithGoogle(idToken: String) {
            try {
                auth.signInWithCredential(GoogleAuthProvider.getCredential(idToken, null))
                Log.d("AuthRepositoryImpl", "${auth.uid}")
            } catch (e: Exception) {
                Log.e("AuthRepositoryImpl", "Firebase auth with Google failed", e)
            }
        }

        override fun loginStateFlow(): Flow<Boolean> =
            callbackFlow {
                val authStateListener =
                    FirebaseAuth.AuthStateListener { auth ->
                        trySend(auth.currentUser != null)
                    }
                auth.addAuthStateListener(authStateListener)
                awaitClose { auth.removeAuthStateListener(authStateListener) }
            }

        override fun getUserNickName(): Flow<String?> =
            callbackFlow {
                val authStateListener =
                    FirebaseAuth.AuthStateListener { auth ->
                        auth.uid?.let {
                            launch {
                                try {
                                    val nickName =
                                        workApi
                                            .getWorkTaskDetails(WorkDetailToken(it, "1", "8.8.8.8"))
                                            .nickname
                                    trySend(nickName)
                                } catch (e: Exception) {
                                    trySend(null)
                                }
                            }
                        } ?: run {
                            trySend(null)
                        }
                    }
                auth.addAuthStateListener(authStateListener)
                awaitClose {
                    auth.removeAuthStateListener(authStateListener)
                }
            }

        override suspend fun userIsJoined(): Boolean {
            try {
                return userSettingApi.userIdCheck(AuthToken(auth.uid!!)).isOk
            } catch (e: Exception) {
                Log.d("ttttt", "userIsJoined 통신 에러" + e.message)
                return false
            }
        }

        override fun firebaseSignOut() {
            auth.signOut()
        }

        override suspend fun setUserNickName(nickName: String): Boolean {
            try {
                val response =
                    userSettingApi.setUserNickName(NickNameToken(auth.uid!!, nickName))
                return response.isOk
            } catch (e: Exception) {
                Log.d("ttttt", "setUserNickName 통신 에러" + e.message)
                return false
            }
        }
    }
