package com.mjc.lst1995.farmhelper.core.data.network.api

import com.mjc.lst1995.farmhelper.core.data.network.request.task.TaskDeleteToken
import com.mjc.lst1995.farmhelper.core.data.network.request.task.TaskUpdateToken
import com.mjc.lst1995.farmhelper.core.data.network.request.user.AuthToken
import com.mjc.lst1995.farmhelper.core.data.network.request.work.WorkCreateToken
import com.mjc.lst1995.farmhelper.core.data.network.request.work.WorkDetailToken
import com.mjc.lst1995.farmhelper.core.data.network.response.result.ResultResponse
import com.mjc.lst1995.farmhelper.core.data.network.response.work.TodayTaskResponse
import com.mjc.lst1995.farmhelper.core.data.network.response.work.WorkDetailResponse
import com.mjc.lst1995.farmhelper.core.data.network.response.work.WorksResponse
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.PATCH
import retrofit2.http.POST
import retrofit2.http.Path

interface WorkApi {
    @GET("main/usercrop/{userId}")
    suspend fun getTodayTasks(
        @Path("userId") userId: String,
    ): TodayTaskResponse

    @POST("work/list")
    suspend fun getWorks(
        @Body authToken: AuthToken,
    ): WorksResponse

    @POST("crop/create")
    suspend fun createWork(
        @Body workCreateToken: WorkCreateToken,
    ): ResultResponse

    @POST("work/worklist")
    suspend fun getWorkTaskDetails(
        @Body workDetailToken: WorkDetailToken,
    ): WorkDetailResponse

    @PATCH("work/patch")
    suspend fun updateTask(
        @Body taskUpdateToken: TaskUpdateToken,
    ): ResultResponse

    @DELETE("work/delete")
    suspend fun deleteTask(
        @Body taskDeleteToken: TaskDeleteToken,
    ): ResultResponse
}
