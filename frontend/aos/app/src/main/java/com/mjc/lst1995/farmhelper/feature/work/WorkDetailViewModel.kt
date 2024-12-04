package com.mjc.lst1995.farmhelper.feature.work

import androidx.lifecycle.ViewModel
import com.mjc.lst1995.farmhelper.core.domain.usecase.NetworkUseCase
import com.mjc.lst1995.farmhelper.core.domain.usecase.WorkUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class WorkDetailViewModel
    @Inject
    constructor(
        private val workUseCase: WorkUseCase,
        private val networkUseCase: NetworkUseCase,
    ) : ViewModel() {
        suspend fun getWorkTaskDetails(cropId: Long) = workUseCase.getWorkTaskDetails(cropId, networkUseCase.getPublicIPAddress()!!)
    }
