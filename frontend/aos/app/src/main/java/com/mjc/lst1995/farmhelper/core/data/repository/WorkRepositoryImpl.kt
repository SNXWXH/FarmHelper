package com.mjc.lst1995.farmhelper.core.data.repository

import com.google.firebase.auth.FirebaseAuth
import com.mjc.lst1995.farmhelper.core.data.network.api.WorkApi
import com.mjc.lst1995.farmhelper.core.data.network.request.task.TaskDeleteToken
import com.mjc.lst1995.farmhelper.core.data.network.request.task.TaskUpdateToken
import com.mjc.lst1995.farmhelper.core.data.network.request.user.AuthToken
import com.mjc.lst1995.farmhelper.core.data.network.request.work.WorkCreateToken
import com.mjc.lst1995.farmhelper.core.data.network.request.work.WorkDetailToken
import com.mjc.lst1995.farmhelper.core.domain.model.crop.CropTask
import com.mjc.lst1995.farmhelper.core.domain.model.task.OtherDetail
import com.mjc.lst1995.farmhelper.core.domain.model.task.Task
import com.mjc.lst1995.farmhelper.core.domain.model.work.Work
import com.mjc.lst1995.farmhelper.core.domain.repository.WorkRepository
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.isActive
import javax.inject.Inject

class WorkRepositoryImpl
    @Inject
    constructor(
        private val workApi: WorkApi,
        private val auth: FirebaseAuth,
    ) : WorkRepository {
        override suspend fun getTodayTasks(): Flow<List<CropTask>> {
            auth.uid?.let {
                return callbackFlow {
                    trySend(workApi.getTodayTasks(it))
                    awaitClose()
                }
            } ?: run {
                return callbackFlow {
                    trySend(emptyList())
                    awaitClose()
                }
            }
        }

        override fun getWorks(): Flow<List<Work>> =
            callbackFlow {
                while (isActive) {
                    auth.uid?.let { userId ->
                        val workList = workApi.getWorks(AuthToken(userId)).workList
                        trySend(workList)
                    } ?: run {
                        trySend(emptyList())
                    }
                }
                awaitClose()
            }

        override fun getNickname(): Flow<String> =
            callbackFlow {
                while (isActive) {
                    auth.uid?.let { userId ->
                        val workList = workApi.getWorks(AuthToken(userId)).nickName
                        trySend(workList)
                    } ?: run {
                        trySend("")
                    }
                }
                awaitClose()
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
            cropId: Long,
            ipAddress: String,
        ): Flow<List<Task>> =
            callbackFlow {
                while (isActive) {
                    val workLogs =
                        workApi
                            .getWorkTaskDetails(WorkDetailToken(auth.uid!!, cropId, ipAddress))
                            .workLogs
                    trySend(workLogs)
                }
                awaitClose()
            }

        override suspend fun getWorkTaskOtherDetail(
            cropId: Long,
            ipAddress: String,
        ): Flow<OtherDetail> =
            flow {
                val workDetail =
                    workApi.getWorkTaskDetails(WorkDetailToken(auth.uid!!, cropId, ipAddress))
                val otherDetail =
                    OtherDetail(
                        today = workDetail.today,
                        cropName = workDetail.cropName,
                        nickname = workDetail.nickname,
                        weather = workDetail.weather,
                    )
                emit(otherDetail)
            }

        override suspend fun updateTask(
            workId: Long,
            cropId: Long,
            workContent: String,
        ): Boolean = workApi.updateTask(TaskUpdateToken(workId, auth.uid!!, cropId, workContent)).isOk

        override suspend fun deleteTask(
            workId: Long,
            cropId: Long,
        ): Boolean = workApi.deleteTask(TaskDeleteToken(workId, auth.uid!!, cropId)).isOk
    }
