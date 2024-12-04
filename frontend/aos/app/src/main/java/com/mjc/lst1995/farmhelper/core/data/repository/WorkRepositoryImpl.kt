package com.mjc.lst1995.farmhelper.core.data.repository

import com.google.firebase.auth.FirebaseAuth
import com.mjc.lst1995.farmhelper.core.data.network.api.WorkApi
import com.mjc.lst1995.farmhelper.core.data.network.request.user.AuthToken
import com.mjc.lst1995.farmhelper.core.data.network.request.work.WorkCreateToken
import com.mjc.lst1995.farmhelper.core.domain.model.crop.CropTask
import com.mjc.lst1995.farmhelper.core.domain.model.task.OtherDetail
import com.mjc.lst1995.farmhelper.core.domain.model.task.Task
import com.mjc.lst1995.farmhelper.core.domain.model.work.Work
import com.mjc.lst1995.farmhelper.core.domain.repository.WorkRepository
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow
import kotlinx.coroutines.isActive
import javax.inject.Inject

class WorkRepositoryImpl
    @Inject
    constructor(
        private val workApi: WorkApi,
        private val auth: FirebaseAuth,
    ) : WorkRepository {
        override suspend fun getTodayTasks(userId: String): List<CropTask> {
            TODO("Not yet implemented")
        }

        override fun getWorks(): Flow<List<Work>> =
            callbackFlow {
                while (isActive) {
                    auth.uid?.let { userId ->
                        // 서버에서 데이터를 가져와 채널에 전송
                        val workList = workApi.getWorks(AuthToken(userId)).workList
                        trySend(workList)
                    } ?: run {
                        trySend(emptyList())
                    }
                    delay(5000)
                }
            }

        override suspend fun createWork(
            cropName: String,
            cropDate: String,
            imageUrl: String?,
        ): Boolean =
            workApi
                .createWork(
                    WorkCreateToken(
                        userId = auth.uid!!,
                        cropName = cropName,
                        cropDate = cropDate,
                        imageUrl = imageUrl,
                    ),
                ).isOk

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
