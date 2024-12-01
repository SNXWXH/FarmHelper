package com.mjc.lst1995.farmhelper.core.data.repository

import com.mjc.lst1995.farmhelper.core.data.network.api.WorkApi
import com.mjc.lst1995.farmhelper.core.domain.model.crop.CropTask
import com.mjc.lst1995.farmhelper.core.domain.model.task.OtherDetail
import com.mjc.lst1995.farmhelper.core.domain.model.task.Task
import com.mjc.lst1995.farmhelper.core.domain.model.work.Work
import com.mjc.lst1995.farmhelper.core.domain.repository.WorkRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class WorkRepositoryImpl
    @Inject
    constructor(
        private val workApi: WorkApi,
    ) : WorkRepository {
        override suspend fun getTodayTasks(userId: String): List<CropTask> {
            TODO("Not yet implemented")
        }

        override suspend fun getWorks(userId: String): List<Work> {
            TODO("Not yet implemented")
        }

        override suspend fun createWork(
            userId: String,
            cropName: String,
            cropDate: String,
            imageUrl: String?,
        ): Boolean {
            TODO("Not yet implemented")
        }

        override suspend fun getWorkTaskDetails(
            userId: String,
            cropId: String,
            ipAddress: String,
        ): Flow<List<Task>> {
            TODO("Not yet implemented")
        }

        override suspend fun getWorkTaskOtherDetail(
            userId: String,
            cropId: String,
            ipAddress: String,
        ): OtherDetail {
            TODO("Not yet implemented")
        }

        override suspend fun updateTask(
            workId: Long,
            userId: String,
            cropId: String,
            workContent: String,
        ): Boolean {
            TODO("Not yet implemented")
        }

        override suspend fun deleteTask(
            workId: Long,
            userId: String,
            cropId: String,
        ): Boolean {
            TODO("Not yet implemented")
        }
    }
