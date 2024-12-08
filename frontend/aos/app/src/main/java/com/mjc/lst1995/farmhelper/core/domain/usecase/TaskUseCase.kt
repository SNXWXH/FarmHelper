package com.mjc.lst1995.farmhelper.core.domain.usecase

import com.mjc.lst1995.farmhelper.core.domain.model.task.RecommendTask
import com.mjc.lst1995.farmhelper.core.domain.repository.TaskRepository
import com.mjc.lst1995.farmhelper.core.domain.repository.WorkRepository
import java.util.UUID
import javax.inject.Inject

class TaskUseCase
    @Inject
    constructor(
        private val taskRepository: TaskRepository,
        private val workRepository: WorkRepository,
    ) {
        suspend fun getRecommendTask(cropId: Long): List<RecommendTask> =
            taskRepository
                .getRecommendTasks(cropId)
                .map {
                    RecommendTask(
                        UUID.randomUUID().toString(),
                        it,
                    )
            }

        suspend fun saveTask(
            ipAddress: String,
            cropId: Long,
            tasks: List<RecommendTask>,
        ): Boolean {
            val saveTasks = tasks.filter { it.isChecked }
            val saveContents = saveTasks.joinToString(SEPARATOR) { it.content }
            return taskRepository.saveTask(cropId, ipAddress, saveContents)
        }

        suspend fun setEditTaskSave(
            workId: Long,
            cropId: Long,
            tasks: List<RecommendTask>,
        ): Boolean =
            workRepository.updateTask(
                workId,
                cropId,
                tasks.filter { it.isChecked }.joinToString(SEPARATOR) { it.content },
            )

        companion object {
            private const val SEPARATOR = "q!gL9A"
        }
    }
