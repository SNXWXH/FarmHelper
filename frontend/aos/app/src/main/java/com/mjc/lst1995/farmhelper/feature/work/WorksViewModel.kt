package com.mjc.lst1995.farmhelper.feature.work

import androidx.lifecycle.ViewModel
import com.mjc.lst1995.farmhelper.core.domain.usecase.AuthUseCase
import com.mjc.lst1995.farmhelper.core.domain.usecase.WorkUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class WorksViewModel
    @Inject
    constructor(
        private val workUseCase: WorkUseCase,
        private val authUseCase: AuthUseCase,
    ) : ViewModel() {
        val works = workUseCase.getWorks()
        val nickName = authUseCase.getUserNickName()
    }
