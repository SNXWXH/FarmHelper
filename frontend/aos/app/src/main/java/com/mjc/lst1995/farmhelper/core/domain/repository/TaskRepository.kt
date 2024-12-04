package com.mjc.lst1995.farmhelper.core.domain.repository

interface TaskRepository {
    suspend fun getRecommendTasks(cropId: Long): List<String>

    suspend fun saveTask(
        cropId: Long,
        ipAddress: String,
        workContent: String,
    ): Boolean
}
