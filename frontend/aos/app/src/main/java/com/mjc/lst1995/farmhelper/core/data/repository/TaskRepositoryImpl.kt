package com.mjc.lst1995.farmhelper.core.data.repository

import com.google.firebase.auth.FirebaseAuth
import com.mjc.lst1995.farmhelper.core.data.network.api.TaskApi
import com.mjc.lst1995.farmhelper.core.data.network.request.task.TaskRecommendToken
import javax.inject.Inject

class TaskRepositoryImpl @Inject constructor(
    private val taskApi: TaskApi,
    private val auth: FirebaseAuth
) {
    suspend fun getRecommendTask(cropId: Long): List<String> {
        return taskApi.getTaskRecommend(TaskRecommendToken(auth.uid!!, cropId)).recommendations
    }
}