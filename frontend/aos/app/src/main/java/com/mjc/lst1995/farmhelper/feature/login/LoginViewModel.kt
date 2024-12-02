package com.mjc.lst1995.farmhelper.feature.login

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.mjc.lst1995.farmhelper.core.domain.usecase.AuthUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class LoginViewModel
    @Inject
    constructor(
        private val authUseCase: AuthUseCase,
    ) : ViewModel() {
        val loginState = authUseCase.loginStateFlow()

        private val _isJoined = MutableLiveData<Boolean?>(null)
        val isJoined: LiveData<Boolean?> = _isJoined

        fun firebaseAuthWithGoogle(idToken: String) {
            authUseCase.firebaseAuthWithGoogle(idToken)
        }

        suspend fun userIsJoined() {
            viewModelScope.launch {
                _isJoined.postValue(authUseCase.userIsJoined())
            }
        }
    }
