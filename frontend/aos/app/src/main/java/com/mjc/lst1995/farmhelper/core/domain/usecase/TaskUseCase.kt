package com.mjc.lst1995.farmhelper.core.domain.usecase

import com.mjc.lst1995.farmhelper.core.domain.model.task.RecommendTask
import com.mjc.lst1995.farmhelper.core.domain.repository.TaskRepository
import com.mjc.lst1995.farmhelper.core.domain.repository.WorkRepository
import javax.inject.Inject

class TaskUseCase
    @Inject
    constructor(
        private val taskRepository: TaskRepository,
        private val workRepository: WorkRepository,
    ) {
        private val recommendTasks = mutableListOf<RecommendTask>()

        suspend fun getRecommendTask(cropId: Long): List<RecommendTask> {
            val getTasks =
                taskRepository
                    .getRecommendTasks(cropId)
                    .map { RecommendTask(System.currentTimeMillis(), it) }
            recommendTasks.addAll(getTasks)
            return recommendTasks.sortedBy { it.id }
        }

        suspend fun saveTask(
            ipAddress: String,
            cropId: Long,
        ): Boolean {
            val saveTasks = recommendTasks.filter { it.isChecked }
            val saveContents = saveTasks.joinToString(SEPARATOR) { it.content }
            recommendTasks.clear()
            return taskRepository.saveTask(cropId, ipAddress, saveContents)
        }

        suspend fun setEditTaskSave(
            workId: Long,
            cropId: Long,
        ): Boolean {
            val result =
                workRepository.updateTask(
                    workId,
                    cropId,
                    recommendTasks.filter { it.isChecked }.joinToString(SEPARATOR) { it.content },
                )
            recommendTasks.clear()
            return result
        }

        fun addTask(task: String): List<RecommendTask> {
            recommendTasks.add(RecommendTask(System.currentTimeMillis(), task))
            return recommendTasks.sortedBy { it.id }
        }

        fun updateRecommendTask(position: Int): List<RecommendTask> {
            val newTask = recommendTasks[position].copy(isChecked = !recommendTasks[position].isChecked)
            recommendTasks[position] = newTask
            return recommendTasks.sortedBy { it.id }
        }

        companion object {
            private const val SEPARATOR = "q!gL9A"
        }
    }
