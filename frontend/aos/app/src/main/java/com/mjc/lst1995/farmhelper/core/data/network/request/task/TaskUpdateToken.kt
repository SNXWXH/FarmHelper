package com.mjc.lst1995.farmhelper.core.data.network.request.task

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class TaskUpdateToken(
    @SerialName("workId")
    val workId: Long,
    @SerialName("userId")
    val userId: String,
    @SerialName("cropId")
    val cropId: Long,
    @SerialName("workContent")
    val workContent: String,
)
