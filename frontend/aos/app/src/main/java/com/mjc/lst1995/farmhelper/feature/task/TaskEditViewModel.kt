package com.mjc.lst1995.farmhelper.feature.task

import android.view.View
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.mjc.lst1995.farmhelper.core.domain.model.task.RecommendTask
import com.mjc.lst1995.farmhelper.core.domain.usecase.TaskUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class TaskEditViewModel
    @Inject
    constructor(
        private val taskUseCase: TaskUseCase,
    ) : ViewModel() {
        private val _recommendTasks = MutableLiveData<List<RecommendTask>>()
        val recommendTask: LiveData<List<RecommendTask>> = _recommendTasks
        private val _progressBarVisibility = MutableLiveData(View.INVISIBLE)
        val progressBarVisibility: LiveData<Int> = _progressBarVisibility
        val editState = MutableLiveData(false)

        fun setTasks(contents: String) {
            _recommendTasks.postValue(
                contents.split("\n\n").mapIndexed { index, s -> RecommendTask(index, s) },
            )
        }

        fun addTask(task: String) {
            if (task.isEmpty()) {
                return
            }
            val tasks = RecommendTask(_recommendTasks.value?.size ?: 0, task)
            val new = _recommendTasks.value?.plus(tasks) ?: listOf(tasks)
            _recommendTasks.postValue(new)
        }

        fun setEditTaskSave(
            workId: Long,
            cropId: Long,
            currentList: List<RecommendTask>,
        ) {
            viewModelScope.launch {
                _progressBarVisibility.postValue(View.VISIBLE)
                editState.postValue(
                    recommendTask.value?.let {
                        taskUseCase.setEditTaskSave(
                            workId,
                            cropId,
                            currentList,
                        )
                    },
                )
                _progressBarVisibility.postValue(View.INVISIBLE)
            }
        }

        fun updateRecommendTask(recommendTask: RecommendTask) {
            val new =
                _recommendTasks.value?.map { if (recommendTask == it) recommendTask.copy(isChecked = !it.isChecked) else it }
                    ?: return
            _recommendTasks.postValue(new)
        }

        fun getRecommendTask(cropId: Long) {
            viewModelScope.launch {
                _progressBarVisibility.postValue(View.VISIBLE)
                val recommendTasks =
                    taskUseCase.getRecommendTask(cropId).mapIndexed { index, recommendTask ->
                        RecommendTask(index + _recommendTasks.value?.size!!, recommendTask.content)
                    }
                val new = _recommendTasks.value?.plus(recommendTasks) ?: recommendTasks
                _recommendTasks.postValue(new)
                _progressBarVisibility.postValue(View.INVISIBLE)
            }
        }
    }
