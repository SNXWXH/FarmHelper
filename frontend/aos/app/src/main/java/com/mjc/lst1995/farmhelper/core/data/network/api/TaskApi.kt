package com.mjc.lst1995.farmhelper.core.data.network.api

import com.mjc.lst1995.farmhelper.core.data.network.request.task.TaskRecommendToken
import com.mjc.lst1995.farmhelper.core.data.network.response.task.TaskRecommendResponse
import retrofit2.http.Body
import retrofit2.http.POST

interface TaskApi {
    @POST
    suspend fun getTaskRecommend(
        @Body taskRecommendToken: TaskRecommendToken,
    ): TaskRecommendResponse
}
