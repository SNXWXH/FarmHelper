package com.mjc.lst1995.farmhelper.core.data.network.request.task

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class TaskCreateToken(
    @SerialName("userId")
    val userId: String,
    @SerialName("cropId")
    val cropId: Long,
    @SerialName("workContent")
    val workContent: String,
    @SerialName("ipAddress")
    val ipAddress: String,
)
