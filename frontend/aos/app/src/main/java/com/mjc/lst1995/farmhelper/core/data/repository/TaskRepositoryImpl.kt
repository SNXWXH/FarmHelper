package com.mjc.lst1995.farmhelper.core.data.repository

import com.google.firebase.auth.FirebaseAuth
import com.mjc.lst1995.farmhelper.core.data.network.api.TaskApi
import com.mjc.lst1995.farmhelper.core.data.network.request.task.TaskCreateToken
import com.mjc.lst1995.farmhelper.core.data.network.request.task.TaskRecommendToken
import com.mjc.lst1995.farmhelper.core.domain.repository.TaskRepository
import javax.inject.Inject

class TaskRepositoryImpl
    @Inject
    constructor(
        private val taskApi: TaskApi,
        private val auth: FirebaseAuth,
    ) : TaskRepository {
        override suspend fun getRecommendTasks(cropId: Long): List<String> =
            taskApi.getTaskRecommend(TaskRecommendToken(auth.uid!!, cropId)).recommendations

        override suspend fun saveTask(
            cropId: Long,
            ipAddress: String,
            workContent: String,
        ): Boolean =
            taskApi
                .saveTasks(
                    TaskCreateToken(
                        auth.uid!!,
                        cropId,
                        workContent,
                        ipAddress,
                    ),
                ).isOk
    }
