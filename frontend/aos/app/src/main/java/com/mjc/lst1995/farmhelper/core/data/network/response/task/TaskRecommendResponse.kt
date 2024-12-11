package com.mjc.lst1995.farmhelper.core.data.network.response.task

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class TaskRecommendResponse(
    @SerialName("cropName")
    val cropName: String,
    @SerialName("nickname")
    val nickName: String,
    @SerialName("recommendations")
    val recommendations: List<String>
)
