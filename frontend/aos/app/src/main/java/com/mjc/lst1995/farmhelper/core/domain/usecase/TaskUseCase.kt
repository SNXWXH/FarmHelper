package com.mjc.lst1995.farmhelper.core.domain.usecase

import com.mjc.lst1995.farmhelper.core.domain.model.task.RecommendTask
import com.mjc.lst1995.farmhelper.core.domain.repository.TaskRepository
import javax.inject.Inject

class TaskUseCase
    @Inject
    constructor(
        private val taskRepository: TaskRepository,
    ) {
        private val recommendTasks = mutableListOf<RecommendTask>()

        suspend fun getRecommendTask(cropId: Long): List<RecommendTask> {
            taskRepository.getRecommendTasks(cropId).forEachIndexed { index, s ->
                recommendTasks.add(RecommendTask(index, s))
            }
            return recommendTasks.toList()
        }

        fun addTask(task: String): List<RecommendTask> {
            val index = recommendTasks.size
            recommendTasks.add(RecommendTask(index, task))
            return recommendTasks.toList()
        }

        fun updateRecommendTask(recommendTask: RecommendTask): List<RecommendTask> {
            val index = recommendTasks.indexOfFirst { it.id == recommendTask.id }
            recommendTasks[index] = recommendTask
            return recommendTasks.toList()
        }
    }
