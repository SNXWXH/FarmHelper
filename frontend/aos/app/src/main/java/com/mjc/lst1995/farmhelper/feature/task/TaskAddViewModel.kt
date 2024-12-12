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

        private val _progressBarVisibility = MutableLiveData(View.INVISIBLE)
        val progressBarVisibility: LiveData<Int> = _progressBarVisibility

        val isSaved = MutableLiveData<Boolean>(false)

        fun getRecommendTask(cropId: Long) {
            viewModelScope.launch {
                _progressBarVisibility.postValue(View.VISIBLE)
                val recommendTasks =
                    taskUseCase.getRecommendTask(cropId).mapIndexed { index, recommendTask ->
                        val size = _recommendTasks.value?.size ?: 0
                        RecommendTask((index + size), recommendTask.content)
                    }
                val new = _recommendTasks.value?.plus(recommendTasks) ?: recommendTasks
                _recommendTasks.postValue(new)
                _progressBarVisibility.postValue(View.INVISIBLE)
            }
        }

        fun addTask(task: String) {
            if (task.isEmpty()) {
                return
            }
            val tasks = RecommendTask(_recommendTasks.value?.size ?: 0, task)
            val new = _recommendTasks.value?.plus(tasks) ?: listOf(tasks)
            _recommendTasks.postValue(new)
        }

        fun updateRecommendTask(recommendTask: RecommendTask) {
            val new =
                _recommendTasks.value
                    ?.map { if (recommendTask.id == it.id) recommendTask.copy(isChecked = !it.isChecked) else it }
                    ?.toList()
                    ?: return
            _recommendTasks.postValue(new)
        }

        fun saveTask(cropId: Long) {
            viewModelScope.launch {
                _progressBarVisibility.postValue(View.VISIBLE)
                recommendTask.value?.let {
                    taskUseCase.saveTask(
                        networkUseCase.getPublicIPAddress(),
                        cropId,
                        it,
                    )
                }
                _progressBarVisibility.postValue(View.GONE)
                isSaved.postValue(true)
            }
        }
    }
