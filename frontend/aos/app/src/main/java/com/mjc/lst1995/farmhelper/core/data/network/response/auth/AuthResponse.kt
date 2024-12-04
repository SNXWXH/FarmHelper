package com.mjc.lst1995.farmhelper.core.data.network.response.auth

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable


@Serializable
data class AuthResponse(
    @SerialName("isOk")
    val isOk: Boolean,
)
