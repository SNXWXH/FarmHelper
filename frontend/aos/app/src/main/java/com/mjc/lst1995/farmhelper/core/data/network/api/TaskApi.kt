package com.mjc.lst1995.farmhelper.core.data.network.api

import com.mjc.lst1995.farmhelper.core.data.network.request.task.TaskCreateToken
import com.mjc.lst1995.farmhelper.core.data.network.request.task.TaskRecommendToken
import com.mjc.lst1995.farmhelper.core.data.network.response.result.ResultResponse
import com.mjc.lst1995.farmhelper.core.data.network.response.task.TaskRecommendResponse
import retrofit2.http.Body
import retrofit2.http.POST

interface TaskApi {
    @POST("work/recommend/create")
    suspend fun getTaskRecommend(
        @Body taskRecommendToken: TaskRecommendToken,
    ): TaskRecommendResponse

    @POST("work/listcreate")
    suspend fun saveTasks(
        @Body taskUpdateToken: TaskCreateToken,
    ): ResultResponse
}
