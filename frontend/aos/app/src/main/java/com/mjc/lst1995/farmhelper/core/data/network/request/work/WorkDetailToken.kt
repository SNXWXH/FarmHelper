package com.mjc.lst1995.farmhelper.core.data.network.request.work

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class WorkDetailToken(
    @SerialName("userId")
    val userId: String,
    @SerialName("cropId")
    val cropId: String,
    @SerialName("ipAddress")
    val ipAddress: String
)
