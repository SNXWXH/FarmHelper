package com.mjc.lst1995.farmhelper.core.domain.repository

import com.mjc.lst1995.farmhelper.core.domain.model.crop.CropTask
import com.mjc.lst1995.farmhelper.core.domain.model.task.OtherDetail
import com.mjc.lst1995.farmhelper.core.domain.model.task.Task
import com.mjc.lst1995.farmhelper.core.domain.model.work.Work
import kotlinx.coroutines.flow.Flow

interface WorkRepository {
    suspend fun getTodayTasks(userId: String): List<CropTask>

    fun getWorks(): Flow<List<Work>>

    suspend fun createWork(
        cropName: String,
        cropDate: String,
        imageUrl: String?,
    ): Boolean

    suspend fun getWorkTaskDetails(
        cropId: Long,
        ipAddress: String,
    ): Flow<List<Task>>

    suspend fun getWorkTaskOtherDetail(
        cropId: String,
        ipAddress: String,
    ): OtherDetail

    suspend fun updateTask(
        workId: Long,
        cropId: String,
        workContent: String,
    ): Boolean

    suspend fun deleteTask(
        workId: Long,
        cropId: String,
    ): Boolean
}
