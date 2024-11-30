package com.mjc.lst1995.farmhelper.core.data.network.request.task

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class TaskDeleteToken(
    @SerialName("workId")
    val workId: Long,
    @SerialName("userId")
    val userId: String,
    @SerialName("cropId")
    val cropId: String
)
