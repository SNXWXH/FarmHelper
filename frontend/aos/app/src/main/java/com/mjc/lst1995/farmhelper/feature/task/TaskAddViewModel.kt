package com.mjc.lst1995.farmhelper.feature.task

import android.view.View
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.mjc.lst1995.farmhelper.core.domain.model.task.RecommendTask
import com.mjc.lst1995.farmhelper.core.domain.usecase.NetworkUseCase
import com.mjc.lst1995.farmhelper.core.domain.usecase.TaskUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class TaskAddViewModel
    @Inject
    constructor(
        private val taskUseCase: TaskUseCase,
        private val networkUseCase: NetworkUseCase,
    ) : ViewModel() {
        private val _recommendTasks = MutableLiveData<List<RecommendTask>>()
        val recommendTask: LiveData<List<RecommendTask>> = _recommendTasks

        private val _progressBarVisibility = MutableLiveData(View.GONE)
        val progressBarVisibility: LiveData<Int> = _progressBarVisibility

        val isSaved = MutableLiveData<Boolean>(false)

        fun getRecommendTask(cropId: Long) {
            viewModelScope.launch {
                _progressBarVisibility.postValue(View.VISIBLE)
                _recommendTasks.postValue(taskUseCase.getRecommendTask(cropId))
                _progressBarVisibility.postValue(View.GONE)
            }
        }

        fun addTask(task: String) {
            _recommendTasks.value = taskUseCase.addTask(task)
        }

        fun updateRecommendTask(position: Int) {
            viewModelScope.launch {
                _recommendTasks.postValue(taskUseCase.updateRecommendTask(position))
            }
        }

        fun saveTask(cropId: Long) {
            viewModelScope.launch {
                _progressBarVisibility.postValue(View.VISIBLE)
                taskUseCase.saveTask(networkUseCase.getDnsServer(), cropId)
                _progressBarVisibility.postValue(View.GONE)
                isSaved.postValue(true)
            }
        }
    }