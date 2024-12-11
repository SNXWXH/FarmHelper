package com.mjc.lst1995.farmhelper.core.data.network.request.work

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class WorkCreateToken(
    @SerialName("userId")
    val userId: String,
    @SerialName("cropName")
    val cropName: String,
    @SerialName("cropDate")
    val cropDate: String,
    @SerialName("imageUrl")
    val imageUrl: String?,
)
