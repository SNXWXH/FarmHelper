package com.mjc.lst1995.farmhelper.core.data.network.response.result

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class ResultResponse(
    @SerialName("isOk")
    val isOk: Boolean,
)
