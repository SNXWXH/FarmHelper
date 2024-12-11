package com.mjc.lst1995.farmhelper.feature.work

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.mjc.lst1995.farmhelper.core.domain.model.task.Task
import com.mjc.lst1995.farmhelper.core.domain.usecase.NetworkUseCase
import com.mjc.lst1995.farmhelper.core.domain.usecase.WorkUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

@HiltViewModel
class WorkDetailViewModel
    @Inject
    constructor(
        private val workUseCase: WorkUseCase,
        private val networkUseCase: NetworkUseCase,
    ) : ViewModel() {
        private val _sortType = MutableLiveData<Boolean>(true)
        val sortType: LiveData<Boolean> = _sortType

        init {
            _sortType.value = true
        }

        fun changeSort() {
            _sortType.value = _sortType.value != true
        }

        suspend fun deleteTask(
            workId: Long,
            cropId: Long,
        ) = workUseCase.deleteTask(workId, cropId)

        suspend fun getWorkTaskOtherDetail(cropId: Long) =
            workUseCase.getWorkTaskOtherDetail(
                cropId,
                networkUseCase.getPublicIPAddress(),
            )

        suspend fun getWorkTaskDetails(cropId: Long): Flow<List<Task>> {
            Log.d("tttt", "아이피 주소 : ${networkUseCase.getPublicIPAddress()}")
            return workUseCase.getWorkTaskDetails(
                cropId,
                networkUseCase.getPublicIPAddress(),
            )
        }
    }
