package com.mjc.lst1995.farmhelper.core.domain.repository

import com.mjc.lst1995.farmhelper.core.domain.model.crop.CropTask
import com.mjc.lst1995.farmhelper.core.domain.model.task.OtherDetail
import com.mjc.lst1995.farmhelper.core.domain.model.task.Task
import com.mjc.lst1995.farmhelper.core.domain.model.work.Work

interface WorkRepository {
    suspend fun getTodayTasks(userId: String): List<CropTask>

    suspend fun getWorks(userId: String): List<Work>

    suspend fun createWork(
        userId: String,
        cropName: String,
        cropDate: String,
        imageUrl: String?,
    ): Boolean

    suspend fun getWorkTaskDetails(
        userId: String,
        cropId: String,
        ipAddress: String,
    ): List<Task>

    suspend fun getWorkTaskOtherDetail(
        userId: String,
        cropId: String,
        ipAddress: String,
    ): OtherDetail

    suspend fun updateTask(
        workId: Long,
        userId: String,
        cropId: String,
        workContent: String,
    ): Boolean

    suspend fun deleteTask(
        workId: Long,
        userId: String,
        cropId: String,
    ): Boolean
}
