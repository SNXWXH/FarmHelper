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
class NickNameViewModel
    @Inject
    constructor(
        private val authUseCase: AuthUseCase,
    ) : ViewModel() {
        private val _isChanged: MutableLiveData<Boolean?> = MutableLiveData(null)
        val isChanged: LiveData<Boolean?> = _isChanged

        val inputUserNickName: MutableLiveData<String?> =  MutableLiveData(null)

        fun setUserNickName() {
            viewModelScope.launch {
                inputUserNickName.value?.let {
                    _isChanged.postValue(authUseCase.setUserNickName(it))
                }
            }
        }
    }
