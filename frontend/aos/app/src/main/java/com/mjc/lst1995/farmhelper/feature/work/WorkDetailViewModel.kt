package com.mjc.lst1995.farmhelper.feature.work

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.mjc.lst1995.farmhelper.core.domain.usecase.AuthUseCase
import com.mjc.lst1995.farmhelper.core.domain.usecase.NetworkUseCase
import com.mjc.lst1995.farmhelper.core.domain.usecase.WorkUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class WorkDetailViewModel
    @Inject
    constructor(
        private val workUseCase: WorkUseCase,
        private val authUseCase: AuthUseCase,
        private val networkUseCase: NetworkUseCase,
    ) : ViewModel() {
        val nickName = authUseCase.getUserNickName()
        private val _sortType = MutableLiveData<Boolean>(true)
        val sortType: LiveData<Boolean> = _sortType

        init {
            _sortType.value = true
        }

        fun changeSort() {
            _sortType.value = _sortType.value != true
        }

        suspend fun getWorkTaskDetails(cropId: Long) = workUseCase.getWorkTaskDetails(cropId, networkUseCase.getPublicIPAddress()!!)
    }
