package com.mjc.lst1995.farmhelper.core.domain.usecase

import com.mjc.lst1995.farmhelper.core.domain.repository.AuthRepository
import javax.inject.Inject

class AuthUseCase @Inject constructor(
    private val authRepository: AuthRepository,
) {
    fun firebaseAuthWithGoogle(idToken: String) = authRepository.firebaseAuthWithGoogle(idToken)

    fun loginStateFlow() = authRepository.loginStateFlow()

    suspend fun userIsJoined() = authRepository.userIsJoined()

    suspend fun setUserNickName(nickName: String) = authRepository.setUserNickName(nickName)
}
