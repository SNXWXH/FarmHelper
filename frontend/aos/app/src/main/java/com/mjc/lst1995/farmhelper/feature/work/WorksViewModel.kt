package com.mjc.lst1995.farmhelper.feature.work

import androidx.lifecycle.ViewModel
import com.mjc.lst1995.farmhelper.core.domain.usecase.WorkUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class WorksViewModel
    @Inject
    constructor(
        private val workUseCase: WorkUseCase,
    ) : ViewModel() {
        val works = workUseCase.getWorks()
        val nickname = workUseCase.getNickname()
    }
